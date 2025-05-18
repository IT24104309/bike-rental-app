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
    private static final String FILE_PATH = "rented_bikes.txt";

    // Create: Save a new rental transaction
    public void saveRental(RentalTransaction transaction) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(transaction.toString());
            writer.newLine();
        }
    }

    // Read: Get all rental transactions
    public List<RentalTransaction> getAllRentals() throws IOException {
        List<RentalTransaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                RentalTransaction transaction = new RentalTransaction(
                        parts[0], parts[1], parts[2], parts[3], parts[4],
                        Integer.parseInt(parts[6]), Double.parseDouble(parts[7]),
                        Boolean.parseBoolean(parts[10])
                );
                transaction.setRentalStart(LocalDateTime.parse(parts[5]));
                transaction.setTotalPrice(Double.parseDouble(parts[8]));
                transaction.setStatus(parts[9]);
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    // Update: Update transaction status and late fees
    public void updateRental(String orderId, String status) throws IOException {
        List<RentalTransaction> transactions = getAllRentals();
        for (RentalTransaction transaction : transactions) {
            if (transaction.getOrderId().equals(orderId)) {
                transaction.setStatus(status);
                if (status.equals("completed")) {
                    double lateFees = transaction.calculateLateFees();
                    transaction.setTotalPrice(transaction.getTotalPrice() + lateFees);
                }
            }
        }
        saveAllTransactions(transactions);
    }

    // Delete: Remove completed transactions
    public void deleteCompletedRental(String orderId) throws IOException {
        List<RentalTransaction> transactions = getAllRentals();
        transactions = transactions.stream()
                .filter(t -> !t.getOrderId().equals(orderId) || !t.getStatus().equals("completed"))
                .collect(Collectors.toList());
        saveAllTransactions(transactions);
    }

    // Helper method to overwrite file with updated transactions
    private void saveAllTransactions(List<RentalTransaction> transactions) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (RentalTransaction transaction : transactions) {
                writer.write(transaction.toString());
                writer.newLine();
            }
        }
    }

    // Abstraction: Check bike availability
    public boolean isBikeAvailable(String bikeId) throws IOException {
        List<RentalTransaction> transactions = getAllRentals();
        return transactions.stream()
                .noneMatch(t -> t.getBikeId().equals(bikeId) && t.getStatus().equals("ongoing"));
    }
}