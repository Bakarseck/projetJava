import java.util.Scanner;

public class LibraryApplication {
    public static void main(String[] args) {
        Library library = new Library(); 
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;

        while (!quit) {
            System.out.println(
                    "Choose an action:\n \t(1) Add Book\n \t(2) Remove Book\n \t(3) Borrow Book\n \t(4) Return Book\n \t(5) Search Books\n \t(6) Show All Books\n \t(7) Quit\n");
            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                // Add Book
                case 1:
                    System.out.println("Enter title:");
                    String title = scanner.nextLine();
                    System.out.println("Enter author:");
                    String author = scanner.nextLine();
                    System.out.println("Enter ISBN:");
                    String isbn = scanner.nextLine();
                    library.addBook(new Book(title, author, isbn));
                    System.out.println("Book added successfully.");
                    library.saveBooksToFile();
                    break;
                // Remove Book
                case 2:
                    System.out.println("Enter ISBN of the book to remove:");
                    isbn = scanner.nextLine();
                    library.removeBook(isbn);
                    System.out.println("Book removed successfully.");
                    library.saveBooksToFile();
                    break;
                // Borrow Book
                case 3:
                    System.out.println("Enter ISBN of the book to borrow:");
                    isbn = scanner.nextLine();
                    library.borrowBook(isbn);
                    System.out.println("Book borrowed successfully.");
                    library.saveBooksToFile();
                    break;
                // Return Book
                case 4:
                    System.out.println("Enter ISBN of the book to return:");
                    isbn = scanner.nextLine();
                    library.returnBook(isbn);
                    System.out.println("Book returned successfully.");
                    library.saveBooksToFile();
                    break;
                // Search Books
                case 5:
                    System.out.println("Enter search query (title, author, or ISBN):");
                    String query = scanner.nextLine();
                    library.searchBooks(query).forEach(System.out::println);
                    break;
                // Show All Books
                case 6:
                    library.printAllBooks();
                    break;
                // Quit
                case 7:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid action. Please choose again.");
                    break;
            }
        }
        scanner.close();
    }
}
