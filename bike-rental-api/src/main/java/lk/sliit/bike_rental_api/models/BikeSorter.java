package lk.sliit.bike_rental_api.models;

import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BikeSorter {

    private static int partition(List<Bike> bikes, int low, int high) {
        String pivot = bikes.get(high).getType();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (bikes.get(j).getType().compareToIgnoreCase(pivot) <= 0) {
                i++;
                Bike temp = bikes.get(i);
                bikes.set(i, bikes.get(j));
                bikes.set(j, temp);
            }
        }
        Bike temp = bikes.get(i + 1);
        bikes.set(i + 1, bikes.get(high));
        bikes.set(high, temp);
        return i + 1;
    }

    private static void quickSort(List<Bike> bikes, int low, int high) {
        if (low < high) {
            int pi = partition(bikes, low, high);
            quickSort(bikes, low, pi - 1);
            quickSort(bikes, pi + 1, high);
        }
    }

    public static List<Bike> getBikesByType(String filePath, String bikeType) {
        ObjectMapper mapper = new ObjectMapper();
        List<Bike> bikes = new ArrayList<>();

        Bike[] bikeArray = null;
        try {
            bikeArray = mapper.readValue(new File(filePath), Bike[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Bike bike: bikeArray) {
            if (bike.getType() != null && bike.getType().equalsIgnoreCase(bikeType)) {
                bikes.add(bike);
            }
        }
        quickSort(bikes, 0, bikes.size()-1);
        return bikes;
    }
}