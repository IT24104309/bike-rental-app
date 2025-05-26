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

    //map has key value pair, created a map from RentRequestQueue
    private final Map<String, RentRequestQueue> requestQueuesMap = new HashMap<>();
    //dequeued orders saving here
    private final Map<String, List<RentRequest>> processedRequestsMap = new HashMap<>();


    //object converting for text files
    private final ObjectMapper objectMapper = createObjectMapper();

    public RentalServiceV1() {
        loadProcessedRequests();
        loadQueues();
    }
    //to allow multiple access without having confusion
    public synchronized String requestBike(RentRequest request) {
        String bikeId = request.getBikeId();
        String userId = request.getUserId();

        if (!bikeService.getBikeMap().containsKey(bikeId)) {
            return "Bike not found";
        }
        //putIfAbsent== in the map if there is no any req from this passing bikeId
        requestQueuesMap.putIfAbsent(bikeId, new RentRequestQueue(4000));
        RentRequestQueue queue = requestQueuesMap.get(bikeId);//calling queue according to the bikeId

        queue.insert(request); //enqueue
        saveQueues();//queue is saving to file

        Bike bike = bikeService.getBikeMap().get(bikeId);//getting the bike related to bikeId from bike map and assign to 'bike'

        // If bike is available and this request is first in queue
        if (bike.isAvailable() && Objects.equals(queue.peekFront().getUserId(), userId)) { //requested user is in front in queue
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


        RentRequestQueue rentRequestsQueue = requestQueuesMap.get(bikeId);

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

    //saving to file
    private void saveQueues() {
        try {
            Map<String, List<RentRequest>> simpleMap = new HashMap<>();
            for (Map.Entry<String, RentRequestQueue> entry : requestQueuesMap.entrySet()) {
                simpleMap.put(entry.getKey(), entry.getValue().toList());
            }
            objectMapper.writeValue(new File(QUEUE_FILE_PATH), simpleMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //loading from file
    private void loadQueues() {
        try {
            File file = new File(QUEUE_FILE_PATH);
            if (file.exists() && file.length() > 0) {
                TypeReference<Map<String, List<RentRequest>>> typeRef = new TypeReference<>() {};
                Map<String, List<RentRequest>> loaded = objectMapper.readValue(file, typeRef);
                for (Map.Entry<String, List<RentRequest>> entry : loaded.entrySet()) {
                    RentRequestQueue queue = RentRequestQueue.fromList(entry.getValue(), 4000); // match your maxSize
                    requestQueuesMap.put(entry.getKey(), queue);
                }
            } else {
                logger.info("Queue file is empty or does not contain orders.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //making the past orders table
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

    //load from file(Process-req.txt)
    // Ongoing or completed or canceled requests are keep inside these to track. (dequeued requests)
    private void loadProcessedRequests() {
        try {
            File file = new File(PROCESSED_REQUESTS_FILE_PATH);
            if (file.exists() && file.length() > 0) {
                TypeReference<Map<String, List<RentRequest>>> typeRef = new TypeReference<>() { //typeRfe is a template for object saving
                };
                Map<String, List<RentRequest>> loaded = objectMapper.readValue(file, typeRef);//read file
                processedRequestsMap.putAll(loaded);
            } else {
                logger.info("Processed requests file is empty or does not contain orders.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//save to file (process-req.txt)
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
                .registerModule(new JavaTimeModule())//to save local date to file
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private void addToProcessedRequestMap(String bikeId, RentRequest updateTheMap) {
        processedRequestsMap.putIfAbsent(bikeId, new ArrayList<>());
        List<RentRequest> processedRequests = processedRequestsMap.get(bikeId);
        processedRequests.add(updateTheMap);
    }


}