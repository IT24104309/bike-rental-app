package lk.sliit.bike_rental_api.repository;


import lk.sliit.bike_rental_api.models.RentalTransaction;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RentalRepository {
    private static final String FILE_PATH = "orders.txt";

    // Create: Save a new rental transaction
    public static void saveRental(RentalTransaction transaction) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(transaction.toString());
            writer.newLine();
        }
    }

    // Read: Get all rental transactions
    public static List<RentalTransaction> getAllRentals() throws IOException {
        List<RentalTransaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    RentalTransaction transaction = new RentalTransaction(
                            parts[0],  // orderId
                            parts[1],  // username
                            parts[2],  // bikeId
                            parts[3],  // bikeType
                            Double.parseDouble(parts[4])  // totalPrice
                    );
                    transaction.setStatus(parts[5]);
                    transactions.add(transaction);
                }
            }
        }
        return transactions;
    }

    // Update: Update transaction status
    public static void updateRental(String orderId, String status) throws IOException {
        List<RentalTransaction> transactions = getAllRentals();
        for (RentalTransaction transaction : transactions) {
            if (transaction.getOrderId().equals(orderId)) {
                transaction.setStatus(status);
            }
        }
        saveAllTransactions(transactions);
    }
    // Delete: Remove completed transactions
    public static void deleteCompletedRental(String orderId) throws IOException {
        List<RentalTransaction> transactions = getAllRentals();
        transactions = transactions.stream()
                .filter(t -> !t.getOrderId().equals(orderId) || !t.getStatus().equals("completed"))
                .collect(Collectors.toList());
        saveAllTransactions(transactions);
    }

    // Helper method to overwrite file with updated transactions
    private static void saveAllTransactions(List<RentalTransaction> transactions) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (RentalTransaction transaction : transactions) {
                writer.write(transaction.toString());
                writer.newLine();
            }
        }
    }

    // Abstraction: Check bike availability
    public static boolean isBikeAvailable(String bikeId) throws IOException {
        List<RentalTransaction> transactions = getAllRentals();
        return transactions.stream()
                .noneMatch(t -> t.getBikeId().equals(bikeId) && t.getStatus().equals("ongoing"));
    }
}