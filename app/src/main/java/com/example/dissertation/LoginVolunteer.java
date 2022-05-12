package com.example.dissertation;

import static android.text.TextUtils.isEmpty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginVolunteer extends AppCompatActivity {

    EditText lEmail;
    EditText lPassword;
    Button login;
    Button register;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login_volunteer);

        lEmail = findViewById(R.id.email_l);
        lPassword = findViewById(R.id.password_l);
        login = findViewById(R.id.login_b);
        register = findViewById(R.id.log_reg);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //Check if user is signed in
        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), DisplayMatches.class));
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = lEmail.getText().toString();
                String password = lPassword.getText().toString();

                if(isEmpty(email)){
                    lEmail.setError("Email Required");
                    return;
                }

                if(isEmpty(password)){
                    lPassword.setError("Password Required");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginVolunteer.this, "User signed in", Toast.LENGTH_SHORT).show();
//                            userID = fAuth.getCurrentUser().getUid();
//                            DocumentReference documentReference = fStore.collection("users").document(userID);
//                            if ()
//
//                            String what = documentReference.getString("Organiser");



                            //startActivity(new Intent(getApplicationContext(), Constraints.class));
                            openResults();
//                            openConstraints();
                        }
                        else{
                            Toast.makeText(LoginVolunteer.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){openVolunteerRegister();}
        });
    }

    public void openResults() {
        Intent intent = new Intent(this, DisplayMatches.class);
        startActivity(intent);
    }

    public void openConstraints() {
        Intent intent = new Intent(this, FillConstraints.class);
        startActivity(intent);
    }

    public void openVolunteerRegister() {
        Intent intent = new Intent(this, RegisterVolunteer.class);
        startActivity(intent);
    }
}