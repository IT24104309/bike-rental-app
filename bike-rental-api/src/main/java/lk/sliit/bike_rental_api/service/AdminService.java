package lk.sliit.bike_rental_api.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lk.sliit.bike_rental_api.enums.AccountStatus;
import lk.sliit.bike_rental_api.enums.Role;
import lk.sliit.bike_rental_api.models.AdminUser;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.*;

@Service
public class AdminService {
    private static final String ADMIN_FILE_PATH = "Admin.txt";
    private final Map<String, AdminUser> adminMap = new LinkedHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public AdminService() {
        loadAdminsFromFile();
        if (adminMap.isEmpty()) {
            createAdmin(new AdminUser("A001", "Sayu Wanigasooriya", "S@gmail.com", Role.SUPER_ADMIN, AccountStatus.ACTIVE,"sayu123" ));
        }
    }

    public List<AdminUser> getAllAdmins() {
        return new ArrayList<>(adminMap.values());
    }

    public AdminUser login(String email, String password) {
        Optional<AdminUser> match = adminMap.values().stream()
                .filter(admin -> admin.getEmail().equalsIgnoreCase(email))
                .findFirst();

        if (match.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }

        AdminUser admin = match.get();

        if (admin.getStatus() != AccountStatus.ACTIVE) {
            throw new RuntimeException("Account is inactive");
        }

        if (!admin.getPassword().equals(password)) {
            throw new RuntimeException("Invalid email or password");
        }


        return admin;
    }

    public AdminUser getAdminById(String id) {
        return adminMap.get(id);
    }

    public void createAdmin(AdminUser admin) {
        adminMap.put(admin.getId(), admin);
        saveAdminsToFile();
    }

    public void updateAdmin(AdminUser updated) {
        adminMap.put(updated.getId(), updated);
        saveAdminsToFile();
    }

    public void deleteAdmin(String id) {
        adminMap.remove(id);
        saveAdminsToFile();
    }
    private void loadAdminsFromFile() {
        File file = new File(ADMIN_FILE_PATH);
        if (!file.exists() || file.length() == 0) return;

        try {
            List<AdminUser> admins = objectMapper.readValue(file, new TypeReference<>() {});
            for (AdminUser admin : admins) {
                adminMap.put(admin.getId(), admin);
            }
        } catch (IOException e) {
            System.err.println("⚠️ Failed to load admins from file: " + e.getMessage());
        }
    }

    private void saveAdminsToFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(ADMIN_FILE_PATH), new ArrayList<>(adminMap.values()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save admins to file", e);
        }
    }
}