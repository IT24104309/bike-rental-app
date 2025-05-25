/* package lk.sliit.bike_rental_api.controller;

import lk.sliit.bike_rental_api.models.Bike;
import lk.sliit.bike_rental_api.models.RentalTransaction;
import lk.sliit.bike_rental_api.service.BikeService;
import lk.sliit.bike_rental_api.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/orders")
public class RentalController {

    @Autowired
    private RentalService rentalService;
    @Autowired
    private BikeService bikeService;

    // Create: Rent a bike
    @PostMapping("/rent")
    public ResponseEntity<RentalTransaction> rentBike(
            @RequestParam String username,
            @RequestParam String bikeId,
            @RequestParam int hours) throws IOException {
        RentalTransaction transaction = rentalService.rentBike(username, bikeId, hours);
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

    // New: Get available bikes by type
    @GetMapping("/available")
    public ResponseEntity<List<Bike>> getAvailableBikes(@RequestParam(required = false) String bikeType) throws IOException {
        List<Bike> allBikes = bikeService.getAllBikes();

        List<RentalTransaction> rentals = rentalService.getAllRentals();
        List<String> rentedBikeIds = rentals.stream()
                .filter(t -> t.getStatus().equals("ongoing"))
                .map(RentalTransaction::getBikeId)
                .toList();
        List<Bike> availableBikes = allBikes.stream()
                .filter(bike -> !rentedBikeIds.contains(bike.getBikeId()))
                .filter(bike -> bikeType == null || bike.getType().equalsIgnoreCase(bikeType))
                .toList();
        return ResponseEntity.ok(availableBikes);
    }


}
 */