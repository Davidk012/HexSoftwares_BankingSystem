package hexsoftwares;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Transaction {
    private final String type;
    private final double amount;
    private final LocalDateTime timestamp;
    private final String description;

    public Transaction(String type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.description = description;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %s: R%.2f - %s", 
                            timestamp.format(formatter), 
                            type, 
                            amount, 
                            description);
    }
}

class BankAccount {
    private final String accountNumber;
    private double balance;
    private final List<Transaction> transactionHistory;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        addTransaction("ACCOUNT CREATION", initialBalance, "Initial deposit");
    }

    public void deposit(double amount, String description) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount. Amount must be positive.");
        } else {
            balance += amount;
            System.out.printf("Deposited R%.2f. New balance: R%.2f%n", amount, balance);
            addTransaction("DEPOSIT", amount, description);
        }
    }

    public void withdraw(double amount, String description) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount. Amount must be positive.");
        } else if (amount > balance) {
            System.out.printf("Withdrawal denied. Insufficient funds. Current balance: R%.2f%n", balance);
            addTransaction("WITHDRAWAL ATTEMPT", amount, "Failed: " + description);
        } else {
            balance -= amount;
            System.out.printf("Withdrew R%.2f. New balance: R%.2f%n", amount, balance);
            addTransaction("WITHDRAWAL", amount, description);
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    private void addTransaction(String type, double amount, String description) {
        transactionHistory.add(new Transaction(type, amount, description));
    }

    public void printTransactionHistory() {
        System.out.println("\nTransaction History for Account: " + accountNumber);
        System.out.println("------------------------------------------------");
        for (Transaction t : transactionHistory) {
            System.out.println(t);
        }
        System.out.println("------------------------------------------------");
        System.out.printf("Current Balance: R%.2f%n", balance);
    }
}

class Customer {
    private final String customerId;
    private final String name;
    private final String pin;
    private final Map<String, BankAccount> accounts;

    public Customer(String customerId, String name, String pin) {
        this.customerId = customerId;
        this.name = name;
        this.pin = pin;
        this.accounts = new HashMap<>();
    }

    public void addAccount(String accountNumber, double initialBalance) {
        if (!accounts.containsKey(accountNumber)) {
            accounts.put(accountNumber, new BankAccount(accountNumber, initialBalance));
            System.out.printf("Account %s created successfully.%n", accountNumber);
        } else {
            System.out.println("Account number already exists.");
        }
    }

    public boolean authenticate(String enteredPin) {
        return pin.equals(enteredPin);
    }

    public void deposit(String accountNumber, double amount, String description) {
        BankAccount account = accounts.get(accountNumber);
        if (account != null) {
            account.deposit(amount, description);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void withdraw(String accountNumber, double amount, String description) {
        BankAccount account = accounts.get(accountNumber);
        if (account != null) {
            account.withdraw(amount, description);
        } else {
            System.out.println("Account not found.");
        }
    }

    public double checkBalance(String accountNumber) {
        BankAccount account = accounts.get(accountNumber);
        if (account != null) {
            return account.getBalance();
        }
        System.out.println("Account not found.");
        return -1;
    }

    public void printTransactionHistory(String accountNumber) {
        BankAccount account = accounts.get(accountNumber);
        if (account != null) {
            account.printTransactionHistory();
        } else {
            System.out.println("Account not found.");
        }
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public List<String> getAccountNumbers() {
        return new ArrayList<>(accounts.keySet());
    }
}

public class SimpleBankingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Create customer with PIN
        Customer customer = new Customer("C001", "Tshepiso Kekana", "4224");
        
        // Add multiple accounts
        customer.addAccount("A123", 1000.0);
        customer.addAccount("S456", 5000.0);
        customer.addAccount("I789", 20000.0);
        
        System.out.println("Welcome to Tshepiso's Enhanced Banking System");
        
        // PIN Authentication
        boolean authenticated = false;
        int attempts = 0;
        while (!authenticated && attempts < 3) {
            System.out.print("Enter your PIN: ");
            String enteredPin = scanner.nextLine();
            authenticated = customer.authenticate(enteredPin);
            
            if (!authenticated) {
                attempts++;
                System.out.println("Incorrect PIN. Attempts remaining: " + (3 - attempts));
            }
        }
        
        if (!authenticated) {
            System.out.println("Authentication failed. Exiting...");
            scanner.close();
            return;
        }
        
        System.out.println("Authentication successful.");
        System.out.println("Customer: " + customer.getName());
        
        String currentAccount = "";
        int choice;
        do {
            System.out.println("\nMain Menu:");
            System.out.println("1. Select Account");
            System.out.println("2. Create New Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Check Balance");
            System.out.println("6. View Transaction History");
            System.out.println("7. Exit");
            System.out.print("Enter your choice (1-7): ");
            
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1: // Select Account
                    List<String> accounts = customer.getAccountNumbers();
                    System.out.println("\nYour Accounts:");
                    for (int i = 0; i < accounts.size(); i++) {
                        System.out.printf("%d. %s%n", i + 1, accounts.get(i));
                    }
                    System.out.print("Select an account (1-" + accounts.size() + "): ");
                    int accountIndex = scanner.nextInt() - 1;
                    scanner.nextLine(); // Consume newline
                    
                    if (accountIndex >= 0 && accountIndex < accounts.size()) {
                        currentAccount = accounts.get(accountIndex);
                        System.out.println("Selected account: " + currentAccount);
                    } else {
                        System.out.println("Invalid selection.");
                    }
                    break;
                    
                case 2: // Create New Account
                    System.out.print("Enter new account number: ");
                    String newAccountNumber = scanner.nextLine();
                    System.out.print("Enter initial balance: R");
                    double initialBalance = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    customer.addAccount(newAccountNumber, initialBalance);
                    currentAccount = newAccountNumber;
                    break;
                    
                case 3: // Deposit
                    if (!currentAccount.isEmpty()) {
                        System.out.print("Enter deposit amount: R");
                        double depositAmount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter deposit description: ");
                        String depositDesc = scanner.nextLine();
                        customer.deposit(currentAccount, depositAmount, depositDesc);
                    } else {
                        System.out.println("Please select an account first.");
                    }
                    break;
                    
                case 4: // Withdraw
                    if (!currentAccount.isEmpty()) {
                        System.out.print("Enter withdrawal amount: R");
                        double withdrawAmount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter withdrawal description: ");
                        String withdrawDesc = scanner.nextLine();
                        customer.withdraw(currentAccount, withdrawAmount, withdrawDesc);
                    } else {
                        System.out.println("Please select an account first.");
                    }
                    break;
                    
                case 5: // Check Balance
                    if (!currentAccount.isEmpty()) {
                        double balance = customer.checkBalance(currentAccount);
                        if (balance >= 0) {
                            System.out.printf("Current Balance: R%.2f%n", balance);
                        }
                    } else {
                        System.out.println("Please select an account first.");
                    }
                    break;
                    
                case 6: // View Transaction History
                    if (!currentAccount.isEmpty()) {
                        customer.printTransactionHistory(currentAccount);
                    } else {
                        System.out.println("Please select an account first.");
                    }
                    break;
                    
                case 7: // Exit
                    System.out.println("Exiting...");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please enter 1-7.");
            }
        } while (choice != 7);
        
        scanner.close();
    }
}
