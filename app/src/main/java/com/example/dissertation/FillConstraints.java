package com.example.dissertation;

import static android.text.TextUtils.isEmpty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v4.app.DialogFragment;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FillConstraints extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView loc;
//    LinearLayout layout;
    Fragment address;
    CheckBox checkBox;
    SeekBar seekBar;
    Slider slider;
    //TextView text;
    EditText age;
    Spinner forms;
    Spinner types;
//    Button dateFrom;
//    Button dateTo;
    LinearLayout dates;
    Button chooseDates;
    TextView dateFrom;
    TextView dateTo;
    TextView weekChoose;
    LinearLayout week;
    CheckBox monday;
    CheckBox tuesday;
    CheckBox wednesday;
    CheckBox thursday;
    CheckBox friday;
    CheckBox saturday;
    CheckBox sunday;
    FloatingActionButton save;
    Button signout;
    public static final String TAG = "TAG";


    String lat_lng;
    String placeName;
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
//    int AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Constraints");
        setContentView(R.layout.activity_constraints);

//        LinearLayout layout = findViewById(R.id.lay);
        loc = findViewById(R.id.textView9);
//        address = findViewById(R.id.autocomp);
        checkBox = findViewById(R.id.checkTravel);
        seekBar = findViewById(R.id.seekTravel);
        slider = findViewById(R.id.slider);
        //text = findViewById(R.id.textView5);
        age = findViewById(R.id.age);
        forms = findViewById(R.id.dropForms);
        types = findViewById(R.id.dropType);
//        dateFrom = findViewById(R.id.dateFrom);
//        dateTo = findViewById(R.id.dateTo1);
        dates = findViewById(R.id.dates);
        chooseDates = findViewById(R.id.chooseDates);
        dateFrom = findViewById(R.id.dateFrom1);
        dateTo = findViewById(R.id.dateTo1);
        weekChoose = findViewById(R.id.textView15);
        week = findViewById(R.id.week);
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);
        saturday = findViewById(R.id.saturday);
        sunday = findViewById(R.id.sunday);
        save = findViewById(R.id.save);
        signout = findViewById(R.id.signout);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        String[] formsOf = {"Animal Rescue", "Children and Youth", "Community Development", "Counselling",
                            "Education", "Environmental Work", "Fundraiser", "Healthcare Work",
                            "Sporting Events", "Wildlife Works", "Working with Elderly"};
        String[] typesOf = {"One-Time", "Regular"};
//        String[] daysOfWeek = new String[7];
        ArrayList<String> daysOfWeek = new ArrayList<>(6);

        // Set values for Form of Volunteering
        ArrayAdapter frm = new ArrayAdapter(this, android.R.layout.simple_spinner_item, formsOf);
        frm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        forms.setAdapter(frm);

        // Set values for Type of Event
        ArrayAdapter tp = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typesOf);
        tp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        types.setAdapter(tp);

        // Location
        Places.initialize(getApplicationContext(), api);
        PlacesClient placesClient = Places.createClient(this);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomp);

        assert autocompleteFragment != null;
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                placeid = place.getId();
                String lat_lng_CUT = String.valueOf(place.getLatLng());
                lat_lng = lat_lng_CUT.substring(10, lat_lng_CUT.length()-1);
                placeName = place.getName();
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

        {
            monday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        daysOfWeek.add("Monday");
                    }
                }
            });

            tuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        daysOfWeek.add("Tuesday");
                    }
                }
            });

            wednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        daysOfWeek.add("Wednesday");
                    }
                }
            });

            thursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        daysOfWeek.add("Thursday");
                    }
                }
            });

            friday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        daysOfWeek.add("Friday");
                    }
                }
            });

            saturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        daysOfWeek.add("Saturday");
                    }
                }
            });

            sunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        daysOfWeek.add("Sunday");
                    }
                }
            });
        }

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
                String ageStr = age.getText().toString();

                if(isEmpty(lat_lng)){
                    loc.setText("Please select location!");
                    return;
                }

                int ageInt = Integer.parseInt(ageStr);
                if(ageInt > 100 || ageInt <= 0) {
                    age.setError("Please provide a valid age");
                }

                if(isEmpty(ageStr)){
                    age.setError("Age Required");
                    return;
                }

                if(selectedType.equals("One-Time") && isEmpty(startDate)){
                    chooseDates.setError("Dates of Availability Required");
                    return;
                }

                boolean empt = daysOfWeek.isEmpty();
                if(selectedType.equals("Regular") && empt){
                    weekChoose.setText("Please select at least one option!");
                    return;
                }

                // save constraints
                userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

                Map<String,Object> user = new HashMap<>();
                user.put("locationID", placeid);
                user.put("locationName", placeName);
                user.put("lat_lng", lat_lng);
                user.put("distance", distance);
                user.put("formOfVolunteering", selectedForm);
                user.put("age", ageStr);
                user.put("typeOfVolunteering", selectedType);
                user.put("startDate", startDate);
                user.put("endDate", endDate);
                user.put("daysOfTheWeek", daysOfWeek);

                fStore.collection("volunteers").document(userID).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Constraints saved for " + userID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Fail: " + e.toString());
                    }
                });


                openResults();
            }
        });

        // slider.setLabelFormatter();
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

                    if(vlu==500){
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


    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);


        String dateFromChosen = getDateFormat(day, month + 1, year);
        dateFrom.setText(dateFromChosen);


//        String dateFromChosen = DateFormat.getDateInstance(DateFormat.FULL).format(cal.getTime());

//        dateFrom.setText(dateFromChosen);
    }

    private String getDateFormat(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }

    public void openResults() {
        Intent intent = new Intent(this, DisplayMatches.class);
        startActivity(intent);
    }

//    @Override
//    public void

}