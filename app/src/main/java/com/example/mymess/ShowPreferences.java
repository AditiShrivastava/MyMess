package com.example.mymess;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowPreferences extends AppCompatActivity {

    TextView heading;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_preferences);

        ArrayList<String> userSelectedTags = (ArrayList<String>) getIntent().getSerializableExtra("USER_SELECTED_TAGS");
        String s = "";
        for (String i : userSelectedTags){
            System.out.println(i);
            s += i;
            s+= ", ";
        }
        heading = findViewById(R.id.headingLabel);
        heading.setText(s);
    }
}
