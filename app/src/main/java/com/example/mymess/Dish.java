package com.example.mymess;
import com.google.firebase.firestore.Exclude;
import java.util.List;
public class Dish {
    private String documentId;
    private String cuisine;

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    private String dish_name;
    private String mess;
    private String image_path;
    List<String> tags;
    List<String> day_meal;

    public Dish() {
        //public no-arg constructor needed
    }

    public Dish(String cuisine, String dish_name, String mess, List<String> tags, List<String> day_meal, String image_path) {
        this.cuisine = cuisine;
        this.dish_name = dish_name;
        this.mess = mess;
        this.tags = tags;
        this.day_meal = day_meal;
        this.image_path = image_path;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getDish_name() {
        return dish_name;
    }

    public String getMess() {
        return mess;
    }

    public List<String> getDay_meal() {
        return day_meal;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getImage_path() {
        return image_path;
    }
}
