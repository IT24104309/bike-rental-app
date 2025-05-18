package lk.sliit.bike_rental_api.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lk.sliit.bike_rental_api.models.AdminUser;
import lk.sliit.bike_rental_api.models.Review;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.io.*;
import java.util.*;

@Service
public class ReviewService {
    private static final String REVIEW_FILE_PATH = "reviews.txt";
    private final Map<String, Review> reviewMap = new LinkedHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ReviewService() {
        loadReviewsFromFile();
        if (reviewMap.isEmpty()) {
            createReview(new Review(100000001, "JohnSmith", 5, "Great Service!"));
            createReview(new Review(100000002, "JaneDoe", 3, "Tire is worn out.."));
            createReview(new Review(100000003, "BobJohnson", 1, "Too expensive!!"));
        }
    }

    public List<Review> getAllReviews() {
        return new ArrayList<>(reviewMap.values());
    }

    public Review getReviewById(long id) {
        return reviewMap.get(id);
    }

    public void createReview(Review review) {
        reviewMap.put(review.getId()+"", review);
        saveReviewsToFile();
    }

    public void updateReview(Review reviewUpdate) {
        reviewMap.put(reviewUpdate.getId()+"", reviewUpdate);
        saveReviewsToFile();
    }

    public void deleteReview(String id) {
        reviewMap.remove(id);
        saveReviewsToFile();
    }

    private void loadReviewsFromFile() {
        File file = new File(REVIEW_FILE_PATH);
        if (!file.exists() || file.length() == 0) return;

        try {
            List<Review> reviews = objectMapper.readValue(file, new TypeReference<>() {});
            for (Review review : reviews) {
                reviewMap.put(review.getId()+"", review);
            }
        } catch (IOException e) {
            System.err.println("⚠️ Failed to load reviews from file: " + e.getMessage());
        }
    }

    private void saveReviewsToFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(REVIEW_FILE_PATH), new ArrayList<>(reviewMap.values()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save reviews to file", e);
        }
    }
}