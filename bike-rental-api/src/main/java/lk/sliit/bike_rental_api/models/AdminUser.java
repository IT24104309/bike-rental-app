package lk.sliit.bike_rental_api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lk.sliit.bike_rental_api.enums.AccountStatus;
import lk.sliit.bike_rental_api.enums.Role;

public class AdminUser {
    private String id;
    private String name;
    private String email;
    private Role role;
    private AccountStatus status;

    @JsonIgnore // Prevent password from being serialized in responses
    private String password;

    public AdminUser() {
        // Required for JSON deserialization
    }

    public AdminUser(String id, String name, String email, Role role, AccountStatus status, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.status = status;
        this.password = password;
    }

    // 🔽 Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public String getPassword() {
        return password;
    }

    // 🔼 Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
