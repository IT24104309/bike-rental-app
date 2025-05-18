package lk.sliit.bike_rental_api.repository;

import lk.sliit.bike_rental_api.models.Review;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ReviewRepository {

    @Value("${app.data.file}")
    private String dataFile;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Review save(Review review) throws IOException {
        List<Review> reviews = findAll();
        if (review.getId() == 0){
            Long newId = reviews.isEmpty() ? 1L : reviews.get(reviews.size() - 1).getId() + 1;
            review.setId(newId);
            reviews.add(review);
        } else {
            Optional<Review> existing = reviews.stream()
                    .filter(s -> (s.getId() == review.getId()))
                    .findFirst();
            if (existing.isPresent()){
                reviews.remove(existing.get());
                reviews.add(review);
            } else {
                throw new RuntimeException("Review not found with id: " + review.getId());
            }
        }
        writeToFile(reviews);
        return review;
    }

    public Optional<Review> findById(Long id) throws IOException {
        List<Review> reviews = findAll();
        return reviews.stream().filter(review -> (review.getId() == id)).findFirst();
    }

    public List<Review> findAll() throws IOException {
        File file = new File(dataFile.replace("classpath:", "src/main/resources/"));
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(file, new TypeReference<List<Review>>() {});
    }

    public void delete(Long id) throws IOException {
        List<Review> reviews = findAll();
        boolean removed = reviews.removeIf(review -> review.getId() == (id));
        if (!removed) {
            throw new RuntimeException("Review not found with id " + id);
        }
        writeToFile(reviews);
    }

    private void writeToFile(List<Review> reviews) throws IOException {
        File file = new File(dataFile.replace("classpath:", "src/main/resources/"));
        objectMapper.writeValue(file, reviews);
    }
}