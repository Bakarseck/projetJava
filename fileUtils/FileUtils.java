import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileUtils {

    // Méthode pour lire le contenu d'un fichier et le retourner sous forme de liste de chaînes de caractères
    public static List<String> readFile(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    // Méthode pour écrire du contenu dans un fichier (écrase le fichier s'il existe déjà)
    public static void writeFile(String filePath, List<String> lines) throws IOException {
        Files.write(Paths.get(filePath), lines);
    }

    // Méthode pour ajouter du contenu à la fin d'un fichier existant
    public static void appendToFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(content);
            writer.newLine();
        }
    }

    // Méthode pour copier un fichier
    public static void copyFile(String sourcePath, String destinationPath) throws IOException {
        Files.copy(Paths.get(sourcePath), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
    }

    // Méthode pour supprimer un fichier
    public static void deleteFile(String filePath) throws IOException {
        Files.delete(Paths.get(filePath));
    }
}
