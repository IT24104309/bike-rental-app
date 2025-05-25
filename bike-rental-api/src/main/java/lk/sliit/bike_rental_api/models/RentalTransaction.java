package lk.sliit.bike_rental_api.models;

public class RentalTransaction {
    private String orderId;
    private String username;
    private String bikeId;
    private String bikeType;
    private String status; // ongoing, completed, cancelled
    private double totalPrice;

    public RentalTransaction(String orderId, String username, String bikeId, String bikeType, double totalPrice) {
        this.orderId = orderId;
        this.username = username;
        this.bikeId = bikeId;
        this.bikeType = bikeType;
        this.status = "ongoing";
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getBikeId() { return bikeId; }
    public void setBikeId(String bikeId) { this.bikeId = bikeId; }

    public String getBikeType() { return bikeType; }
    public void setBikeType(String bikeType) { this.bikeType = bikeType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%.2f,%s",
                orderId, username, bikeId, bikeType, totalPrice, status);
    }
}
