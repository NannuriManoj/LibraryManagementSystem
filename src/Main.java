import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        LibrarySystem library = new LibrarySystem();

        Librarian librarian1 = new Librarian("Ram", "EMP001");
        library.addLibrarian(librarian1);

        Book book1 = new Book("on a day","chimtu", "love", "BOOK001",2023);
        Book book2 = new Book("I fell for you", "chumki", "romantic","BOOK002",2024);

        library.getBookCatalog().addBook(book1);
        library.getBookCatalog().addBook(book2);

        Member member1 = new Member("Manoj", "M101", new RegularMember());
        Member member2 = new Member("Moni", "M102", new PremiumMember());
        library.registerMember(member1);
        library.registerMember(member2);

        library.borrowBook(member1,"BOOK001");
        library.borrowBook(member1,"BOOK001");

        library.returnBook(member1,"BOOK001");

        System.out.println(member1.calculateFine(10));
        library.getBookCatalog().searchByGenre("romantic").forEach(book -> System.out.println(book));

        BookCatalog catalog = new BookCatalog();
        catalog.addBook(new Book("Clean Code", "Martin", "Tech", "978-001", 2008));
        catalog.addBook(new Book("The Pragmatic Programmer", "Hunt", "Tech", "978-002", 1999));
        catalog.addBook(new Book("Dune", "Herbert", "Sci-Fi", "978-003", 1965));
        catalog.addBook(new Book("Foundation", "Asimov", "Sci-Fi", "978-004", 1951));

        System.out.println(catalog.getAllGenre());

        try {
            catalog.findByIsbn("123-321");
        } catch (BookNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            catalog.addBook(new Book("Clean Code", "Martin", "Tech", "978-001", 2008));
        } catch (BookAlreadyExistsException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            library.findMemberById("102");
        } catch (MemberNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}