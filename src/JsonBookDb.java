import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JsonBookDb implements BookDb {
    private List<Book> books;

    @Override
    public List<Book> getBooks(String filter) {
        return books.stream()
                .filter(b ->
                     b.getTitle().toUpperCase().contains(filter.toUpperCase()) ||
                    b.getAuthor().toUpperCase().contains(filter.toUpperCase()) ||
                    b.getCountry().toUpperCase().contains(filter.toUpperCase()) ||
                    b.getImageLink().toUpperCase().contains(filter.toUpperCase()) ||
                    b.getLink().toUpperCase().contains(filter.toUpperCase()) ||
                    b.getLanguage().toUpperCase().contains(filter.toUpperCase()) ||
                    (b.getYear() + "").contains(filter) ||
                    (b.getPages() + "").contains(filter)
                )
                .collect(Collectors.toList());

    }

    @Override
    public void save(String path) throws IOException {
        Gson gson = createGson();
        File file;
        FileWriter writer = new FileWriter(path);
        gson.toJson(this, writer);
        writer.close();
    }

    @Override
    public List<Book> sortByAuthor() {
        books.sort((b1, b2) -> b1.getAuthor().toUpperCase().compareTo(b2.getAuthor().toUpperCase()));
        return books;
    }

    @Override
    public List<Book> sortByCountry() {
        books.sort((b1, b2) -> b1.getCountry().toUpperCase().compareTo(b2.getCountry().toUpperCase()));
        return books;
    }

    @Override
    public List<Book> sortByTitle() {
        books.sort((b1, b2) -> b1.getTitle().toUpperCase().compareTo(b2.getTitle().toUpperCase()));
        return books;
    }

    public static JsonBookDb fromFile(String path) throws IOException {
        Gson gson = createGson();
        return gson.fromJson(new FileReader(path), JsonBookDb.class);
    }

    private static Gson createGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }
}
