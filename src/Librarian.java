public class Librarian {
    private String name;
    private final String employeeId;

    public Librarian(String name, String employeeId) {
        this.name = name;
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public String getEmployeeId() {
        return employeeId;
    }
    public void addBook(BookCatalog bookCatalog, Book book){
        bookCatalog.addBook(book);
    }
    public void removeBook(BookCatalog bookCatalog, Book book){
        bookCatalog.removeBook(book);
    }
}
