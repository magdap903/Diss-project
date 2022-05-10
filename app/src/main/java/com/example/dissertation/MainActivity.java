package com.example.dissertation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;
    Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVolunteerRegister();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrganiserRegister();
            }
        });
    }
    public void openVolunteerRegister() {
        Intent intent = new Intent(this, RegisterVolunteer.class);
        startActivity(intent);
    }

    public void openOrganiserRegister() {
        Intent intent = new Intent(this, RegisterOrganiser.class);
        startActivity(intent);
    }
}
