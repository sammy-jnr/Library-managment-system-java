import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Book {
    String name;
    String id;
    String dateAdded;
    String author;
    String description;
    ArrayList<String> genre;

    public Book(String name, String author, String id, String dateAdded,ArrayList<String> genre, String description){
        this.name = name;
        this.id = id;
        this.dateAdded = dateAdded;
        this.genre = genre;
        this.author = author;
        this.description = description;
    }

    public ArrayList<Book> delete(ArrayList<Book> bookList){
        bookList.removeIf(book -> book.id.equals(this.id));

        return bookList;
    }

    public void addGenre(String newGenre){
        this.genre.add(newGenre);
    }
    public void removeGenre(String newGenre){
        if (this.genre.size() == 1){
            System.out.println("Books must have at least 1 genre");
            return;
        }
        this.genre.remove(newGenre);
        System.out.println("Genre: " + this.genre.toString());
    }
    public void editDescription(String newDescription){
        this.description = newDescription;
    }
    public void editName(String newName){
        this.name = newName;
    }
    public void writeToFile(BufferedWriter writer) throws IOException {
        writer.write(this.name + "\n");
        writer.write(this.author + "\n");
        writer.write(this.id + "\n");
        writer.write(this.dateAdded + "\n");
        String sub = this.genre.toString().substring(1,(this.genre.toString().length() - 1));
        writer.write(sub + "\n");
        writer.write(this.description + "\n\n");
    }

    public void printString(){
        System.out.println("Name: " + this.name);
        System.out.println("Author: " + this.author);
        System.out.println("ID: " + this.id);
        System.out.println("Date added: " + this.dateAdded);
        System.out.println("Genre: " + this.genre.toString());
        System.out.println("Description: " + this.description + "\n");
    }

}
