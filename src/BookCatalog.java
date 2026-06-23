import java.util.ArrayList;
import java.util.HashMap;

public class BookCatalog implements Searchable{
    private HashMap<String,Book> books;

    public BookCatalog(){
        this.books = new HashMap<>();
    }
    @Override
    public ArrayList<Book> searchByTitle(String title) {
        ArrayList<Book> result = new ArrayList<>();
        for (Book book : books.values()){
            if(book.getTitle().toLowerCase().contains(title.toLowerCase())){
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public ArrayList<Book> searchByAuthor(String author) {
        ArrayList<Book> result = new ArrayList<>();
        for(Book book : books.values()){
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())){
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public ArrayList<Book> searchByGenre(String genre) {
        ArrayList<Book> result = new ArrayList<>();
        for (Book book : books.values()){
            if (book.getGenre().toLowerCase().contains(genre.toLowerCase())){
                result.add(book);
            }
        }
        return result;
    }

    public void addBook(Book book){
        books.put(book.getIsbn(), book);
    }
    public void removeBook(Book book){
        books.remove(book.getIsbn());
    }
    public Book findByIsbn(String isbn){
        return books.get(isbn);
    }
}
