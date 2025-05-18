package lk.sliit.bike_rental_api.service;

import lk.sliit.bike_rental_api.enums.AccountStatus;
import lk.sliit.bike_rental_api.enums.Role;
import lk.sliit.bike_rental_api.models.AdminUser;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class AdminService {
    private static final String ADMIN_FILE_PATH = "./store/Admin.txt";
    private final Map<String, AdminUser> adminMap = new LinkedHashMap<>();


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
        if (!file.exists() || file.length() == 0) return; // 🔐 skip if empty

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                List<?> list = (List<?>) obj;
                for (Object item : list) {
                    if (item instanceof AdminUser) {
                        AdminUser admin = (AdminUser) item;
                        adminMap.put(admin.getId(), admin);
                    }
                }
            }
        } catch (EOFException eof) {
            // safe fallback - file might be empty or corrupt
            System.err.println("Admin file is empty or corrupt. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load admin data", e);
        }
    }


    private void saveAdminsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ADMIN_FILE_PATH))) {
            oos.writeObject(new ArrayList<>(adminMap.values()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save admin data", e);
        }
    }
}