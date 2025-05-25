package lk.sliit.bike_rental_api.models;

import java.time.LocalDateTime;

public class RentRequest {
    private String userId;
    private LocalDateTime requestTime;
    private int hours;
    private String bikeId;

    public RentRequest(String userId) {
        this.userId = userId;
        this.requestTime = LocalDateTime.now();
        this.hours=hours;
        this.bikeId=bikeId;
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

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }
}