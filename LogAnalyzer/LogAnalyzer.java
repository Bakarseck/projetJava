import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class LogAnalyzer {
    public static void main(String[] args) {
        String filePath = "exemple_logs.log";
        System.out.println("Entrez le niveau de sévérité à filtrer (INFO, WARN, ERROR) ou 'ALL' pour tous les niveaux:");
        
        Scanner scanner = new Scanner(System.in);
        String severityFilter = scanner.nextLine().trim().toUpperCase();
        
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            Map<String, Long> logCounts = stream
                .filter(line -> severityFilter.equals("ALL") || line.contains(" " + severityFilter + " "))
                .collect(Collectors.groupingBy(line -> line.split(" ")[2], Collectors.counting()));
            
            logCounts.forEach((severity, count) -> System.out.println(severity + ": " + count));
            
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier de log: " + e.getMessage());
        }
        scanner.close();
    }
}
