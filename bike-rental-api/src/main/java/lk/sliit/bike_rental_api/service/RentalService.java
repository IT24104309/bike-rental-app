package lk.sliit.bike_rental_api.service;

import com.bikerental.model.RentalTransaction;
import com.bikerental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public RentalTransaction rentBike(String renterName, String username, String bikeId, 
                                    String bikeType, int hours, double hourlyRate, boolean isPremiumUser) 
                                    throws IOException {
        if (!rentalRepository.isBikeAvailable(bikeId)) {
            throw new IllegalStateException("Bike is not available");
        }
        String orderId = UUID.randomUUID().toString();
        RentalTransaction transaction = new RentalTransaction(orderId, renterName, username, 
                                                            bikeId, bikeType, hours, hourlyRate, isPremiumUser);
        rentalRepository.saveRental(transaction);
        return transaction;
    }

    public List<RentalTransaction> getAllRentals() throws IOException {
        return rentalRepository.getAllRentals();
    }

    public void confirmReturn(String orderId) throws IOException {
        rentalRepository.updateRental(orderId, "completed");
    }

    public void cancelRental(String orderId) throws IOException {
        rentalRepository.updateRental(orderId, "cancelled");
    }

    public void deleteCompletedRental(String orderId) throws IOException {
        rentalRepository.deleteCompletedRental(orderId);
    }
}