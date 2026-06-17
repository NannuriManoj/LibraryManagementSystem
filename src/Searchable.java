
import java.util.ArrayList;

public interface Searchable {
    ArrayList<Book> searchByTitle(String title);
    ArrayList<Book> searchByAuthor(String author);
    ArrayList<Book> searchByGenre(String genre);

}
