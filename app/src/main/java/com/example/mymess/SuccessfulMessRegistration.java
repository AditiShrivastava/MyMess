package com.example.mymess;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class SuccessfulMessRegistration extends AppCompatActivity {

    TextView m_textViewTitle,m_textViewCuisine, m_textViewMess, m_textViewTags,
            m_textViewNumberOfTagsMatching, m_textViewNumberOfTagsMatchingwithMess,
            m_textViewDisplay;
    ImageView m_imageView;
    String mess_name;
    String day_meal;
    Button m_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_mess_registration);

        ActionBar actionBar = getSupportActionBar();

        m_imageView = findViewById(R.id.imageIv);
        m_textViewTitle = findViewById(R.id.titleTv);
        m_textViewCuisine = findViewById(R.id.cuisineTv);
        m_textViewMess = findViewById(R.id.messTv);
        m_textViewTags = findViewById(R.id.tagsTv);
//        m_textViewNumberOfTagsMatching = findViewById(R.id.numtagsTv);
//        m_textViewNumberOfTagsMatchingwithMess = findViewById(R.id.nummessTagsTv);
        m_textViewDisplay = findViewById(R.id.displaymessage);
        m_logout = findViewById(R.id.logout_Button);


        Intent intent = getIntent();


        String gDishName = intent.getStringExtra("DISH_NAME");
        String gCuisine = intent.getStringExtra("CUISINE");
        String gMessName = intent.getStringExtra("MESS");
        mess_name = gMessName;
        String tags = intent.getStringExtra("TAGS");
        String gImagePath = intent.getStringExtra("IMAGE_PATH");
//        int gNumMatchingTags = intent.getIntExtra("NumMatchingTags");


    actionBar.setTitle(gDishName);

    m_textViewTitle.setText(gDishName);
    m_textViewCuisine.setText(gCuisine);
    m_textViewMess.setText(gMessName);
    m_textViewTags.setText(tags);
    Picasso.get().load(gImagePath).into(m_imageView);
    String display_message = "Congratulations!! You have successfully selected for " + mess_name + " mess.";
    m_textViewDisplay.setText(display_message);



    }

//    public void setOnClickListener (View.OnClickListener l){
//
//        Intent intent = new Intent(SuccessfulMessRegistration.this, MessSelected.class);
//        intent.putExtra("MESS", mess_name);
//        startActivity(intent);
//
//    }

//    public void logout(View view){
//        FirebaseAuth.getInstance().signOut();
//        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        finish();
//    }


//
//        m_logout.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            startActivity(new Intent(getApplicationContext(), Register.class));
//        }
//    });

}
