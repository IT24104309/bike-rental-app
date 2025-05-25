package lk.sliit.bike_rental_api.controller;

import lk.sliit.bike_rental_api.models.Bike;
import lk.sliit.bike_rental_api.models.RentRequest;
import lk.sliit.bike_rental_api.service.BikeService;
import lk.sliit.bike_rental_api.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private BikeService bikeService;

    // ===========================
    // Rent Request: Queue or Assign
    // ===========================
    @PostMapping("/request")
    public ResponseEntity<String> requestBike(
            @RequestParam String userId,
            @RequestParam String bikeId) {
        String result = rentalService.requestBike(bikeId, userId);
        return ResponseEntity.ok(result);
    }

    // ===========================
    // Release: Return a Bike
    // ===========================
    @PostMapping("/release")
    public ResponseEntity<String> releaseBike(
            @RequestParam String bikeId) {
        String result = rentalService.releaseBike(bikeId);
        return ResponseEntity.ok(result);
    }

    // ===========================
    // View Queue for a Bike
    // ===========================
    @GetMapping("/queue/{bikeId}")
    public ResponseEntity<List<RentRequest>> getQueue(@PathVariable String bikeId) {
        List<RentRequest> queue = rentalService.getQueue(bikeId);
        return ResponseEntity.ok(queue);
    }

    // ===========================
    // Add New Bike
    // ===========================
    @PostMapping("/add-bike")
    public ResponseEntity<String> addBike(@RequestBody Bike bike) {
        rentalService.addBike(bike);
        return ResponseEntity.ok("Bike added successfully.");
    }

    // ===========================
    // List All Bikes (Optional)
    // ===========================
    @GetMapping("/bikes")
    public ResponseEntity<List<Bike>> getAllBikes() {
        return ResponseEntity.ok(bikeService.getAllBikes());
    }
}
