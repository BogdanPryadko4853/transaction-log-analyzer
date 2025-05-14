import service.TransactionProcessor;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter directory path containing log files: ");
        String directoryPath = scanner.nextLine();

        TransactionProcessor processor = new TransactionProcessor();
        try {
            processor.processDirectory(directoryPath);
            System.out.println("Processing completed successfully!");
        } catch (IOException e) {
            System.err.println("Error processing directory: " + e.getMessage());
        }
    }
}