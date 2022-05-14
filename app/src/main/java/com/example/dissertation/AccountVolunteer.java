package com.example.dissertation;

import static android.text.TextUtils.isEmpty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class AccountVolunteer extends AppCompatActivity {

    TextView firstName, lastName, email;
    TextView dateFrom, dateTo, weekChoose;
    Slider slider;
    Spinner forms, types;
    EditText age;
    Button  chooseDates, signout;
    LinearLayout dates, week;
    CheckBox monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    FloatingActionButton save;
    public static final String TAG = "TAG";

    String lat_lng;
    String placeid;
    String distance;
    String selectedForm;
    String selectedType;
    String startDate;
    String endDate;

    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    private String api = "AIzaSyBz78w0LdSZV2nD_JO7PRzTiG-5u8yGPTk";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_volunteer);

        firstName = findViewById(R.id.textView26);
        lastName = findViewById(R.id.textView27);
        email = findViewById(R.id.textView28);
        slider = findViewById(R.id.sliderUP);
        forms = findViewById(R.id.dropFormsUP);
        age = findViewById(R.id.ageUP);
        types = findViewById(R.id.dropTypeUP);
        chooseDates = findViewById(R.id.chooseDatesUP);
        dates = findViewById(R.id.datesUP);
        dateFrom = findViewById(R.id.dateFrom1UP);
        dateTo = findViewById(R.id.dateTo1UP);
        week = findViewById(R.id.weekUP);
        weekChoose = findViewById(R.id.textView36);
        monday = findViewById(R.id.mondayUP);
        tuesday = findViewById(R.id.tuesdayUP);
        wednesday = findViewById(R.id.wednesdayUP);
        thursday = findViewById(R.id.thursdayUP);
        friday = findViewById(R.id.fridayUP);
        saturday = findViewById(R.id.saturdayUP);
        sunday = findViewById(R.id.sundayUP);
        save = findViewById(R.id.saveUP);
        signout = findViewById(R.id.signoutUP);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        String[] formsOf = {"Animal Rescue", "Children and Youth", "Community Development", "Counselling",
                "Education", "Environmental Work", "Fundraiser", "Healthcare Work",
                "Sporting Events", "Wildlife Works", "Working with Elderly"};
        String[] typesOf = {"One-Time", "Regular"};

        ArrayList<String> daysOfWeek = new ArrayList<>(6);

        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = fStore.collection("users").document(userID);
        DocumentReference documentReference1 = fStore.collection("volunteers").document(userID);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot ds = task.getResult();
                firstName.setText(ds.get("firstName").toString());
                lastName.setText(ds.get("lastName").toString());
                email.setText(ds.get("email").toString());
            }
        });


        documentReference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                 if(task.isSuccessful()){
                     DocumentSnapshot doc = task.getResult();
                     age.setText(doc.get("age").toString());
                 }
            }
        });


        //age.setText(doc.get(age).toString())




        // Set values for Form of Volunteering
        ArrayAdapter frm = new ArrayAdapter(this, android.R.layout.simple_spinner_item, formsOf);
        frm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        forms.setAdapter(frm);

        // Set values for Type of Event
        ArrayAdapter tp = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typesOf);
        tp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        types.setAdapter(tp);

        // GOOGLE MAPS
        Places.initialize(getApplicationContext(), api);
        PlacesClient placesClient = Places.createClient(this);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocompUP);

        assert autocompleteFragment != null;
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                placeid = place.getId();
                String lat_lng_CUT = String.valueOf(place.getLatLng());
                lat_lng = lat_lng_CUT.substring(9);


                String placeName = place.getName();
                autocompleteFragment.setText(placeName);
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "Error with Autocomplete: " + status);
            }
        });

        slider.addOnSliderTouchListener(touchListener);

        forms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedForm = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        types.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedType = adapterView.getItemAtPosition(i).toString();

                if(selectedType.equals("One-Time")) {
                    chooseDates.setVisibility(View.VISIBLE);
                    dates.setVisibility(View.VISIBLE);
                    weekChoose.setVisibility(View.GONE);
                    week.setVisibility(View.GONE);
                    monday.setChecked(false);
                    tuesday.setChecked(false);
                    wednesday.setChecked(false);
                    thursday.setChecked(false);
                    friday.setChecked(false);
                    saturday.setChecked(false);
                    sunday.setChecked(false);
                    daysOfWeek.clear();
                }
                else{
                    chooseDates.setVisibility(View.GONE);
                    dates.setVisibility(View.GONE);
                    weekChoose.setVisibility(View.VISIBLE);
                    week.setVisibility(View.VISIBLE);
                    startDate = null;
                    endDate = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        MaterialDatePicker datePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds())).build();

        chooseDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show(getSupportFragmentManager(), "date_picker");

                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        Pair selectedDates = (Pair) datePicker.getSelection();
                        final Pair<Date,Date> rangeDate = new Pair<>(new Date((Long) selectedDates.first), new Date((Long) selectedDates.second));
                        Date startD = rangeDate.first;
                        Date endD = rangeDate.second;
                        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy");

                        startDate = simpleFormat.format(startD);
                        endDate = simpleFormat.format(endD);

                        dateFrom.setText(startDate);
                        dateTo.setText(endDate);
                    }
                });
            }
        });

        monday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    daysOfWeek.add("Monday");
                }
            }
        });

        tuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    daysOfWeek.add("Tuesday");
                }
            }
        });

        wednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    daysOfWeek.add("Wednesday");
                }
            }
        });

        thursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    daysOfWeek.add("Thursday");
                }
            }
        });

        friday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    daysOfWeek.add("Friday");
                }
            }
        });

        saturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    daysOfWeek.add("Saturday");
                }
            }
        });

        sunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    daysOfWeek.add("Sunday");
                }
            }
        });

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
                if(selectedType.equals("One-Time") && isEmpty(startDate)){
                    chooseDates.setError("Dates of Availability Required");
                    return;
                }

                boolean empt = daysOfWeek.isEmpty();
                if(selectedType.equals("Regular") && empt){
                    weekChoose.setText("Please select at least one option!");
                    return;
                }

                userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();




            }
        });


    }

    private final Slider.OnSliderTouchListener touchListener = new Slider.OnSliderTouchListener() {

        @SuppressLint("RestrictedApi")
        @Override
        public void onStartTrackingTouch(@NonNull Slider slider) {
            slider.setLabelFormatter(new LabelFormatter() {
                @NonNull
                @Override
                public String getFormattedValue(float value) {
                    float vl = slider.getValue();
                    int vlu = (int) vl;

                    if(vlu==200){
                        return vlu + "+km";
                    }
                    else {
                        return vlu + "km";
                    }
                }
            });
        }

        @SuppressLint("RestrictedApi")
        @Override
        public void onStopTrackingTouch(@NonNull Slider slider) {
            float v = slider.getValue();
            int val = (int) v;
            distance = String.valueOf(val);
        }
    };

}