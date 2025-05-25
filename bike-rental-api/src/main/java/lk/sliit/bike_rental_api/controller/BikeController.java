package lk.sliit.bike_rental_api.controller;

import lk.sliit.bike_rental_api.models.Bike;
import lk.sliit.bike_rental_api.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bike-management")
public class BikeController {

    @Autowired
    public BikeService bikeService;


    @PostMapping("/Bikes")
    public ResponseEntity<String> addBike(@RequestBody Bike bike) {
        bike.setBikeId(UUID.randomUUID().toString());
        bikeService.addBike(bike);
        return ResponseEntity.ok("Bike added successfully!");
    }

    // Read (GET)
    @GetMapping("/Bikes")
    public ResponseEntity<List<Bike>> getAllBikes() {
        List<Bike> bikes = bikeService.getAllBikes();
        return ResponseEntity.ok(bikes);
    }

    // Read by ID (GET)
    @GetMapping("/{bikeId}")
    public ResponseEntity<Bike> getBikeById(@PathVariable String bikeId) {
        Bike bike = bikeService.getBikeById(bikeId);
        if (bike != null) {
            return ResponseEntity.ok(bike);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update (PUT)
    @PutMapping("/{bikeId}")
    public ResponseEntity<String> updateBike(@PathVariable String bikeId, @RequestBody Bike updatedBike) {
        boolean updated = bikeService.updateBike(bikeId, updatedBike);
        if (updated) {
            return ResponseEntity.ok("Bike updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete (DELETE)
    @DeleteMapping("/{bikeId}")
    public ResponseEntity<String> deleteBike(@PathVariable String bikeId) {
        boolean deleted = bikeService.deleteBike(bikeId);
        if (deleted) {
            return ResponseEntity.ok("Bike deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
