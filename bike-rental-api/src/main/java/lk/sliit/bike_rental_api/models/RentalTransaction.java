package lk.sliit.bike_rental_api.models;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class RentalTransaction {
    private String orderId;
    private String renterName;
    private String username;
    private String bikeId;
    private String bikeType;
    private LocalDateTime rentalStart;
    private int durationHours;
    private double hourlyRate;
    private double totalPrice;
    private String status; // ongoing, completed, cancelled
    private boolean isPremiumUser;

    // Constructor
    public RentalTransaction(String orderId, String renterName, String username, String bikeId, 
                           String bikeType, int durationHours, double hourlyRate, boolean isPremiumUser) {
        this.orderId = orderId;
        this.renterName = renterName;
        this.username = username;
        this.bikeId = bikeId;
        this.bikeType = bikeType;
        this.rentalStart = LocalDateTime.now();
        this.durationHours = durationHours;
        this.hourlyRate = hourlyRate;
        this.isPremiumUser = isPremiumUser;
        this.status = "ongoing";
        this.totalPrice = calculateTotalPrice();
    }

    // Getters and Setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getRenterName() { return renterName; }
    public void setRenterName(String renterName) { this.renterName = renterName; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getBikeId() { return bikeId; }
    public void setBikeId(String bikeId) { this.bikeId = bikeId; }
    public String getBikeType() { return bikeType; }
    public void setBikeType(String bikeType) { this.bikeType = bikeType; }
    public LocalDateTime getRentalStart() { return rentalStart; }
    public void setRentalStart(LocalDateTime rentalStart) { this.rentalStart = rentalStart; }
    public int getDurationHours() { return durationHours; }
    public void setDurationHours(int durationHours) { this.durationHours = durationHours; }
    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public boolean isPremiumUser() { return isPremiumUser; }
    public void setPremiumUser(boolean premiumUser) { isPremiumUser = premiumUser; }

    // Polymorphic cost calculation
    public double calculateTotalPrice() {
        double basePrice = hourlyRate * durationHours;
        return isPremiumUser ? basePrice * 0.9 : basePrice; // 10% discount for premium users
    }

    // Calculate late fees if returned late
    public double calculateLateFees() {
        long actualHours = ChronoUnit.HOURS.between(rentalStart, LocalDateTime.now());
        if (actualHours > durationHours) {
            double lateHours = actualHours - durationHours;
            double lateFeeRate = isPremiumUser ? 5.0 : 7.0; // Lower late fee for premium users
            return lateHours * lateFeeRate;
        }
        return 0.0;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%d,%.2f,%.2f,%s,%b",
                orderId, renterName, username, bikeId, bikeType, rentalStart,
                durationHours, hourlyRate, totalPrice, status, isPremiumUser);
    }
}