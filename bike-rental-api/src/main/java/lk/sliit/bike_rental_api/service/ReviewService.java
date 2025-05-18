package lk.sliit.bike_rental_api.service;

import lk.sliit.bike_rental_api.models.Review;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {
    private final Map<String, Review> reviewMap = new LinkedHashMap<>();

    public ReviewService() {
        createReview(new Review(100000001, "JohnSmith", 5, "Great Service!"));
        createReview(new Review(100000002, "JaneDoe", 3, "Tire is worn out.."));
        createReview(new Review(100000003, "BobJohnson", 1, "Too expensive!!"));
    }

    public List<Review> getAllReviews() {
        return new ArrayList<>(reviewMap.values());
    }

    public Review getReviewById(long id) {
        return reviewMap.get(id);
    }

    public void createReview(Review review) {
        reviewMap.put(review.getId()+"", review);
    }

    public void updateReview(Review reviewUpdate) {
        reviewMap.put(reviewUpdate.getId()+"", reviewUpdate);
    }

    public void deleteReview(String id) {
        reviewMap.remove(id);
    }
}