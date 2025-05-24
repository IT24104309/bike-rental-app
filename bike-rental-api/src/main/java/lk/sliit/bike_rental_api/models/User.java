package lk.sliit.bike_rental_api.models;




public class User {

    private String userID;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String userType;

    public User() {
    }

    public User(String userID, String username, String password, String email, String phoneNumber, String userType) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {

        this.userID = userID;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPhoneNumber() {

        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }

    public String getUserType() {

        return userType;
    }

    public void setUserType(String userType) {

        this.userType = userType;
    }
}
