package com.example.mymess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MessSelected extends AppCompatActivity {
    TextView m_textViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_selected);

        Intent intent = getIntent();


        String mess_name = intent.getStringExtra("MESS");
        m_textViewTitle = findViewById(R.id.displaymessage);
        String display_message = "Congratulations!! You have successfully registered for " + mess_name + " mess.";
        m_textViewTitle.setText(mess_name);

    }
}
