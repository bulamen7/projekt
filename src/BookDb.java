import java.io.IOException;
import java.util.List;

public interface BookDb {
    List<Book> getBooks(String filter) ;
    void save(String path) throws IOException;
    List<Book> sortByAuthor();
    List<Book> sortByCountry();
    List<Book> sortByTitle();
    void add(Book book);
    void delete(Book book);
}
