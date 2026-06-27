import java.util.*;
import java.util.stream.Collectors;

public class BookCatalog implements Searchable{
    private HashMap<String,Book> books;

    public BookCatalog(){
        this.books = new HashMap<>();
    }
    @Override
    public ArrayList<Book> searchByTitle(String title) {
        return books.values().stream()
                .filter(book -> book.getTitle().toLowerCase()
                        .contains(title.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Book> searchByAuthor(String author) {
        return books.values().stream()
                .filter(book -> book.getAuthor().toLowerCase()
                        .contains(author.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Book> searchByGenre(String genre) {
        return books.values().stream()
                .filter(book -> book.getGenre().toLowerCase()
                        .contains(genre.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void addBook(Book book){
        if (books.containsKey(book.getIsbn())){
            throw new BookAlreadyExistsException("Book already with ISBN " + book.getIsbn() + "exists ");
        }
        books.put(book.getIsbn(), book);
    }
    public void removeBook(Book book){
        if (!books.containsKey(book.getIsbn())){
            throw new BookNotFoundException("Book with ISBN: "+ book.getIsbn() + " not found");
        }
        books.remove(book.getIsbn());
    }
    public Book findByIsbn(String isbn){
        Book book = books.get(isbn);
        if (book == null){
            throw new BookNotFoundException("Book with ISBN: "+ isbn + " not found");
        }
        return book;
    }

    public TreeSet<String> getAllGenre(){
        return books.values().stream()
                .map(Book::getGenre)
                .collect(Collectors.toCollection(TreeSet::new));
    }
}
