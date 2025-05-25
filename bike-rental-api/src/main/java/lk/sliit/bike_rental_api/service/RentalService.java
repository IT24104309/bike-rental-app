package lk.sliit.bike_rental_api.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lk.sliit.bike_rental_api.models.*;
import lk.sliit.bike_rental_api.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class RentalService {

    @Autowired
    private BikeService bikeService;

    private static final String QUEUE_FILE_PATH = "queues.txt";
    private static final String BIKE_FILE_PATH = "bikes.txt";

    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private final Map<String, Queue<RentRequest>> bikeQueues = new HashMap<>();
    private final Map<String, Bike> bikeMap = new HashMap<>();

    public RentalService() {
        loadBikes();
        loadQueues();
    }

    public synchronized String requestBike(String bikeId, String userId) {
        if (!bikeMap.containsKey(bikeId)) return "Bike not found";

        bikeQueues.putIfAbsent(bikeId, new LinkedList<>());
        Queue<RentRequest> queue = bikeQueues.get(bikeId);
        queue.offer(new RentRequest(userId));

        saveQueues();

        Bike bike = bikeMap.get(bikeId);
        if (bike.isAvailable() && queue.peek().getUserId().equals(userId)) {
            bike.setAvailable(false);
            queue.poll(); // assign bike
            saveQueues();
            saveBikes();
            return "Bike assigned immediately";
        }
        return "Added to waiting queue. Your position: " + queue.size();
    }
    public synchronized String releaseBike(String bikeId) {
        if (!bikeMap.containsKey(bikeId)) return "Bike not found";

        Queue<RentRequest> queue = bikeQueues.get(bikeId);
        Bike bike = bikeMap.get(bikeId);

        if (queue != null && !queue.isEmpty()) {
            RentRequest next = queue.poll();
            saveQueues();
            // Assign to next user
            return "Bike assigned to next in queue: " + next.getUserId();
        } else {
            bike.setAvailable(true);
            saveBikes();
            return "Bike is now available";
        }
    }

    private void saveQueues() {
        try {
            objectMapper.writeValue(new File(QUEUE_FILE_PATH), bikeQueues);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadQueues() {
        try {
            File file = new File(QUEUE_FILE_PATH);
            if (file.exists()) {
                TypeReference<Map<String, LinkedList<RentRequest>>> typeRef = new TypeReference<>() {};
                Map<String, LinkedList<RentRequest>> loaded = objectMapper.readValue(file, typeRef);
                bikeQueues.putAll(loaded);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBikes() {
        try {
            objectMapper.writeValue(new File(BIKE_FILE_PATH), bikeMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBikes() {
        try {
            File file = new File(BIKE_FILE_PATH);
            if (file.exists()) {
                TypeReference<List<Bike>> typeRef = new TypeReference<>() {};
                List<Bike> loadedBikes = objectMapper.readValue(file, typeRef);
                for (Bike bike : loadedBikes) {
                    bikeMap.put(bike.getBikeId(), bike);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addBike(Bike bike) {
        bikeMap.put(bike.getBikeId(), bike);
        saveBikes();
    }

    public List<RentRequest> getQueue(String bikeId) {
        return new ArrayList<>(bikeQueues.getOrDefault(bikeId, new LinkedList<>()));
    }
}
