import java.util.*;
import java.io.IOException;

public class BankSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<Integer, BankAccount> accounts = new HashMap<>();
    private static int nextAccountNumber = 1;
    private static final String ACCOUNTS_FILE_PATH = "accounts.txt";

    public static void main(String[] args) {
        loadAccounts();
        runSystem();
        saveAccounts();
    }

    private static void loadAccounts() {
        try {
            List<String> lines = FileUtils.readFile(ACCOUNTS_FILE_PATH);
            int maxAccountNumber = 0;
            for (String line : lines) {
                BankAccount account = BankAccount.fromString(line);
                accounts.put(account.getAccountNumber(), account);
                maxAccountNumber = Math.max(maxAccountNumber, account.getAccountNumber());
            }
            nextAccountNumber = maxAccountNumber + 1;
        } catch (IOException e) {
            System.out.println("Impossible de charger les comptes : " + e.getMessage());
            nextAccountNumber = 1;
        }
    }

    private static void saveAccounts() {
        List<String> lines = new ArrayList<>();
        for (BankAccount account : accounts.values()) {
            lines.add(account.toString());
        }
        try {
            FileUtils.writeFile(ACCOUNTS_FILE_PATH, lines);
        } catch (IOException e) {
            System.out.println("Impossible de sauvegarder les comptes : " + e.getMessage());
        }
    }

    private static void runSystem() {
        boolean quit = false;
        while (!quit) {
            System.out.println(
                    "Choisissez une action:\n \t(1) Créer Compte\n \t(2) Dépôt\n \t(3) Retrait\n \t(4) Voir Solde\n \t(5) Transfert\n \t(6) Quitter");
            int action = scanner.nextInt();
            scanner.nextLine(); // Nettoie le buffer

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

    private static void createAccount() {
        BankAccount newAccount = new BankAccount(nextAccountNumber);
        accounts.put(newAccount.getAccountNumber(), newAccount);
        System.out.println("Nouveau compte créé avec le numéro de compte : " + newAccount.getAccountNumber());
        saveAccounts();
    }

    private static void manageDeposit() {
        System.out.println("Numéro de compte :");
        int accountNumber = scanner.nextInt();
        System.out.println("Montant à déposer :");
        double amount = scanner.nextDouble();
        if (accounts.containsKey(accountNumber)) {
            accounts.get(accountNumber).deposit(amount);
            System.out.println("Dépôt effectué.");
            saveAccounts();
        } else {
            System.out.println("Compte non trouvé.");
        }
    }

    private static void manageWithdrawal() {
        System.out.println("Numéro de compte :");
        int accountNumber = scanner.nextInt();
        System.out.println("Montant à retirer :");
        double amount = scanner.nextDouble();
        if (accounts.containsKey(accountNumber) && accounts.get(accountNumber).withdraw(amount)) {
            System.out.println("Retrait effectué.");
            saveAccounts();
        } else {
            System.out.println("Retrait échoué. Vérifiez le solde ou le numéro de compte.");
        }
    }

    private static void showBalance() {
        System.out.println("Numéro de compte :");
        int accountNumber = scanner.nextInt();
        if (accounts.containsKey(accountNumber)) {
            System.out.println("Solde actuel : " + accounts.get(accountNumber).getBalance());
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
        if (accounts.containsKey(sourceAccountNumber) && accounts.containsKey(targetAccountNumber)) {
            if (accounts.get(sourceAccountNumber).transfer(accounts.get(targetAccountNumber), amount)) {
                System.out.println("Transfert effectué.");
                saveAccounts();
            } else {
                System.out.println("Transfert échoué. Vérifiez les soldes.");
            }
        } else {
            System.out.println("Un des comptes n'a pas été trouvé.");
        }
    }
}
