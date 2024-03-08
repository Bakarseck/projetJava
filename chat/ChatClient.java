import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        // Crée un nouveau thread pour lire les messages du serveur
        new Thread(() -> {
            try {
                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    System.out.println("Message du serveur: " + serverMessage);
                }
            } catch (IOException e) {
                System.out.println("Erreur lors de la lecture du serveur: " + e.getMessage());
            }
        }).start();

        try {
            // Lire les messages de l'utilisateur et les envoyer au serveur
            String userInput;
            while ((userInput = consoleReader.readLine()) != null) {
                out.println(userInput);
            }
        } finally {
            socket.close();
        }
    }
}
