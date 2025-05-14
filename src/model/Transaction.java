package model;

import java.time.LocalDateTime;

public class Transaction {
    private final String username;
    private final TransactionType type;
    private final double amount;
    private String targetUser;
    private final LocalDateTime timestamp;

    public Transaction(LocalDateTime timestamp, String username, TransactionType type, double amount) {
        this.timestamp = timestamp;
        this.username = username;
        this.type = type;
        this.amount = amount;
    }

    public Transaction(LocalDateTime timestamp, String username, TransactionType type, double amount, String targetUser) {
        this(timestamp, username, type, amount);
        this.targetUser = targetUser;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }
}