package lk.sliit.bike_rental_api.controller;

import lk.sliit.bike_rental_api.models.Bike;
import lk.sliit.bike_rental_api.models.BikeSorter;
import lk.sliit.bike_rental_api.models.RentalTransaction;
import lk.sliit.bike_rental_api.service.BikeService;
import lk.sliit.bike_rental_api.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bikes")
public class BikeRentalController {

    @Autowired
    private RentalService rentalService;
    @Autowired
    private BikeService bikeService;

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableBikes(@RequestParam String bikeType) {
        String filePath = "bikes.txt";
        List<Bike> bikes = BikeSorter.getBikesByType(filePath, bikeType);
        List<BikeDTO> bikeDTOs = bikes.stream().map(bike -> new BikeDTO(
                bike.getBikeId(),
                bike.getType(),
                bike.getHourlyRate()
        )).collect(Collectors.toList());
        return ResponseEntity.ok(bikeDTOs);
    }

    public static class BikeDTO {
        private String bikeId;
        private String bikeType;
        private double hourlyRate;

        public BikeDTO(String bikeId, String bikeType, double hourlyRate) {
            this.bikeId = bikeId;
            this.bikeType = bikeType;
            this.hourlyRate = hourlyRate;
        }

        public String getBikeId() { return bikeId; }
        public String getBikeType() { return bikeType; }
        public double getHourlyRate() { return hourlyRate; }
    }

    public static class ErrorResponse {
        private String message;
        public ErrorResponse(String message) { this.message = message; }
        public String getMessage() { return message; }
    }

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
}
