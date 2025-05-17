package lk.sliit.bike_rental_api.service;

import lk.sliit.bike_rental_api.enums.AccountStatus;
import lk.sliit.bike_rental_api.enums.Role;
import lk.sliit.bike_rental_api.models.AdminUser;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {
    private final Map<String, AdminUser> adminMap = new LinkedHashMap<>();

    public AdminService() {
        // Preload admins
        createAdmin(new AdminUser("A001", "John Smith", "john.smith@example.com", Role.SUPER_ADMIN, AccountStatus.ACTIVE));
        createAdmin(new AdminUser("A002", "Jane Doe", "jane.doe@example.com", Role.MODERATOR, AccountStatus.INACTIVE));
        createAdmin(new AdminUser("A003", "Bob Johnson", "bob.johnson@example.com", Role.ADMIN, AccountStatus.ACTIVE));
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

        // For demo purposes: password = firstName(lowercase) + 123
        String expectedPassword = admin.getName().split(" ")[0].toLowerCase() + "123";
        if (!expectedPassword.equals(password)) {
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