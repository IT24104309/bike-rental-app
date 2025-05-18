package lk.sliit.bike_rental_api.repository;


import lk.sliit.bike_rental_api.models.User;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
public class UserRepository {

    private final String filePath = "users.txt";

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    User user = new User(data[0], data[1], data[2], data[3], data[4], data[5]);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void saveUser(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            String data = String.join(",", user.getUserID(), user.getUsername(), user.getPassword(),
                    user.getEmail(), user.getPhoneNumber(), user.getUserType());
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUsers(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : users) {
                String data = String.join(",", user.getUserID(), user.getUsername(), user.getPassword(),
                        user.getEmail(), user.getPhoneNumber(), user.getUserType());
                bw.write(data);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

