package lk.sliit.bike_rental_api.controller;

import com.bikerental.model.RentalTransaction;
import com.bikerental.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/bikes")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    // Create: Rent a bike
    @PostMapping("/rent")
    public ResponseEntity<RentalTransaction> rentBike(
            @RequestParam String renterName,
            @RequestParam String username,
            @RequestParam String bikeId,
            @RequestParam String bikeType,
            @RequestParam int hours,
            @RequestParam double hourlyRate,
            @RequestParam boolean isPremiumUser) throws IOException {
        RentalTransaction transaction = rentalService.rentBike(renterName, username, bikeId, 
                                                           bikeType, hours, hourlyRate, isPremiumUser);
        return ResponseEntity.ok(transaction);
    }

    // Read: Get all rentals
    @GetMapping("/rentals")
    public ResponseEntity<List<RentalTransaction>> getAllRentals() throws IOException {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    // Update: Confirm bike return
    @PutMapping("/return/{orderId}")
    public ResponseEntity<String> confirmReturn(@PathVariable String orderId) throws IOException {
        rentalService.confirmReturn(orderId);
        return ResponseEntity.ok("Bike returned successfully");
    }

    // Update: Cancel rental
    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelRental(@PathVariable String orderId) throws IOException {
        rentalService.cancelRental(orderId);
        return ResponseEntity.ok("Rental cancelled successfully");
    }

    // Delete: Remove completed rental
    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<String> deleteRental(@PathVariable String orderId) throws IOException {
        rentalService.deleteCompletedRental(orderId);
        return ResponseEntity.ok("Completed rental deleted successfully");
    }
}