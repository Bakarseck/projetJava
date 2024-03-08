public class BankAccount {
    private int accountNumber;
    private double balance;

    // Constructeur qui accepte un numéro de compte
    public BankAccount(int accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0.0; // Initialise le solde à 0
    }

    // Getters et Setters
    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Méthodes pour déposer, retirer
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    // Méthode toString pour sérialiser l'objet en chaîne de caractères
    @Override
    public String toString() {
        return accountNumber + "," + balance;
    }

    // Méthode statique pour créer une instance à partir d'une chaîne de caractères
    public static BankAccount fromString(String data) {
        String[] parts = data.split(",");
        int accountNumber = Integer.parseInt(parts[0]);
        double balance = Double.parseDouble(parts[1]);
        BankAccount account = new BankAccount(accountNumber);
        account.balance = balance;
        return account;
    }

    public boolean transfer(BankAccount toAccount, double amount) {
        if (this.balance >= amount && amount > 0) {
            this.balance -= amount;

            toAccount.deposit(amount);

            return true;
        }
        return false;
    }
}
