package service;

import model.Transaction;
import model.TransactionType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Pattern LOG_PATTERN = Pattern.compile(
            "\\[(.*?)\\] (\\w+) (balance inquiry|transferred|withdrew) ([\\d.]+)(?: to (\\w+))?"
    );

    public Transaction parseLine(String line) {
        Matcher matcher = LOG_PATTERN.matcher(line);
        if (!matcher.find()) {
            return null;
        }

        LocalDateTime timestamp = LocalDateTime.parse(matcher.group(1), DATE_FORMATTER);
        String username = matcher.group(2);
        String operation = matcher.group(3);
        double amount = Double.parseDouble(matcher.group(4));

        return switch (operation) {
            case "balance inquiry" -> new Transaction(timestamp, username, TransactionType.BALANCE_INQUIRY, amount);
            case "transferred" ->
                    new Transaction(timestamp, username, TransactionType.TRANSFERRED, amount, matcher.group(5));
            case "withdrew" -> new Transaction(timestamp, username, TransactionType.WITHDREW, amount);
            default -> null;
        };
    }
}