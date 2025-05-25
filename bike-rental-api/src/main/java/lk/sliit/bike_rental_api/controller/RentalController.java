package lk.sliit.bike_rental_api.controller;

import lk.sliit.bike_rental_api.enums.OrderStatus;
import lk.sliit.bike_rental_api.models.RentRequest;
import lk.sliit.bike_rental_api.service.BikeService;
import lk.sliit.bike_rental_api.service.RentalServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rental-management")
public class RentalController {

    @Autowired
    private RentalServiceV1 rentalService;

    @Autowired
    private BikeService bikeService;

    // ===========================
    // Rent Request: Queue or Assign
    // ===========================
    @PostMapping("/request")
    public ResponseEntity<String> requestBike(@RequestBody RentRequest request) {
        request.setOrderId(UUID.randomUUID().toString());
        request.setRequestTime(LocalDateTime.now());
        request.setStatus(OrderStatus.PROCESSING);
        String result = rentalService.requestBike(request);
        return ResponseEntity.ok(result);
    }

    // ===========================
    // Release: Return a Bike
    // ===========================
    @PostMapping("/release")
    public ResponseEntity<String> releaseBike(
            @RequestParam String bikeId, @RequestParam String orderId) {
        String result = rentalService.releaseBike(bikeId,orderId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/cancel")
    public ResponseEntity<String> cancelRequest(
            @RequestParam String bikeId, @RequestParam String orderId) {
        String result = rentalService.releaseBike(bikeId,orderId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get-orders/{userId}")
    public ResponseEntity<List<RentRequest>> getOrdersByUserId(@PathVariable String userId) {
        var orders = rentalService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
}
