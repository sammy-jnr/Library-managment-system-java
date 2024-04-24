import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Shelf {
    ArrayList<Book> books = new ArrayList<>();
    ArrayList<String> genres = new ArrayList<>();

    public Shelf(String genrePath, String booksPath) throws IOException {
        this.reloadShelf(genrePath, booksPath);
    }

    public void reloadShelf(String genrePath, String booksPath) throws IOException{
        BufferedReader genreReader = new BufferedReader(new FileReader(genrePath));
        String line;
        books = new ArrayList<>();
        genres = new ArrayList<>();
        while((line = genreReader.readLine()) != null){
            this.genres.add(line.strip());
        }


        BufferedReader booksReader = new BufferedReader(new FileReader(booksPath));

        ArrayList<String> bookInfoArray = new ArrayList<>();

        while((line = booksReader.readLine()) != null){
            if (line.isEmpty()){
                String name = bookInfoArray.get(0);
                String author = bookInfoArray.get(1);
                String id = bookInfoArray.get(2);
                String dateAdded = bookInfoArray.get(3);
                ArrayList<String> genres = new ArrayList<String>(Arrays.asList(bookInfoArray.get(4).split(",")));
                String description = bookInfoArray.get(5);
                Book newBook = new Book(name,author,id,dateAdded,genres,description);
                this.books.add(newBook);
                bookInfoArray = new ArrayList<>();
            }else{
                bookInfoArray.add(line);
            }
        }
    }

    public void Search(){

    }

}
