package lk.sliit.bike_rental_api.service;

import lk.sliit.bike_rental_api.model.Bike;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BikeService {

    private final List<Bike> bikes = new ArrayList<>();

    public void addBike(Bike bike) {
        bikes.add(bike);
    }

    public List<Bike> getAllBikes() {
        return bikes;
    }

    public Bike getBikeById(String bikeId) {
        return bikes.stream()
                .filter(b -> b.getBikeId().equalsIgnoreCase(bikeId))
                .findFirst()
                .orElse(null);
    }

    public boolean updateBike(String bikeId, Bike updatedBike) {
        Bike existingBike = getBikeById(bikeId);
        if (existingBike != null) {
            existingBike.setType(updatedBike.getType());
            existingBike.setStatus(updatedBike.getStatus());
            return true;
        }
        return false;
    }

    public boolean deleteBike(String bikeId) {
        return bikes.removeIf(b -> b.getBikeId().equalsIgnoreCase(bikeId));
    }
}
