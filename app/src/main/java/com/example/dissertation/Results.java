package com.example.dissertation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Matches");
        setContentView(R.layout.activity_results);
    }
}