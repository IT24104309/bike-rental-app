package lk.sliit.bike_rental_api.models;

public class Review {
    private long id;
    private String userName;
    private int rating;
    private String desc;

    public Review(){

    }

    public Review(long id, String userName, int rating, String desc) {
        this.id = id;
        this.userName = userName;
        this.rating = rating;
        this.desc = desc;
    }

    public long getId() { return id; }

    public String getUserName() {
        return userName;
    }

    public int getRating() {
        return rating;
    }

    public String getDesc() {
        return desc;
    }

    public void setId(long id) { this.id = id; }

    public void setUserName(String name) {
        this.userName = userName;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}