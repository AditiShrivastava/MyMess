package com.example.mymess;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShowPreferences extends AppCompatActivity {
    Button btn;
    TextView heading;
    TextView heading_label;
    HashSet<String> userSelectedTagsHashSet = new HashSet<String>();
    ListMultimap<Integer, Dish> dishCountMatchesHashMap = MultimapBuilder.hashKeys().linkedListValues().build();
//    HashMap<Integer, String> dishCountMatchesHashMap = new HashMap<Integer, String>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("dishes");
    private static final String TAG = "ShowPreferences";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_preferences);

        ArrayList<String> userSelectedTags = (ArrayList<String>) getIntent().getSerializableExtra("USER_SELECTED_TAGS");
        for (String tag : userSelectedTags){
            userSelectedTagsHashSet.add(tag);
        }

        String day = getIntent().getStringExtra("DAY");
        String meal = getIntent().getStringExtra("MEAL");
        String user_day_meal = day.toLowerCase() + "_" + meal.toLowerCase();

        String s = "";
        for (String i : userSelectedTags){
            s += i;
            s+= ", ";
        }

        heading_label = (TextView) findViewById(R.id.headingLabel2);
//        heading_label.setText(getIntent().getStringExtra("DAY") + " | " + getIntent().getStringExtra("MEAL"));

        heading = findViewById(R.id.headingLabel);
//        heading.setText(s);



        notebookRef.whereArrayContains("day_meal", user_day_meal).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Dish dish = documentSnapshot.toObject(Dish.class);
                    List<String> dish_tags = dish.getTags();
                    ArrayList<String> dish_tags_array = new ArrayList<String>(dish_tags);

                    String s = "";
                    for (String i : dish_tags_array){
                        s += i;
                        s+= ", ";
                    }
                    dish.setDocumentId(documentSnapshot.getId());
                    String dish_id = dish.getDocumentId();
                    System.out.println("Dish: " + dish_id + ", "+ s);
                    Integer dish_tag_match_count = 0;
                    for (String tag : dish_tags_array) {
                        if (userSelectedTagsHashSet.contains(tag))
                            dish_tag_match_count++;
                    }
                    dishCountMatchesHashMap.put(dish_tag_match_count, dish);
                }
                // Iterate over Guava's Multimap using entries() method

//                final List<Integer, Dish> reversedishCountMatchesHashMap
//                        = Lists.newArrayList(dishCountMatchesHashMap.values());
                Set keySet = dishCountMatchesHashMap.keySet();
                Iterator keyIterator = keySet.iterator();

                while (keyIterator.hasNext() ) {
                    int key = (int)keyIterator.next();
                    List values = dishCountMatchesHashMap.get(key);
                    System.out.println(key + ": " + values);
                }

                for (Map.Entry<Integer,Dish> entry: dishCountMatchesHashMap.entries()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }

                System.out.println("Hash_MultiMap: " + Arrays.asList(dishCountMatchesHashMap));
//                System.out.println("User Selected Tags: " + Arrays.asList(userSelectedTagsHashSet));
            }
        });

    }


}
