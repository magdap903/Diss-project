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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterVolunteer extends AppCompatActivity {

//    FirebaseFirestore db = Firebase

    EditText rFirstName;
    EditText rLastName;
    EditText rEmail;
    EditText rPassword;
    Button register;
    Button signin;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_volunteer);

        rFirstName = findViewById(R.id.firstName);
        rLastName = findViewById(R.id.lastName);
        rEmail = findViewById(R.id.email);
        rPassword = findViewById(R.id.password);
        register = findViewById(R.id.regb);
        signin = findViewById(R.id.reg_sign);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        // Check if user is signed in
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), FillConstraints.class));
            finish();
        }


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            //public void onClick(View v) {openConstraints();}
            public void onClick(View v) {
                String firstName = rFirstName.getText().toString();
                String lastName = rLastName.getText().toString();
                String email = rEmail.getText().toString(); //.trim()?
                String password = rPassword.getText().toString();

                if(isEmpty(firstName)){
                    rFirstName.setError("First Name Required");
                    return;
                }

                if(isEmpty(lastName)){
                    rLastName.setError("Last Name Required");
                    return;
                }

                if(isEmpty(email)){
                    rEmail.setError("Email Required");
                    return;
                }

                if(isEmpty(password)){
                    rPassword.setError("Password Required");
                    return;
                }
                //password characters min ?


//                // Register User
//                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new onCompleteListener<AuthResult() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()) {
//                            // Sign in success, update UI with user info
//                            Log.d(TAG, "Success");
//                            FirebaseUser user = fAuth.getCurrentUser();
//                            updateUI(user);
//                        }
//                    }
//                        }



                // register user
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterVolunteer.this, "User Created", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Organiser", false);
                            user.put("firstName", firstName);
                            user.put("lastName", lastName);
                            user.put("email", email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "User Profile created for " + userID + firstName);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "Fail: " + e.toString());
                                }
                            });
                            openConstraints();
                            //startActivity(new Intent(getApplicationContext(), Constraints.class));
                        }
                        else {
                            Toast.makeText(RegisterVolunteer.this, "Error" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });


            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){openVolunteerLogin();}
        });
    }

//    private void Register(){
//        String email = rEmail.getText().toString();
//        String password = rPassword.getText().toString();
//    }


    public void openConstraints() {
        Intent intent = new Intent(this, FillConstraints.class);
        startActivity(intent);
    }

    public void openVolunteerLogin() {
        Intent intent = new Intent(this, LoginVolunteer.class);
        startActivity(intent);
    }
}