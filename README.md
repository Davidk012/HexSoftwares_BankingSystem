Project Overview
This Java application implements a simple banking system that allows customers to perform basic banking operations such as deposits, withdrawals, and balance checks. The system is designed with object-oriented principles to ensure clean separation of concerns.

Features
Account Management

Create bank accounts with initial balances

Deposit funds with validation

Withdraw funds with overdraft protection

Check current balance

Customer Management

Create customer profiles linked to bank accounts

Access customer information (ID, name, account details)

Interactive Console Interface

User-friendly menu system

Real-time transaction feedback

Error handling for invalid inputs

Class Structure
BankAccount Class

accountNumber: Unique account identifier (immutable)

balance: Current account balance

Methods:

deposit(amount): Adds funds to account

withdraw(amount): Deducts funds with validation

getBalance(): Returns current balance

Customer Class

customerId: Unique customer identifier

name: Customer's full name

account: Associated BankAccount instance

Methods:

deposit()/withdraw(): Proxy to account methods

checkBalance(): Returns account balance

SimpleBankingSystem Class (Main)

Creates sample customer (Tshepiso Kekana)

Provides interactive console menu

Handles user input and operations

How to Run the Application
Use Apache NetBeans
