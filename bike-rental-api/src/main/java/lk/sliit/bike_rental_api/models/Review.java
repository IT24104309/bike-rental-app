package lk.sliit.bike_rental_api.models;

public class Review {
    private String id, name;
    private int rating;
    private String desc;

    public Review(String id, String name, int rating, String desc) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public String getDesc() {
        return desc;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}