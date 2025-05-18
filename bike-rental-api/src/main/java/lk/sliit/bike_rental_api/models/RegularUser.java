package lk.sliit.bike_rental_api.models;

public class RegularUser extends User{

    public RegularUser() {
        setUserType("Regular");
    }

    public RegularUser(String userID, String username, String password, String email, String phoneNumber, String userType) {
        super(userID, username, password, email, phoneNumber, userType);
    }
}
