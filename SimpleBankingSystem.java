package hexsoftwares;

import java.util.Scanner;

class BankAccount {
    private final String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount. Amount must be positive.");
        } else {
            balance += amount;
            System.out.printf("Deposited $%.2f. New balance: $%.2f%n", amount, balance);
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount. Amount must be positive.");
        } else if (amount > balance) {
            System.out.printf("Withdrawal denied. Insufficient funds. Current balance: $%.2f%n", balance);
        } else {
            balance -= amount;
            System.out.printf("Withdrew $%.2f. New balance: $%.2f%n", amount, balance);
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}

class Customer {
    private final String customerId;
    private final String name;
    private final BankAccount account;

    public Customer(String customerId, String name, String accountNumber, double initialBalance) {
        this.customerId = customerId;
        this.name = name;
        this.account = new BankAccount(accountNumber, initialBalance);
    }

    public void deposit(double amount) {
        account.deposit(amount);
    }

    public void withdraw(double amount) {
        account.withdraw(amount);
    }

    public double checkBalance() {
        return account.getBalance();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public BankAccount getAccount() {
        return account;
    }
}

public class SimpleBankingSystem {
    public static void main(String[] args) {
        Customer customer = new Customer("C001", "Tshepiso Kekana", "A123", 1000.0);
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to Tshepiso's Banking System");
        System.out.println("Customer: " + customer.getName());
        System.out.println("Account Number: " + customer.getAccount().getAccountNumber());
        System.out.printf("Initial Balance: R%.2f%n", customer.checkBalance());
        
        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");
            
            choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter deposit amount: R");
                    double depositAmount = scanner.nextDouble();
                    customer.deposit(depositAmount);
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: R");
                    double withdrawAmount = scanner.nextDouble();
                    customer.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.printf("Current Balance: R%.2f%n", customer.checkBalance());
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1-4.");
            }
        } while (choice != 4);
        
        System.out.printf("Final balance: R%.2f%n", customer.checkBalance());
        scanner.close();
    }
}
