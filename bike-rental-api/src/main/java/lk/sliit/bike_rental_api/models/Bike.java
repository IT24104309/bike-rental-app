package lk.sliit.bike_rental_api.models;

import java.io.Serializable;

public class Bike implements Serializable {
    private static final long serialVersionUID = 1L;

    private String bikeId;
    private String type;
    private String status;
    private double hourlyRate;
    private String registrationNumber;
    private String model;

    public Bike() {
    }


    public Bike(String bikeId, String type, String status, double hourlyRate, String registrationNumber, String model) {
        this.bikeId = bikeId;
        this.type = type;
        this.status = status;
        this.hourlyRate = hourlyRate;
        this.registrationNumber = registrationNumber;
        this.model = model;
    }


    public String getBikeId() {
        return bikeId;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }


    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
