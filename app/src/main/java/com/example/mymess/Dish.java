package com.example.mymess;
import com.google.firebase.firestore.Exclude;
import java.util.List;
public class Dish {
    private String cuisine;
    private String dish_name;
    private String mess;
    List<String> tags;
    List<String> day_meal;

    public Dish() {
        //public no-arg constructor needed
    }

    public Dish(String cuisine, String dish_name, String mess, List<String> tags, List<String> day_meal) {
        this.cuisine = cuisine;
        this.dish_name = dish_name;
        this.mess = mess;
        this.tags = tags;
        this.day_meal = day_meal;
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
}
