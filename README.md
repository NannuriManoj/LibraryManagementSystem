# Library Management System — Build Journal

A complete walkthrough of how we designed and implemented this project from scratch, decision by decision.

---

## The Problem Breakdown Process

Before writing a single line of code, we applied a four-step thinking process. This process works for any OOP project.

**Step 1 — Find the nouns** (candidate classes)
Book, Member, Librarian, Catalog, BorrowHistory, Fine, ISBN, MemberType

**Step 2 — Find the verbs** (candidate methods)
search, borrow, return, calculate fine, add book, remove book

**Step 3 — Find the relationships**
- Is-a? → PremiumMember is-a MemberType
- Has-a? → Member has-a BorrowHistory, Member has-a MemberType
- Can-do? → BookCatalog can-do Searchable, Book can-do Comparable

**Step 4 — Build order** (most independent class first)
`Book` → `MemberType hierarchy` → `BorrowHistory` → `Member` → `Librarian` → `BookCatalog` → `LibrarySystem`

---

## Class-by-Class Breakdown

### 1. Book

**What it holds:** title, author, genre, isbn, publicationYear

**Key decisions made:**

- All fields are `private final` — a book's title and ISBN never change after creation. No setters needed. This is called **immutability**.
- `author` stays as `String` — not a separate class. Rule: extract a class only when the thing has behavior or multiple fields. A name has neither.
- `isbn` is `String` not `long` — ISBNs have hyphens and structure (978-0-13-468599-1). Never use numeric types for identifiers.
- Implements `Comparable<Book>` — for **natural ordering** by title alphabetically. `compareTo` delegates to `String.compareTo`.
- `equals` and `hashCode` both based on ISBN — because ISBN is the unique identifier. This is a Java contract: if two objects are equal, they must have the same hashCode. Breaking this contract silently breaks HashMap lookups later.
- `toString` overridden — so `System.out.println(book)` gives readable output instead of `Book@7852e922`.

**Separate class — BookYearComparator:**

`Comparable` gives one natural ordering (by title). For a second ordering (by year), we use a separate `Comparator<Book>`. Rule: one `Comparable`, many `Comparators`.

Used `Integer.compare()` instead of subtraction — the subtraction trick can silently overflow with extreme values.

---

### 2. MemberType (interface) + RegularMember + PremiumMember

**The design question:** abstract class or interface?

Chose **interface** because:
- No shared state between Regular and Premium members
- No shared implementation worth putting in a parent
- `MemberType` is purely a contract: "whoever implements me must have `calculateFine`"

**The polymorphism payoff:**

```
MemberType (interface)
    calculateFine(int daysOverdue): double

RegularMember implements MemberType → daysOverdue * 5.0  (₹5/day)
PremiumMember implements MemberType → daysOverdue * 2.0  (₹2/day)
```

When `member.calculateFine(10)` is called, Java looks at what `memberType` actually holds at runtime and calls the right version. No if-else needed anywhere. This is the **Strategy Pattern**.

---

### 3. BorrowHistory

**What it holds:** `ArrayList<Book>` — the books a single member currently has borrowed.

**Key decisions made:**

- Belongs to one member, not all members. Think of it as a library card — it tracks your books, not everyone's.
- Separate class instead of a raw ArrayList inside Member — because it has its own behavior (add, remove, contains). This keeps Member clean.
- `getBooks()` returns a **defensive copy** — `new ArrayList<>(books)` — so callers can't accidentally wipe internal state from outside the class.
- `removeBook` works correctly because `ArrayList.remove()` internally calls `Book.equals()` — which we based on ISBN. This is why getting `equals` right on `Book` matters downstream.

---

### 4. Member

**What it holds:** name, memberId, memberType, borrowHistory

**Key decisions made:**

- `memberId` is `final` — an ID never changes.
- `memberType` is not final — a Regular member can upgrade to Premium. Needs a setter.
- `borrowHistory` is created internally — `new BorrowHistory()` inside the constructor. The caller doesn't pass one in. A new member always starts with an empty history.
- `memberType` has a setter — `setMemberType()` — because membership tier can change.

**Methods:**

`borrowBook(Book book)` — checks if already borrowed first, then adds.

`returnBook(Book book)` — checks if the book is actually in borrow history first, then removes. Message says "not in your borrow history" not "already submitted" — more accurate.

`calculateFine(int daysOverdue)` — one line: `return memberType.calculateFine(daysOverdue)`. Member doesn't need to know if it's Regular or Premium. Polymorphism handles it.

---

### 5. Librarian

**The Person base class trap:**

Librarian and Member both have name and ID. The instinct is to make both extend a `Person` base class.

We didn't — because: **inheritance should model shared behavior, not just shared data.** Two shared fields is not enough reason for a base class. Librarian never borrows books. Member never adds books to catalog. Their behaviors are completely disjoint.

**Key decisions made:**

- Standalone class with name and employeeId only.
- `employeeId` is `final` — never changes.
- `addBook` and `removeBook` take `BookCatalog` as a parameter — Librarian operates on the catalog but doesn't own it. If the librarian leaves, the catalog doesn't disappear.

---

### 6. Searchable (interface) + BookCatalog

**Searchable interface:**

```
searchByTitle(String title): ArrayList<Book>
searchByAuthor(String author): ArrayList<Book>
searchByGenre(String genre): ArrayList<Book>
```

Returns `ArrayList<Book>` not a single book — multiple books can match a search.

**BookCatalog:**

- Holds `ArrayList<Book>` — initialized in constructor.
- Implements `Searchable` — all three search methods use case-insensitive `.contains()` matching. Both sides lowercased so "clean" matches "Clean Code".
- `findByIsbn(String isbn)` — uses `.equals()` not `.contains()`. Search uses contains. Unique ID lookup uses equals. Important distinction.
- `addBook` and `removeBook` — simple delegation to ArrayList.

---

### 7. LibrarySystem

**The coordinator.** Owns everything, wires everything together.

**What it holds:** BookCatalog, ArrayList<Member>, ArrayList<Librarian> — all created internally.

**Key design decision — where does the catalog check happen?**

When a member borrows a book, two things need to happen: check the catalog, then update borrow history. Member doesn't have catalog access — and shouldn't. LibrarySystem is the coordinator, so the catalog check lives here.

```
LibrarySystem.borrowBook:
    1. Find book in catalog by ISBN
    2. If not found → print "Book not found"
    3. If found → member.borrowBook(book)
```

`findMemberById` uses `.equals()` not `.contains()` — ID lookup must be exact match.

---

## The Full Class Map

```
Book                  (Comparable, immutable, equals by ISBN)
BookYearComparator    (Comparator, external sort by year)
MemberType            (interface — calculateFine contract)
RegularMember         (implements MemberType — ₹5/day)
PremiumMember         (implements MemberType — ₹2/day)
BorrowHistory         (wraps ArrayList<Book>, defensive copy)
Member                (has MemberType, has BorrowHistory)
Searchable            (interface — search contract)
BookCatalog           (implements Searchable, case-insensitive)
Librarian             (operates on catalog, doesn't own it)
LibrarySystem         (coordinator — owns catalog and lists)
Main                  (end to end test)
```

---

## OOP Concepts Demonstrated

| Concept | Where |
|---|---|
| Encapsulation | All fields private, defensive copy in BorrowHistory |
| Immutability | Book fields are private final, no setters |
| Inheritance | RegularMember and PremiumMember extend MemberType |
| Polymorphism | calculateFine works differently per type, no if-else |
| Abstraction | MemberType and Searchable hide implementation details |
| Composition | Member has-a BorrowHistory, has-a MemberType |
| Strategy Pattern | MemberType family — swappable fine calculation algorithms |
| Single Responsibility | Member manages history. LibrarySystem coordinates. Librarian operates on catalog. |

---

## Key Rules Learned

- **Don't add setters by default.** Ask: does this field ever change after creation? If no, no setter.
- **Extract a class only when it has behavior or multiple fields.** Otherwise String is enough.
- **Use String for identifiers** — never numeric types for ISBNs, IDs, or codes with structure.
- **equals and hashCode must be overridden together** — always based on the same field. Breaking this contract silently breaks HashMap lookups.
- **Comparable = one natural ordering. Comparator = external, additional orderings.**
- **Inheritance models shared behavior, not shared data.** Two shared fields is not enough reason for a base class.
- **Search uses contains. ID lookup uses equals.** Never mix them up.
- **Defensive copy on getters** — return `new ArrayList<>(list)` not the list itself.
- **Coordinator classes (LibrarySystem) own the cross-cutting logic.** Individual classes own only their own state.
