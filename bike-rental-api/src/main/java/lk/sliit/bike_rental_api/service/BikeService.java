package lk.sliit.bike_rental_api.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lk.sliit.bike_rental_api.models.AdminUser;
import lk.sliit.bike_rental_api.models.Bike;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BikeService {
    private static final String filePath = "bikes.txt";
    private final Map<String, Bike> bikeMap = new LinkedHashMap<>();//my key is admin id & my value is adminuser
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BikeService() {
        loadBikesFromFile();
    }

    public void addBike(Bike bike) {
        //VERIFY THE BIKE REGISTRATION NUMBER BEFORE SAVE
        boolean exists = bikeMap.values().stream()
                .anyMatch(existingBike -> existingBike.getRegistrationNumber().equalsIgnoreCase(bike.getRegistrationNumber()));

        if (exists) {
            throw new IllegalArgumentException("A bike with this registration number already exists.");
        }
        bikeMap.put(bike.getBikeId(), bike);
        saveBikesToFile();
    }

    public List<Bike> getAllBikes() {
        return new ArrayList<>(bikeMap.values());
    }

    public Bike getBikeById(String bikeId) {
        return bikeMap.get(bikeId);
    }

    public boolean updateBike(String bikeId, Bike updatedBike) {
        bikeMap.put(updatedBike.getBikeId(), updatedBike);
        saveBikesToFile();
        return true;
    }

    public boolean deleteBike(String bikeId) {
        bikeMap.remove(bikeId);
        saveBikesToFile();
        return true;
    }

    private void loadBikesFromFile() {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) return;

        try {
            List<Bike> bikes = objectMapper.readValue(file, new TypeReference<>() {
            });
            for (Bike bike : bikes) {
                bikeMap.put(bike.getBikeId(), bike);
            }
        } catch (IOException e) {
            System.err.println("⚠️ Failed to load bikes from file: " + e.getMessage());
        }
    }

    private void saveBikesToFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), new ArrayList<>(bikeMap.values()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save admins to file", e);
        }
    }
}