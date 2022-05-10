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

public class LoginOrganiser extends AppCompatActivity {

    EditText lEmail;
    EditText lPassword;
    Button login;
    Button register;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login_organiser);

        lEmail = findViewById(R.id.email_lO);
        lPassword = findViewById(R.id.password_lO);
        login = findViewById(R.id.login_bO);
        register = findViewById(R.id.log_regO);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //Check if user is signed in
        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), FirstEvent.class));
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = lEmail.getText().toString();
                String password = lPassword.getText().toString();

                if (isEmpty(email)) {
                    lEmail.setError("Email Required");
                    return;
                }

                if (isEmpty(password)) {
                    lPassword.setError("Password Required");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginOrganiser.this, "User signed in", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(getApplicationContext(), Constraints.class));
                            openResults();
//                            openConstraints();
                        } else {
                            Toast.makeText(LoginOrganiser.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){openOrganiserRegister();}
        });

    }

    public void openResults() {
        Intent intent = new Intent(this, FirstEvent.class);
        startActivity(intent);
    }

    public void openOrganiserRegister() {
        Intent intent = new Intent(this, RegisterOrganiser.class);
        startActivity(intent);
    }
}