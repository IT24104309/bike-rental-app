package lk.sliit.bike_rental_api.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lk.sliit.bike_rental_api.models.User;
import lk.sliit.bike_rental_api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final String filePath = "users.txt";
    private final Map<String, User> userMap = new LinkedHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserService(){
        loadUsersFromFile();

    }
    public void registerUser(User user) {

       userMap.put(user.getUserID(), user);
       saveUsersToFile();

    }
    public User login(String username, String password) {
        Optional<User> match = userMap.values().stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst();

        if (match.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }
        User user = match.get();

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }
        logger.info("Login successful for user {}",user.getUsername());

        return user;
    }
    public User findByUsernameOrId(String key) {
        return userRepository.getAllUsers().stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(key) || user.getUserID().equals(key))
                .findFirst()
                .orElse(null);
    }

    public void updateUser(User updatedUser) {
       userMap.put(updatedUser.getUserID(), updatedUser);
       saveUsersToFile();
    }

    public void deleteUser(String userId) {
       userMap.remove(userId);
       saveUsersToFile();
    }

    public List<User> getAllUsers() {

        return new ArrayList<>(userMap.values());
    }

    private void saveUsersToFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), new ArrayList<>(userMap.values()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save users to file", e);
        }
    }
    private void loadUsersFromFile() {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) return;

        try {
            List<User> users = objectMapper.readValue(file, new TypeReference<>() {});
            for (User user : users) {
                userMap.put(user.getUserID(), user);
            }
        } catch (IOException e) {
            System.err.println("⚠️ Failed to load users from file: " + e.getMessage());
        }
    }
}
