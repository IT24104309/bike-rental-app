package lk.sliit.bike_rental_api.service;

import lk.sliit.bike_rental_api.models.Bike;
import lk.sliit.bike_rental_api.models.BikeQueue;
import lk.sliit.bike_rental_api.models.RentalTransaction;
import lk.sliit.bike_rental_api.models.User;
import lk.sliit.bike_rental_api.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RentalService {

    @Autowired
    private BikeService bikeService;

    private final Map<String, BikeQueue> bikeQueues = new HashMap<>();

    public RentalTransaction rentBike(String username, String bikeId, int hours) throws IOException {

        Bike bike = bikeService.getBikeById(bikeId);
        if (bike == null) {
            throw new IllegalArgumentException("Bike not found.");
        }

        // If bike is not available, enqueue user instead
        if (!RentalRepository.isBikeAvailable(bikeId)) {
            // Create or fetch queue for this bike
            BikeQueue queue = bikeQueues.computeIfAbsent(bikeId, id -> new BikeQueue(bike, 10));
            queue.enqueue(new User(username));
            int ahead = queue.getSize() - 1; // user just enqueued, so all before them are ahead

            throw new IllegalStateException("Bike is not available. You have " + ahead + " person(s) ahead of you.");
        }

        double totalPrice = bike.getHourlyRate() * hours;
        String orderId = UUID.randomUUID().toString();

        RentalTransaction transaction = new RentalTransaction(orderId, username, bike.getBikeId(), bike.getType(), totalPrice);
        RentalRepository.saveRental(transaction);
        return transaction;
    }

    public List<RentalTransaction> getAllRentals() throws IOException {
        return RentalRepository.getAllRentals();
    }

    public void confirmReturn(String orderId) throws IOException {
        RentalRepository.updateRental(orderId, "completed");
        String bikeId = RentalRepository.getAllRentals().stream()
                .filter(t -> t.getOrderId().equals(orderId))
                .map(RentalTransaction::getBikeId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Rental not found"));
        BikeQueue queue = bikeQueues.get(bikeId);
        if (queue != null && queue.getSize() > 0) {
            User nextUser = queue.dequeue();
            // Alert the user or log their turn (e.g., send email or notification)
            System.out.println("User " + nextUser.getUsername() + " can now rent bike " + bikeId);
        }
    }

    public void cancelRental(String orderId) throws IOException {
        RentalRepository.updateRental(orderId, "cancelled");
    }

    public void deleteCompletedRental(String orderId) throws IOException {
        RentalRepository.deleteCompletedRental(orderId);
    }
}
