package lk.sliit.bike_rental_api.service;

import lk.sliit.bike_rental_api.models.Review;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReviewService {
    private final Map<String, Review> reviewMap = new LinkedHashMap<>();

    public ReviewService() {
        createReview(new Review("R001", "John Smith", 5, "Great Service!"));
        createReview(new Review("R002", "Jane Doe", 3, "Tire is worn out.."));
        createReview(new Review("R003", "Bob Johnson", 1, "Too expensive!!"));
    }

    public List<Review> getAllReviews() {
        return new ArrayList<>(reviewMap.values());
    }

    public Review getReviewById(String id) {
        return reviewMap.get(id);
    }

    public void createReview(Review review) {
        reviewMap.put(review.getId(), review);
    }

    public void updateReview(Review reviewUpdate) {
        reviewMap.put(reviewUpdate.getId(), reviewUpdate);
    }

    public void deleteReview(String id) {
        reviewMap.remove(id);
    }
}