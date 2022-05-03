package com.example.dissertation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.libraries.places.api.Places;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Constraints extends AppCompatActivity {

    CheckBox checkBox;
    SeekBar seekBar;
    Slider slider;
    //TextView text;
    EditText age;
    Spinner forms;
    Spinner type;
    FloatingActionButton save;
    Button signout;

    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraints);

        checkBox = findViewById(R.id.checkTravel);
        seekBar = findViewById(R.id.seekTravel);
        slider = findViewById(R.id.sliderKM);
        //text = findViewById(R.id.textView5);
        age = findViewById(R.id.age);
        forms = findViewById(R.id.dropForms);
        type = findViewById(R.id.dropType);
        save = findViewById(R.id.save);
        signout = findViewById(R.id.signout);

        fStore = FirebaseFirestore.getInstance();

        String[] formsOf = {"kids", "elderly", "charity event", "cooking"};
        String[] types = {"One-Time", "Regular"};

        // Set values for Form of Volunteering
        ArrayAdapter frm = new ArrayAdapter(this, android.R.layout.simple_spinner_item, formsOf);
        frm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        forms.setAdapter(frm);
        // Set values for Type of Event
        ArrayAdapter tp = new ArrayAdapter(this, android.R.layout.simple_spinner_item, types);
        tp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(tp);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //slider.setVisibility(View.VISIBLE);
                    slider.setEnabled(true);
                }
                else{
                    //slider.setVisibility(View.GONE);
                    slider.setEnabled(false);
                }

            }
        });

//        type.setOnItemSelectedListener(this);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String
            }
        });

        // slider.setLabelFormatter();
    }
//    @Override
//    public void

}