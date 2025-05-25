package lk.sliit.bike_rental_api.models;

import lk.sliit.bike_rental_api.enums.OrderStatus;

import java.time.LocalDateTime;

public class RentRequest {
    private String orderId;
    private String bikeId;
    private String bikeType;
    private String userId;
    private LocalDateTime requestTime;
    private double hours;
    private double hourlyRate;
    private double totalAmount;
    private OrderStatus status;

    public RentRequest(String orderId, String bikeId, String bikeType, String userId, LocalDateTime requestTime,
                       double hours, double hourlyRate, double totalAmount, OrderStatus status) {
        this.orderId = orderId;
        this.bikeId = bikeId;
        this.bikeType = bikeType;
        this.userId = userId;
        this.requestTime = requestTime;
        this.hours = hours;
        this.hourlyRate = hourlyRate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getBikeType() {
        return bikeType;
    }

    public void setBikeType(String bikeType) {
        this.bikeType = bikeType;
    }
}