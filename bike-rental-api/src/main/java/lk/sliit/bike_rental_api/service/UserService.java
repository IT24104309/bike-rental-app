package lk.sliit.bike_rental_api.service;


import lk.sliit.bike_rental_api.models.User;
import lk.sliit.bike_rental_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void registerUser(User user) {
        userRepository.saveUser(user);
    }

    public User findByUsernameOrId(String key) {
        return userRepository.getAllUsers().stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(key) || user.getUserID().equals(key))
                .findFirst()
                .orElse(null);
    }

    public void updateUser(User updatedUser) {
        List<User> users = userRepository.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserID().equals(updatedUser.getUserID())) {
                users.set(i, updatedUser);
                break;
            }
        }
        userRepository.updateUsers(users);
    }

    public void deleteUser(String userId) {
        List<User> users = userRepository.getAllUsers();
        users.removeIf(user -> user.getUserID().equals(userId));
        userRepository.updateUsers(users);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}

