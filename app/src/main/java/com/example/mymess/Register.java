package com.example.mymess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    // Create some variables
    EditText mFullName, mEmail, mPassword;
    Button mRegisterBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);
        mFullName = findViewById(R.id.full_Name_Field);
        mEmail = findViewById(R.id.email_Field);
        mPassword = findViewById(R.id.password_Field);
        mRegisterBtn = findViewById(R.id.register_Button);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        fStore = FirebaseFirestore.getInstance();

//        checking whether the user is already logged in or not!
//        if(fAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            finish();
//        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
//                String fullname = mFullName.getText().toString();
//final issue was there if I declared fullname here and access it from inner class --> check [16:19] video
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required. ");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required. ");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Register the user in firebase

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String fullname = mFullName.getText().toString();
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User Registered Successfully!", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fullname);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: User profile is created for " + userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else{
                            Toast.makeText(Register.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


            }
        });
    }
}
