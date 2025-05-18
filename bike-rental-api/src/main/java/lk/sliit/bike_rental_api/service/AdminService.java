package lk.sliit.bike_rental_api.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import lk.sliit.bike_rental_api.enums.AccountStatus;
import lk.sliit.bike_rental_api.enums.Role;
import lk.sliit.bike_rental_api.models.AdminUser;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {
    private final Map<String, AdminUser> adminMap = new LinkedHashMap<>();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public AdminService() {
        // Preload admins with encrypted passwords
        createAdmin(new AdminUser("A001", "John Smith", "john.smith@example.com", Role.SUPER_ADMIN, AccountStatus.ACTIVE, passwordEncoder.encode("john123")));
        createAdmin(new AdminUser("A002", "Jane Doe", "jane.doe@example.com", Role.MODERATOR, AccountStatus.INACTIVE, passwordEncoder.encode("jane123")));
        createAdmin(new AdminUser("A003", "Bob Johnson", "bob.johnson@example.com", Role.ADMIN, AccountStatus.ACTIVE, passwordEncoder.encode("bob123")));
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

        // ✅ SECURE: Match raw password against hashed password
        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }


        return admin;
    }


    public AdminUser getAdminById(String id) {
        return adminMap.get(id);
    }

    public void createAdmin(AdminUser admin) {
        adminMap.put(admin.getId(), admin);
    }

    public void updateAdmin(AdminUser updated) {
        adminMap.put(updated.getId(), updated);
    }

    public void deleteAdmin(String id) {
        adminMap.remove(id);
    }

}