import java.util.Comparator;

public class Book implements Comparable<Book>{
    private final String title;
    private final String author;
    private final String genre;
    private final String isbn;
    private final int publicationYear;

    public Book(String title, String author, String genre, String isbn, int publicationYear) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    @Override
    public int compareTo(Book o) {
        return this.title.compareTo(o.title);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book other = (Book) obj;
        return this.isbn.equals(other.isbn);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publicationYear=" + publicationYear +
                '}';
    }
}

class BookYearComparator implements Comparator<Book> {

    @Override
    public int compare(Book o1, Book o2) {
        return Integer.compare(o1.getPublicationYear(), o2.getPublicationYear());
    }
}
