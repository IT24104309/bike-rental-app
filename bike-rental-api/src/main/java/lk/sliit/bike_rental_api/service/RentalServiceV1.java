package lk.sliit.bike_rental_api.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lk.sliit.bike_rental_api.enums.OrderStatus;
import lk.sliit.bike_rental_api.models.Bike;
import lk.sliit.bike_rental_api.models.RentRequest;
import lk.sliit.bike_rental_api.models.RentRequestQueue;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RentalServiceV1 {


    @Autowired
    private BikeService bikeService;

    private static final Logger logger = LoggerFactory.getLogger(RentalServiceV1.class);

    private static final String QUEUE_FILE_PATH = "queues.txt";
    private static final String PROCESSED_REQUESTS_FILE_PATH = "processed-requests.txt";

    private final Map<String, List<RentRequest>> processedRequestsMap = new HashMap<>();
    private final Map<String, RentRequestQueue> requestQueuesMap = new HashMap<>();

    private final ObjectMapper objectMapper = createObjectMapper();

    public RentalServiceV1() {
        loadProcessedRequests();
        loadQueues();
    }

    public synchronized String requestBike(RentRequest request) {
        String bikeId = request.getBikeId();
        String userId = request.getUserId();

        if (!bikeService.getBikeMap().containsKey(bikeId)) {
            return "Bike not found";
        }

        requestQueuesMap.putIfAbsent(bikeId, new RentRequestQueue(4000));
        RentRequestQueue queue = requestQueuesMap.get(bikeId);

        queue.insert(request); //enqueue
        saveQueues();

        Bike bike = bikeService.getBikeMap().get(bikeId);

        // If bike is available and this request is first in queue
        if (bike.isAvailable() && Objects.equals(queue.peekFront().getUserId(), userId)) {
            bike.setAvailable(false);
            queue.remove(); // assign bike// dequeue
            request.setStatus(OrderStatus.ONGOING);

            addToProcessedRequestMap(bikeId, request);

            saveQueues();
            saveProcessedRequests();
            bikeService.saveBikesToFile();
            return "Bike assigned immediately";
        }
        return "Added to waiting queue. Your position: " + queue.size();
    }

    public synchronized String releaseBike(String bikeId, String orderId) {
        if (!bikeService.getBikeMap().containsKey(bikeId)) {
            return "Bike not found";
        }

        List<RentRequest> processedRequests = processedRequestsMap.get(bikeId);
        Bike bike = bikeService.getBikeMap().get(bikeId);

        RentRequestQueue rentRequestsQueue = requestQueuesMap.get(bikeId);

        if (processedRequests != null && !processedRequests.isEmpty()) {
            List<RentRequest> filtered = processedRequests.stream().filter(processed -> processed.getOrderId() == orderId)
                    .collect(Collectors.toList());
            for (RentRequest rentRequest : filtered) {
                rentRequest.setStatus(OrderStatus.COMPLETED);
            }
            saveProcessedRequests();
            bike.setAvailable(true);
            bikeService.saveBikesToFile();
            logger.info("Bike is now marked as available");
        }

        if (rentRequestsQueue != null && !rentRequestsQueue.isEmpty()) {
            RentRequest nextRequest = rentRequestsQueue.remove(); // dequeue the next request
            nextRequest.setStatus(OrderStatus.ONGOING);

            addToProcessedRequestMap(bikeId, nextRequest);

            saveQueues();
            saveProcessedRequests();
            bikeService.saveBikesToFile();

            return "Bike assigned to next user: " + nextRequest.getUserId();
        } else {

            return "Request Queue is empty!";
        }
    }

    private void saveQueues() {
        try {
            objectMapper.writeValue(new File(QUEUE_FILE_PATH), requestQueuesMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadQueues() {
        try {
            File file = new File(QUEUE_FILE_PATH);
            if (file.exists() && file.length() > 0) {
                TypeReference<Map<String, RentRequestQueue>> typeRef = new TypeReference<>() {
                };
                Map<String, RentRequestQueue> loaded = objectMapper.readValue(file, typeRef);
                requestQueuesMap.putAll(loaded);
            } else {
                logger.info("Queue file is empty or does not contain orders.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<RentRequest> getOrdersByUserId(String userId) {
        List<RentRequest> userRequests = new ArrayList<>();
        for (RentRequestQueue queue : requestQueuesMap.values()) {
            for (int i = 0; i < queue.size(); i++) {
                RentRequest request = queue.get(i);
                if (request.getUserId().equals(userId)) {
                    userRequests.add(request);
                }
            }
        }

        var filteredProcessedOnes = processedRequestsMap.values().stream()
                .flatMap(Collection::stream)
                .filter(request -> request.getUserId().equals(userId))
                .toList();
        userRequests.addAll(filteredProcessedOnes);
        return userRequests;
    }

    // Ongoing or completed or canceled requests are keep inside these to track. (dequeued requests)
    private void loadProcessedRequests() {
        try {
            File file = new File(PROCESSED_REQUESTS_FILE_PATH);
            if (file.exists() && file.length() > 0) {
                TypeReference<Map<String, List<RentRequest>>> typeRef = new TypeReference<>() {
                };
                Map<String, List<RentRequest>> loaded = objectMapper.readValue(file, typeRef);
                processedRequestsMap.putAll(loaded);
            } else {
                logger.info("Processed requests file is empty or does not contain orders.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveProcessedRequests() {
        try {
            objectMapper.writeValue(new File(PROCESSED_REQUESTS_FILE_PATH), processedRequestsMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ObjectMapper createObjectMapper() {
        return new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private void addToProcessedRequestMap(String bikeId, RentRequest updateTheMap) {
        processedRequestsMap.putIfAbsent(bikeId, new ArrayList<>());
        List<RentRequest> processedRequests = processedRequestsMap.get(bikeId);
        processedRequests.add(updateTheMap);
    }


}