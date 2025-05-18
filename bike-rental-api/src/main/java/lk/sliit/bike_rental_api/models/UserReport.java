package lk.sliit.bike_rental_api.models;


import java.util.ArrayList;
import java.util.List;

public class UserReport {


    private final List<User> users;

    public UserReport() {
        this.users = new ArrayList<>();
    }


    public void addUser(User user) {
        if (user != null) {
            users.add(user);
        }
    }

    public void addUsers(List<? extends User> newUsers) {
        if (newUsers != null) {
            users.addAll(newUsers);
        }
    }

    public void clear() {
        users.clear();
    }


    public List<User> getUsers() {
        return new ArrayList<>(users);
    }


    public String generateReport() {
        if (users.isEmpty()) {
            return "User List Report\n\n(no users to display)";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("User List Report\n\n");

        // Header row
        sb.append(String.format("%-10s%-18s%-30s%-15s%-15s%n",
                "ID", "Username", "Email", "Phone", "Type"));
        sb.append("-".repeat(88)).append('\n');

        // User rows
        for (User u : users) {
            sb.append(String.format("%-10s%-18s%-30s%-15s%-15s%n",
                    u.getUserID(),
                    u.getUsername(),
                    u.getEmail(),
                    u.getPhoneNumber(),
                    u.getUserType()));
        }

        return sb.toString();
    }
}

