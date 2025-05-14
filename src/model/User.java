package model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String username;
    private double balance;
    private final List<Transaction> transactions;

    public User(String username) {
        this.username = username;
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        updateBalance(transaction);
    }

    private void updateBalance(Transaction transaction) {
        switch (transaction.getType()) {
            case BALANCE_INQUIRY:
                this.balance = transaction.getAmount();
                break;
            case TRANSFERRED, WITHDREW:
                this.balance -= transaction.getAmount();
                break;
        }
    }

    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}