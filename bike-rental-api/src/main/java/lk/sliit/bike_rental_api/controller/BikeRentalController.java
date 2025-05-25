package lk.sliit.bike_rental_api.controller;

import lk.sliit.bike_rental_api.models.Bike;
import lk.sliit.bike_rental_api.models.BikeSorter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bikes")
public class BikeRentalController {
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
}
