import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BankSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<Integer, BankAccount> accounts = new HashMap<>();
    private static int nextAccountNumber = 1;

    public static void main(String[] args) {
        boolean quit = false;

        while (!quit) {
            System.out.println(
                    "Choisissez une action: \t(1) Créer Compte\n \t(2) Dépôt\n \t(3) Retrait\n \t(4) Voir Solde\n \t(5) Transfert\n \t(6) Quitter\n");
            int action = scanner.nextInt();

            switch (action) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    manageDeposit();
                    break;
                case 3:
                    manageWithdrawal();
                    break;
                case 4:
                    showBalance();
                    break;
                case 5:
                    manageTransfer();
                    break;
                case 6:
                    quit = true;
                    System.out.println("Merci d'avoir utilisé notre système bancaire.");
                    break;
                default:
                    System.out.println("Action non reconnue.");
                    break;
            }
        }
    }

    private static void logToFile(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bank_system_log.txt", true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier de log : " + e.getMessage());
        }
    }

    private static void createAccount() {
        BankAccount newAccount = new BankAccount(nextAccountNumber++);
        accounts.put(newAccount.getAccountNumber(), newAccount);
        String message = "Nouveau compte créé avec le numéro de compte : " + newAccount.getAccountNumber();
        System.out.println(message);
        logToFile(message);
    }

    private static void manageDeposit() {
        System.out.println("Numéro de compte :");
        int accountNumber = scanner.nextInt();
        System.out.println("Montant à déposer :");
        double amount = scanner.nextDouble();

        BankAccount account = accounts.get(accountNumber);
        if (account != null) {
            account.deposit(amount);
            System.out.println("Dépôt effectué.");
        } else {
            System.out.println("Compte non trouvé.");
        }
    }

    private static void manageWithdrawal() {
        System.out.println("Numéro de compte :");
        int accountNumber = scanner.nextInt();
        System.out.println("Montant à retirer :");
        double amount = scanner.nextDouble();

        BankAccount account = accounts.get(accountNumber);
        if (account != null && account.withdraw(amount)) {
            System.out.println("Retrait effectué.");
        } else {
            System.out.println("Retrait échoué. Vérifiez le solde ou le numéro de compte.");
        }
    }

    private static void showBalance() {
        System.out.println("Numéro de compte :");
        int accountNumber = scanner.nextInt();

        BankAccount account = accounts.get(accountNumber);
        if (account != null) {
            System.out.println("Solde actuel : " + account.getBalance());
        } else {
            System.out.println("Compte non trouvé.");
        }
    }

    private static void manageTransfer() {
        System.out.println("Numéro de compte source :");
        int sourceAccountNumber = scanner.nextInt();
        System.out.println("Numéro de compte cible :");
        int targetAccountNumber = scanner.nextInt();
        System.out.println("Montant à transférer :");
        double amount = scanner.nextDouble();

        BankAccount sourceAccount = accounts.get(sourceAccountNumber);
        BankAccount targetAccount = accounts.get(targetAccountNumber);

        if (sourceAccount != null && targetAccount != null && sourceAccount.transfer(targetAccount, amount)) {
            System.out.println("Transfert effectué.");
        } else {
            System.out.println("Transfert échoué. Vérifiez les numéros de compte ou le solde.");
        }
    }
}
