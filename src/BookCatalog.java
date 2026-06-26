import java.util.*;

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
        TreeSet<String> genres = new TreeSet<>();

        for (Book book : books.values()){
            genres.add(book.getGenre());
        }
        return genres;
    }
}
