package com.example.mymess;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.chip.Chip;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.validator.ChipifyingNachoValidator;


public class AdminDataEntry extends AppCompatActivity {
    private static final String TAG = "AdminDataEntry";

    private static final String KEY_DISH_NAME = "dish_name";
    private static final String KEY_MESS_NAME = "mess";
    private static final String KEY_TAGS = "tags";
    private static final String KEY_CUISINE_NAME = "cuisine";
    private static final String KEY_DAY_MEAL = "day_meal";

    private EditText editTextDishName;
    private EditText editTextMessName;
    private EditText editTextCuisineName;
    NachoTextView mNachoTextViewTags;
    NachoTextView mNachoTextViewDayMeal;
    Button mlogin_Button;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_data_entry);

        editTextDishName = findViewById(R.id.edit_text_dish_name);
        editTextMessName = findViewById(R.id.edit_text_mess_name);
        editTextCuisineName = findViewById(R.id.edit_text_cuisine_name);

        mNachoTextViewTags = findViewById(R.id.tag_text_input);
        mNachoTextViewTags.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);

        mNachoTextViewDayMeal = findViewById(R.id.tag_day_meal);
        mNachoTextViewDayMeal.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);

        mlogin_Button = findViewById(R.id.login_Button);

    }

    public void saveNote(View v) {
        String dish_name = editTextDishName.getText().toString();
        String mess_name = editTextMessName.getText().toString();
        String cuisine_name = editTextCuisineName.getText().toString();
        List<String> chiptags = mNachoTextViewTags.getChipAndTokenValues();
        List<String> chipdaymeal = mNachoTextViewDayMeal.getChipAndTokenValues();
//        String[] tags = {"John","Tim","Sam","Ben"};
        List tagsList = new ArrayList<String>(chiptags);
        List daymealsList = new ArrayList<String>(chipdaymeal);
        Map<String, Object> note = new HashMap<>();
        note.put(KEY_DISH_NAME, dish_name);
        note.put(KEY_MESS_NAME, mess_name);
        note.put(KEY_CUISINE_NAME, cuisine_name);
        note.put(KEY_TAGS, tagsList);
        note.put(KEY_DAY_MEAL, daymealsList);

        db.collection("dishes").add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AdminDataEntry.this, "Note saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminDataEntry.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });

        mlogin_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

}