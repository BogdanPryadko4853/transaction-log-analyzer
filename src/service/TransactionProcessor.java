package service;

import model.Transaction;
import model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class TransactionProcessor {
    private final LogParser logParser;
    private final FileHandler fileHandler;
    private final Map<String, User> users;

    public TransactionProcessor() {
        this.logParser = new LogParser();
        this.fileHandler = new FileHandler();
        this.users = new HashMap<>();
    }

    public void processDirectory(String directoryPath) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".log"))
                    .forEach(this::processFile);
        }

        Path outputDir = Paths.get(directoryPath, "transactions_by_users");
        fileHandler.createDirectory(outputDir);

        users.values().forEach(user -> {
            try {
                fileHandler.writeUserTransactions(user, outputDir);
            } catch (IOException e) {
                System.err.println("Error writing user file: " + e.getMessage());
            }
        });
    }

    private void processFile(Path filePath) {
        try {
            List<String> lines = Files.readAllLines(filePath);
            lines.stream()
                    .map(logParser::parseLine)
                    .filter(Objects::nonNull)
                    .forEach(this::processTransaction);
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath + " - " + e.getMessage());
        }
    }

    private void processTransaction(Transaction transaction) {
        users.computeIfAbsent(transaction.getUsername(), User::new)
                .addTransaction(transaction);
    }
}