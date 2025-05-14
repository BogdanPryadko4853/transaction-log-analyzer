package service;

import model.Transaction;
import model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FileHandler {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void createDirectory(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    public void writeUserTransactions(User user, Path outputDir) throws IOException {
        Path userFile = outputDir.resolve(user.getUsername() + ".log");
        List<String> lines = prepareUserTransactions(user);
        Files.write(userFile, lines);
    }

    private List<String> prepareUserTransactions(User user) {
        List<String> lines = user.getTransactions().stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp))
                .map(this::formatTransaction)
                .collect(Collectors.toList());

        lines.add(String.format("[%s] %s final balance %.2f",
                LocalDateTime.now().format(DATE_FORMATTER),
                user.getUsername(),
                user.getBalance()));

        return lines;
    }

    private String formatTransaction(Transaction transaction) {
        return switch (transaction.getType()) {
            case BALANCE_INQUIRY -> String.format("[%s] %s balance inquiry %.2f",
                    transaction.getTimestamp().format(DATE_FORMATTER),
                    transaction.getUsername(),
                    transaction.getAmount());
            case TRANSFERRED -> String.format("[%s] %s transferred %.2f to %s",
                    transaction.getTimestamp().format(DATE_FORMATTER),
                    transaction.getUsername(),
                    transaction.getAmount(),
                    transaction.getTargetUser());
            case WITHDREW -> String.format("[%s] %s withdrew %.2f",
                    transaction.getTimestamp().format(DATE_FORMATTER),
                    transaction.getUsername(),
                    transaction.getAmount());
        };
    }
}