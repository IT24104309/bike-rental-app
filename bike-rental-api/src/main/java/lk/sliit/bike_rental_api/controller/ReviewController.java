package lk.sliit.bike_rental_api.controller;

import lk.sliit.bike_rental_api.models.Review; // entity
import lk.sliit.bike_rental_api.service.ReviewService; // service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review-management")

public class ReviewController {

    @Autowired
    private final ReviewService reviewService;

    @GetMapping
    public List<Review> getAll() {
        return reviewService.getAllReviews();
    }

    /*
    @GetMapping
    public String listReviews(Model model) {
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "review-list";
    }
     */

    // create review form
    /*
    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("review", new Review());
        return "review-student";
    }
    */

    // handle create reviews
    @PostMapping
    public void create(@RequestBody Review review) {
        reviewService.createReview(review);
    }

    /*
    @PostMapping("/create")
    public String createReview(@ModelAttribute Review review){
        reviewService.createReview(review);
        return "redirect:/reviews";
    }
     */

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }



    @GetMapping("/{id}")
    public Review getOne(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }



    @PutMapping("/{id}")
    public void update(@PathVariable String id, @RequestBody Review review) {
        reviewService.updateReview(review);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        reviewService.deleteReview(id);
        // return "redirect:/reviews";
    }
}