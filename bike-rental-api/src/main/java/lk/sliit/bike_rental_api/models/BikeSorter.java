package lk.sliit.bike_rental_api.models;

import java.util.*;

public class BikeSorter {

    private List<Bike> bikeList;

    public BikeSorter(List<Bike> bikeList) {
        this.bikeList = bikeList;
    }

    private void swap(int i, int j) {
        Bike temp = bikeList.get(i);
        bikeList.set(i, bikeList.get(j));
        bikeList.set(j, temp);
    }

    private int partition(int low, int high) {
        int pivot = Integer.parseInt(bikeList.get(high).getBikeId());
        int i = low - 1;
        for (int j = low; j <= high; j++) {
            int compareId = Integer.parseInt(bikeList.get(j).getBikeId());
            if (compareId < pivot) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    public List<Bike> quickSortByType(String type) {
        List<Bike> tempList = bikeList;
        bikeList = new ArrayList<Bike>();
        for (Bike bike: tempList) {
            if (!bike.getType().equals(type)) continue;
            bikeList.add(bike);
        }
        quickSort(0, bikeList.size());
        return bikeList;
    }
}
