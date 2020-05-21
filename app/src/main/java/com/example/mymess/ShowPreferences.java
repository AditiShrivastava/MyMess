package com.example.mymess;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ShowPreferences extends AppCompatActivity {

    //ForDisplay
    RecyclerView m_recyclerView;
    MyAdapter m_adapter;
    ArrayList<DishForDisplay> m_arrListDishesForDisplay;
    //ForDisplay End


    Button btn;
    TextView heading;
    TextView heading_label;
    HashSet<String> userSelectedTagsHashSet = new HashSet<String>();
    //ListMultimap<Integer, String> dishCountMatchesHashMap = MultimapBuilder.hashKeys().linkedListValues().build();
    Multimap<Integer, Dish> dishCountMatchesHashMap = MultimapBuilder.treeKeys().linkedListValues().build();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("dishes");
    private static final String TAG = "ShowPreferences";
    Integer kadamba_mess_count, south_mess_count, north_mess_count, yuktahaar_mess_count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_preferences);

        m_arrListDishesForDisplay = new ArrayList<DishForDisplay>();



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
        heading_label.setText(getIntent().getStringExtra("DAY") + " | " + getIntent().getStringExtra("MEAL"));

        //heading = findViewById(R.id.headingLabel);
        //heading.setText(s);
        kadamba_mess_count = 0;
        south_mess_count = 0;
        north_mess_count = 0;
        yuktahaar_mess_count = 0;

        Context context = this;
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
                        if (userSelectedTagsHashSet.contains(tag)){
                            dish_tag_match_count++;
                            if(dish.getMess().toLowerCase().equals("south"))
                                south_mess_count++;
                            else if(dish.getMess().toLowerCase().equals("north"))
                                north_mess_count++;
                            else if(dish.getMess().toLowerCase().equals("kadamba"))
                                kadamba_mess_count++;
                            else if(dish.getMess().toLowerCase().equals("yuktahaar"))
                                yuktahaar_mess_count++;
                        }


                    }
                    dishCountMatchesHashMap.put(dish_tag_match_count, dish);


                }


                Set<Integer> keys = dishCountMatchesHashMap.keySet();
                List<Integer> list = new ArrayList(keys);
                Collections.sort(list, Collections.reverseOrder());
                Set<Integer> resultSet = new LinkedHashSet(list);
                Integer mess_count = 0;

                for (Integer keyprint : resultSet) {
                    System.out.println("Key = " + keyprint);
                    Collection<Dish> values = dishCountMatchesHashMap.get(keyprint);
                    for(Dish dishentry : values){
                        System.out.println("Value= "+ dishentry.getDish_name());//here add entry into list !@#$
                        if(dishentry.getMess().equals("south"))
                            mess_count = south_mess_count;
                        if(dishentry.getMess().equals("north"))
                            mess_count = north_mess_count;
                        if(dishentry.getMess().equals("kadamba"))
                            mess_count = kadamba_mess_count;
                        if(dishentry.getMess().equals("yuktahaar"))
                            mess_count = yuktahaar_mess_count;
                        DishForDisplay dish_with_count = new DishForDisplay(dishentry.getCuisine(),
                                dishentry.getDish_name(), dishentry.getMess(), dishentry.getTags(), dishentry.getDay_meal(),
                                dishentry.getImage_path(), keyprint, mess_count);
                        m_arrListDishesForDisplay.add(dish_with_count);//Final Display Entry
                    }
                }
                //forDisplay
                m_recyclerView = findViewById(R.id.recyclerView);
                m_recyclerView.setLayoutManager(new LinearLayoutManager(context));
                m_adapter = new MyAdapter(context,m_arrListDishesForDisplay);
                m_recyclerView.setAdapter(m_adapter);

                //System.out.println("Hash_MultiMap: " + Arrays.asList(dishCountMatchesHashMap));
                //System.out.println("User Selected Tags: " + Arrays.asList(userSelectedTagsHashSet));
            }
        });

//Here
    }


}
