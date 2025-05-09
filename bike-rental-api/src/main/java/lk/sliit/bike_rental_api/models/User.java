package lk.sliit.bike_rental_api.models;

import lk.sliit.bike_rental_api.enums.AccountStatus;
import lk.sliit.bike_rental_api.enums.Role;


public class User {
    protected String id;
    protected String name;
    protected String email;
    protected Role role;
    protected AccountStatus status;

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
}
