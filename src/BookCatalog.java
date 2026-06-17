import com.sun.source.tree.LambdaExpressionTree;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class BookCatalog implements Searchable{
    private ArrayList<Book> books;

    public BookCatalog(){
        this.books = new ArrayList<>();
    }
    @Override
    public ArrayList<Book> searchByTitle(String title) {
        ArrayList<Book> result = new ArrayList<>();
        for (Book book : books){
            if(book.getTitle().toLowerCase().contains(title.toLowerCase())){
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public ArrayList<Book> searchByAuthor(String author) {
        ArrayList<Book> result = new ArrayList<>();
        for(Book book : books){
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())){
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public ArrayList<Book> searchByGenre(String genre) {
        ArrayList<Book> result = new ArrayList<>();
        for (Book book : books){
            if (book.getGenre().toLowerCase().contains(genre.toLowerCase())){
                result.add(book);
            }
        }
        return result;
    }

    public void addBook(Book book){
        books.add(book);
    }
    public void removeBook(Book book){
        books.remove(book);
    }
    public Book findByIsbn(String isbn){
        for(Book book: books){
            if(book.getIsbn().equals(isbn)){
                return book;
            }
        }
        return null;
    }
}
