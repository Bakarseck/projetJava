import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Library {
    private List<Book> books = new ArrayList<>();

    public Library() {
        loadBooksFromFile("books.txt");
    }

    public void addBook(Book book) {
        books.add(book);
        saveBooksToFile();
    }

    public void removeBook(String isbn) {
        books.removeIf(book -> book.getIsbn().equals(isbn));
        saveBooksToFile();
    }

    public void borrowBook(String isbn) {
        books.stream()
                .filter(book -> book.getIsbn().equals(isbn) && !book.isBorrowed())
                .findFirst()
                .ifPresent(Book::borrow);
        saveBooksToFile();
    }

    public void returnBook(String isbn) {
        books.stream()
                .filter(book -> book.getIsbn().equals(isbn) && book.isBorrowed())
                .findFirst()
                .ifPresent(Book::returnBook);
        saveBooksToFile();
    }

    public List<Book> searchBooks(String query) {
        return books.stream()
                    .filter(book -> book.getTitle().contains(query) || book.getAuthor().contains(query) || book.getIsbn().contains(query))
                    .collect(Collectors.toList());
    }

    public void printAllBooks() {
        books.forEach(System.out::println);
    }

    public void saveBooksToFile() {
        String filePath = "books.txt";
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Book book : books) {
                writer.write(book.getTitle() + "," + book.getAuthor() + "," + book.getIsbn() + "," + book.isBorrowed() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBooksFromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                Book book = new Book(data[0], data[1], data[2]);
                if (Boolean.parseBoolean(data[3])) {
                    book.borrow();
                }
                books.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
