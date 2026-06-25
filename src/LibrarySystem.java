import java.util.ArrayList;

public class LibrarySystem {
    private BookCatalog bookCatalog;
    private ArrayList<Member> members;
    private ArrayList<Librarian> librarians;

    public LibrarySystem() {
        this.bookCatalog = new BookCatalog();
        this.members = new ArrayList<>();
        this.librarians = new ArrayList<>();
    }

    public BookCatalog getBookCatalog() {
        return bookCatalog;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public ArrayList<Librarian> getLibrarians() {
        return librarians;
    }

    public void registerMember(Member member){
        members.add(member);
        System.out.println("Member registered successfully");
    }

    public void addLibrarian(Librarian librarian){
        librarians.add(librarian);
        System.out.println("Librarian added successfully");
    }

    public Member findMemberById(String memberId){
        for (Member member : members){
            if (member.getMemberId().equals(memberId)){
                return member;
            }
        }
        throw new MemberNotFoundException("Member with ID " + memberId + " not found");
    }

    public void borrowBook(Member member, String isbn){
        Book book = bookCatalog.findByIsbn(isbn);
//        if (book == null){
//            System.out.println("Book not found");
//            return;
//        }
        member.borrowBook(book);
    }

    public void returnBook(Member member, String isbn) {
        Book book = bookCatalog.findByIsbn(isbn);
//        if (book == null) {
//            System.out.println("Book not found.");
//            return;
//        }
        member.returnBook(book);
    }
}
