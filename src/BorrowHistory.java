import java.util.ArrayList;
public class BorrowHistory {
    private ArrayList<Book> books;

    public BorrowHistory() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book){
        books.add(book);
    }
    public void removeBook(Book book){
        books.remove(book);
    }
    public ArrayList<Book> getBooks(){
        return new ArrayList<>(books);
    }
    public boolean contains(Book book){
        return books.contains(book);
    }
}
