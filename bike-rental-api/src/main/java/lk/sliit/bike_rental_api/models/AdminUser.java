package lk.sliit.bike_rental_api.models;


import lk.sliit.bike_rental_api.enums.AccountStatus;
import lk.sliit.bike_rental_api.enums.Role;

public class AdminUser extends User {
    // Add additional admin-only fields if needed
    public AdminUser(String id, String name, String email, Role role, AccountStatus status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public AdminUser() {
        // Required for JSON deserialization
    }



}