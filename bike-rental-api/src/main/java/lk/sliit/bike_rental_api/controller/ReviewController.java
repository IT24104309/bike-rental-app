package lk.sliit.bike_rental_api.controller;

import lk.sliit.bike_rental_api.models.Review;
import lk.sliit.bike_rental_api.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review-management")

public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> getAll() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public Review getOne(@PathVariable String id) {
        return reviewService.getReviewById(id);
    }

    @PostMapping
    public void create(@RequestBody Review review) {
        reviewService.createReview(review);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable String id, @RequestBody Review review) {
        reviewService.updateReview(review);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        reviewService.deleteReview(id);
    }

}
