package com.example.dissertation;

import static android.text.TextUtils.isEmpty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterOrganiser extends AppCompatActivity {

    EditText organisationName;
    EditText emailOrg;
    EditText passwordOrg;
    Button register;
    Button signin;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String perm;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_organiser);

        organisationName = findViewById(R.id.organName);
        emailOrg = findViewById(R.id.emailO);
        passwordOrg = findViewById(R.id.passwordO);
        register = findViewById(R.id.registerO);
        signin = findViewById(R.id.signO);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //Check if user is signed in
        if(fAuth.getCurrentUser() != null) {
//            String userIDRegistered = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
//            DocumentReference drRegistered = fStore.collection("users").document(userIDRegistered);
//            drRegistered.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                    if(task.isSuccessful()){
//                        DocumentSnapshot doc = task.getResult();
//                        perm = Objects.requireNonNull(doc.get("Organiser")).toString();
//                    }
//                }
//            });
//
//            if(perm.equals("false")) {
//                startActivity(new Intent(getApplicationContext(), DisplayMatches.class));
//                Toast.makeText(RegisterOrganiser.this, "Access Denied - User is a Volunteer", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//            else {
                startActivity(new Intent(getApplicationContext(), DisplayEvents.class));
                finish();
//            }
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String organName = organisationName.getText().toString();
                String email = emailOrg.getText().toString();
                String password = passwordOrg.getText().toString();

                if(isEmpty(organName)){
                    organisationName.setError("Organisation Name Required");
                    return;
                }

                if(isEmpty(email)){
                    emailOrg.setError("Email Required");
                    return;
                }

                if(isEmpty(password)){
                    passwordOrg.setError("Password Required");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterOrganiser.this, "User Created", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("Organiser", "false");
                            user.put("organisationName", organName);
                            user.put("email", email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "User Profile created for " + userID + organName);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "Fail: " + e.toString());
                                }
                            });
                            openFirstEvent();
                        }
                        else {
                            Toast.makeText(RegisterOrganiser.this, "Error" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                


            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){openOrganiserLogin();}
        });

    }

    public void openFirstEvent() {
        Intent intent = new Intent(this, FirstEvent.class);
        startActivity(intent);
    }

    public void openOrganiserLogin() {
        Intent intent = new Intent(this, LoginOrganiser.class);
        startActivity(intent);
    }
}