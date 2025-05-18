package lk.sliit.bike_rental_api.models;

import java.io.Serializable;

public class Bike implements Serializable {
    private static final long serialVersionUID = 1L;

    private String bikeId;
    private String type;
    private String status;


    public Bike() {
    }


    public Bike(String bikeId, String type, String status) {
        this.bikeId = bikeId;
        this.type = type;
        this.status = status;
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


}
