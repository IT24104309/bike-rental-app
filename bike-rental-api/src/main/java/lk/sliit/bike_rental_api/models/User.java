package lk.sliit.bike_rental_api.models;

import java.util.List;

public class User {
    private int rideCount;
    private String userType;
    private List<String> profiles;
    private int lastRideDaysAgo;

    public User(int rideCount, String userType, List<String> profiles, int lastRideDaysAgo) {
        this.rideCount = rideCount;
        this.userType = userType;
        this.profiles = profiles;
        this.lastRideDaysAgo = lastRideDaysAgo;
    }

    public int getRideCount() { return rideCount; }
    public String getUserType() { return userType; }
    public List<String> getProfiles() { return profiles; }
    public int getDaysSinceLastRide() { return lastRideDaysAgo; }
}

