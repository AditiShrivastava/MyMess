package com.example.mymess;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class SelectPreferences extends AppCompatActivity {

    TextView heading, heading_label;
//    Button btn;
    Button mShowMessChoices;
    TextView mDisplaySelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_preferences);

        heading_label = (TextView) findViewById(R.id.headingLabel2);
        heading_label.setText(getIntent().getStringExtra("DAY") + " | " + getIntent().getStringExtra("MEAL"));

        mShowMessChoices = (Button) findViewById(R.id.showMessChoicesButton);
        mDisplaySelected = (TextView) findViewById(R.id.display_Selected);

        listItems = getResources().getStringArray(R.array.tags);
        checkedItems = new boolean[listItems.length];

        mShowMessChoices.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SelectPreferences.this);
                mBuilder.setTitle("Select the tags you wish for your meal");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {

                        if(isChecked){
                            mUserItems.add(position);
                        }else{
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for(int i = 0; i < mUserItems.size(); i++){
                            item = item + listItems[mUserItems.get(i)];
                            if(i != mUserItems.size() - 1){
                                item = item + ", ";
                            }
                        }
                        mDisplaySelected.setText(item);
                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for(int i = 0; i < checkedItems.length; i++){
                            checkedItems[i] = false;
                            mUserItems.clear();
                            mDisplaySelected.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

//        btn = (Button) findViewById(R.id.foodPreferencesButton);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int count = 0;
//                String categoryChoices = "";
//                boolean spicyChecked = ((CheckBox) findViewById(R.id.checkBoxSpicy)).isChecked();
//                boolean healthyChecked = ((CheckBox) findViewById(R.id.checkBoxHealthy)).isChecked();
//                boolean fibrousChecked = ((CheckBox) findViewById(R.id.checkBoxFibrous)).isChecked();
//                boolean friedChecked = ((CheckBox) findViewById(R.id.checkBoxFried)).isChecked();
//                if (spicyChecked) {
//                    categoryChoices += "Spicy, ";
//                    count += 1;
//                }
//                if (healthyChecked) {
//                    categoryChoices += "Healthy, ";
//                    count += 1;
//                }
//                if (fibrousChecked) {
//                    categoryChoices += "Fibrous, ";
//                    count += 1;
//                }
//                if (friedChecked) {
//                    categoryChoices += "Fried, ";
//                    count += 1;
//                }
//                if (count > 0) {
//                    categoryChoices = categoryChoices.substring(0, categoryChoices.length() - 2);
//                } else {
//                    categoryChoices = "None";
//                }
//                Intent intent = new Intent(SelectPreferences.this, ShowPreferences.class);
//                intent.putExtra("CATEGORIES", categoryChoices);
//                intent.putExtra("DESCRIPTION", description);
//                startActivity(intent);
//            }
//        });
    }
}
