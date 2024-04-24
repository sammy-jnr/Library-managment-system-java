import java.io.*;
import java.util.*;

public class Main {
    String CurrentDirectory = System.getProperty("user.dir");
    File UserDetails = new File(CurrentDirectory, "UserDetails.txt");
    String path = UserDetails.getPath();

    File BookDetails = new File(CurrentDirectory, "bookDetails.txt");
    String bookPath = BookDetails.getPath();

    File genreDetails = new File(CurrentDirectory, "genres.txt");
    String genrePath = genreDetails.getPath();

    Shelf shelf;
    String username;
    String email;
    String password;

    public static void main(String[] args) throws IOException {
        Main user = new Main();
        HashMap<String, Object> loginResult = user.Login();
        if (loginResult.get("username") == ""){
            user.Welcome();
        }else{
            user.username = loginResult.get("username").toString();
            user.email = loginResult.get("email").toString();
            user.password = loginResult.get("password").toString();

            if (!(Boolean)loginResult.get("isLoggedIn")){
                System.out.println("Login\n");
                Scanner mainScanner = new Scanner(System.in);

                boolean usernameCorrect = false;
                boolean passwordCorrect = false;

                while (!usernameCorrect || !passwordCorrect){
                    usernameCorrect = false;
                    passwordCorrect = false;
                    System.out.print("Username: ");
                    String username = mainScanner.next();
                    mainScanner.nextLine();
                    if(username.equals(loginResult.get("username"))){
                        usernameCorrect = true;
                    }
                    System.out.print("Password: ");
                    String password = mainScanner.next();
                    mainScanner.nextLine();
                    if(password.equals(loginResult.get("password"))){
                        passwordCorrect = true;
                    }
                }
                String username = user.username;
                String email = loginResult.get("email").toString();
                String password = loginResult.get("password").toString();
                try{
                    BufferedWriter mainWriter = new BufferedWriter(new FileWriter(user.path));
                    mainWriter.write(username + "\n");
                    mainWriter.write(email + "\n");
                    mainWriter.write(password + "\n");
                    mainWriter.write("true" + "\n");
                    mainWriter.close();
                }catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            user.shelf = new Shelf(user.genrePath, user.bookPath);
            System.out.println("\nWelcome back "+ loginResult.get("username").toString() + " !!\n");
            user.Options();






        }

    }


    public HashMap<String, Object> Login(){
        System.out.println(path);

        ArrayList<String> loginInfo = new ArrayList<>();
        HashMap<String, Object> userInfo = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;

            while ((line = br.readLine())!= null){
                loginInfo.add(line);
            }
            br.close();
        }catch (FileNotFoundException e){
//            e.printStackTrace();
            System.out.println("error");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(loginInfo.isEmpty()){
            userInfo.put("username","");
            userInfo.put("email","");
            userInfo.put("password","");
            userInfo.put("isLoggedIn",false);
        }else{
            userInfo.put("username",loginInfo.get(0));
            userInfo.put("email",loginInfo.get(1));
            userInfo.put("password",loginInfo.get(2));
            userInfo.put("isLoggedIn",Boolean.parseBoolean(loginInfo.get(3)));
        }
        return userInfo;
    }

    public void Welcome(){
        System.out.println("\nWelcome to your personal Library, \nPls Sign up to continue\n\n");
        Scanner mainScanner = new Scanner(System.in);
        System.out.print("Username: ");
        String username = mainScanner.next();
        mainScanner.nextLine();
        System.out.print("Email: ");
        String email = mainScanner.next();
        mainScanner.nextLine();
        String password ="";

        boolean isWeak = true;
        while (isWeak){
            System.out.print("Password: ");
            password = mainScanner.next();
            mainScanner.nextLine();

            boolean hasUppercase = false;
            boolean hasLowerCase = false;
            boolean hasDigits = false;
            boolean hasSpecialCase = false;

            for (char c : password.toCharArray()){
                if (Character.isUpperCase(c)){
                    hasUppercase = true;
                } else if (Character.isLowerCase(c)) {
                    hasLowerCase = true;
                } else if (Character.isDigit(c)) {
                    hasDigits = true;
                } else{
                    hasSpecialCase = true;
                }
            }

            if (hasUppercase && hasLowerCase && hasDigits && hasSpecialCase){
                isWeak = false;
                System.out.println("Congratulations you have successfully created an account");
            }else{
                System.out.println("Password must contain uppercase,lowercase, number and a special character");
                System.out.println("Try again!!\n");
            }
        }
        try{
            BufferedWriter mainWriter = new BufferedWriter(new FileWriter(path));
            mainWriter.write(username + "\n");
            mainWriter.write(email + "\n");
            mainWriter.write(password + "\n");
            mainWriter.write("true" + "\n");
            mainWriter.close();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void Options() throws IOException{
        System.out.println("What would you like to do?");
        System.out.println("A) Browse Genres");
        System.out.println("B) Add Genre");
        System.out.println("C) Browse Books");
        System.out.println("D) Add Books");
        System.out.println("E) Search");
        System.out.println("F) Delete Book");
        System.out.println("G) Logout\n");

        Scanner mainScanner = new Scanner(System.in);
        String selection = mainScanner.next();
        mainScanner.nextLine();
        char option = Character.toLowerCase(selection.charAt(0));

        switch(option){
            case('a'):
                this.showGenres();
                break;
            case('b'):
                System.out.print("Enter genre name: ");
                Scanner tempScanner = new Scanner(System.in);
                String newGenre = tempScanner.nextLine();
//                tempScanner.close();
                this.addGenres(newGenre);
                break;
            case('c'):
                this.showBooks();
                break;
            case('d'):
                Scanner bookScanner = new Scanner(System.in);
                System.out.println("\n\n");

                System.out.print("Enter book's name: ");
                String name = bookScanner.nextLine();
                System.out.println("\n");

                System.out.print("Author: ");
                String author = bookScanner.nextLine();
                System.out.println("\n");

                Random ranNum = new Random();
                String id = "BK" + ranNum.nextInt(1000000);

                Date fulldate = new Date();
                String date = fulldate.toString();

                System.out.print("Enter genres separated by ',': ");
                String genresResponse = bookScanner.nextLine();
                ArrayList<String> genre = new ArrayList<>(Arrays.asList(genresResponse.split(",")));
                System.out.println("\n");

                System.out.print("Enter a short description: ");
                String description = bookScanner.nextLine();

//                bookScanner.close();

                Book book = new Book(name,author,id,date,genre,description);
                this.addBooks(book,this.bookPath);
                break;
            case('e'):
                System.out.println("Search by:");
                System.out.println("A) Book name");
                System.out.println("B) Genre");
                System.out.println("C) Author");

                Scanner searchScanner = new Scanner(System.in);
                String choice = searchScanner.nextLine();
                System.out.print("\nEnter search term: ");
                String searchTerm = searchScanner.next();
                searchScanner.nextLine();

                if(choice.equalsIgnoreCase("a")){
                    for (Book i: this.shelf.books){
                        if(i.name.contains(searchTerm)){
                            i.printString();
                        }
                    }
                } else if (choice.equalsIgnoreCase("b")) {
                    for (Book i: this.shelf.books){
                        if(i.genre.contains(searchTerm)){
                            i.printString();
                        }
                    }
                } else if (choice.equalsIgnoreCase("c")) {
                    for (Book i: this.shelf.books){
                        if(i.author.contains(searchTerm)){
                            i.printString();
                        }
                    }
                }else{
                    System.out.println("Invalid option selected ");
                    this.reselectOptions();
                    return;
                }
                break;
            case('f'):
                System.out.print("Enter book Id: ");
                Scanner idScanner = new Scanner(System.in);
                String bookId = idScanner.next();
                idScanner.nextLine();
                Book selectedBook = null;
                for (Book i: this.shelf.books){
                    if(i.id.equals(bookId)){
                        selectedBook = i;
                    }
                }
                if (selectedBook == null){
                    System.out.println("Unable to delete, book with ID: "+ bookId + " not found.");
                }else{
                    this.removeBook(selectedBook, this.bookPath);
                }
                break;
            case('g'):
                this.logout(this.username,this.email,this.password);
                break;
            default:
                System.out.println("Invalid option selected, exiting program");

        }

    }

    public void reselectOptions() throws IOException{
        boolean validSelection = false;
        while (!validSelection){
            System.out.println("\nClick 'm' for Main menu or 'q' to Quit\n");
            Scanner tempScanner = new Scanner(System.in);
            String res = tempScanner.next();

            if (res.equalsIgnoreCase("m")){
                validSelection = true;
                this.Options();
            }
            if (res.equalsIgnoreCase("q")){
                validSelection = true;
                System.exit(0);
            }
        }
    }

    public void showGenres() throws IOException {
        if (shelf.genres.isEmpty()){
            System.out.println("You have not added any categories");
        }else{
            for (String genre : shelf.genres){
                System.out.println(genre);
            }
        }
        this.reselectOptions();
    }

    public void addGenres(String newGenre) throws IOException{
        ArrayList<String> genreList = shelf.genres;
        genreList.add(newGenre.strip());
        Collections.sort(genreList);
        BufferedWriter genreWriter = new BufferedWriter(new FileWriter(genrePath));
        for (String genre : genreList ){
            genreWriter.write(genre+"\n");
        }
        genreWriter.close();
        shelf.reloadShelf(this.genrePath,this.bookPath);
        System.out.println("Successfully added " + newGenre);
        this.reselectOptions();
    }

    public void showBooks() throws IOException {
        if (shelf.books.isEmpty()){
            System.out.println("You have not added any Books");
            this.reselectOptions();
            return;
        }
        for (Book book : shelf.books){
            book.printString();
        }
        this.reselectOptions();
    }

    public void removeBook(Book newBook, String bookPath) throws IOException{
        ArrayList<Book> booksList = shelf.books;
        booksList.remove(newBook);
        BufferedWriter bookWriter = new BufferedWriter(new FileWriter(bookPath));
        for (Book newbook : booksList ){
            newbook.writeToFile(bookWriter);
        }
        bookWriter.close();
        shelf.reloadShelf(this.genrePath,this.bookPath);
        System.out.println("Successfully Deleted " + newBook.name);
        this.reselectOptions();
    }
    public void addBooks(Book newBook, String bookPath) throws IOException{

        ArrayList<Book> booksList = shelf.books;
        booksList.add(newBook);
        BufferedWriter bookWriter = new BufferedWriter(new FileWriter(bookPath));
        for (Book newbook : booksList ){
            newbook.writeToFile(bookWriter);
        }
        bookWriter.close();
        for (String genre : newBook.genre){
            if (!shelf.genres.contains(genre)){
                ArrayList<String> genreList = shelf.genres;
                genreList.add(genre.strip());
                Collections.sort(genreList);
                BufferedWriter genreWriter = new BufferedWriter(new FileWriter(genrePath));
                for (String newgenre : genreList ){
                    genreWriter.write(newgenre+"\n");
                }
                genreWriter.close();
            }
        }
        shelf.reloadShelf(this.genrePath,this.bookPath);
        System.out.println("Successfully added " + newBook.name);
        this.reselectOptions();
    }

    public void logout(String username,String email,String password){

        try{
            BufferedWriter mainWriter = new BufferedWriter(new FileWriter(this.path));
            mainWriter.write(username + "\n");
            mainWriter.write(email + "\n");
            mainWriter.write(password + "\n");
            mainWriter.write("false" + "\n");
            mainWriter.close();
            System.out.println("\nLogging out ...");
            System.exit(0);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
