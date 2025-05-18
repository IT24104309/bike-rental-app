package lk.sliit.bike_rental_api.models;

public class subscription extends User{

    public subscription() {
        setUserType("subscription");
    }

    public subscription(String userID, String username, String password, String email, String phoneNumber, String userType) {
        super(userID, username, password, email, phoneNumber, userType);
    }
}
