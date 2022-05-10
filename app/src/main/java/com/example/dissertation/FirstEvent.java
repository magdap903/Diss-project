package com.example.dissertation;

import static android.text.TextUtils.isEmpty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FirstEvent extends AppCompatActivity {

    EditText nameEvent;
    TextView loc;
    LinearLayout layEvent;
    Spinner forms;
    LinearLayout layAge;
    EditText ageMin;
    EditText ageMax;
    EditText numberOfV;
    Spinner types;
    LinearLayout dates;
    Button dateEvent;
    LinearLayout weekEvent;
    TextView mess;
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
    String placeid;
    String placeName;
    String selectedForm;
    String selectedType;
    String chosenDate;

    String userID;
    String eventID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    private String api = "AIzaSyBz78w0LdSZV2nD_JO7PRzTiG-5u8yGPTk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_event);

        nameEvent = findViewById(R.id.nameEvent);
        loc = findViewById(R.id.textView17);
        layEvent = findViewById(R.id.layEvent);
        forms = findViewById(R.id.dropFormsEvent);
        layAge = findViewById(R.id.layAge);
        ageMin = findViewById(R.id.ageMin);
        ageMax = findViewById(R.id.ageMax);
        numberOfV = findViewById(R.id.numberOfV);
        types = findViewById(R.id.dropTypeEvent);
        dates = findViewById(R.id.dateOfEvent);
        dateEvent = findViewById(R.id.dateEvent);
        weekEvent = findViewById(R.id.weekEvent);
        mess = findViewById(R.id.textView25);
        monday = findViewById(R.id.mondayE);
        tuesday = findViewById(R.id.tuesdayE);
        wednesday = findViewById(R.id.wednesdayE);
        thursday = findViewById(R.id.thursdayE);
        friday = findViewById(R.id.fridayE);
        saturday = findViewById(R.id.saturdayE);
        sunday = findViewById(R.id.sundayE);
        save = findViewById(R.id.saveO);
        signout = findViewById(R.id.signoutO);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        String[] formsOf = {"Animal Rescue", "Children and Youth", "Community Development", "Counselling",
                            "Education", "Environmental Work", "Fundraiser", "Healthcare Work",
                            "Sporting Events", "Wildlife Works", "Working with Elderly"};
        String[] typesOf = {"One-Time", "Regular"};
        ArrayList<String> daysOfWeek = new ArrayList<>(6);


        // IF THERE ARE ANY EVENTS ALREADY MOVE TO EVENTSPAGE SO IT DOESN'T OVERWRITE



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

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocompEvent);

        assert autocompleteFragment != null;
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                placeid = place.getId();
                String lat_lng_CUT = String.valueOf(place.getLatLng());
                lat_lng = lat_lng_CUT.substring(9);


                placeName = place.getName();
                autocompleteFragment.setText(placeName);
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "Error with Autocomplete: " + status);
            }
        });


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
                    dates.setVisibility(View.VISIBLE);
                    weekEvent.setVisibility(View.GONE);
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
                    dates.setVisibility(View.GONE);
                    weekEvent.setVisibility(View.VISIBLE);
                    chosenDate = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds()).build();

        dateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show(getSupportFragmentManager(), "date_picker");

                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
//                        final String OLD_FORMAT =

                        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy");
                        chosenDate = simpleFormat.format(datePicker.getSelection());


//                        chosenDate = datePicker.getHeaderText();
//                        chosenDate = simpleFormat.format(dateP);

//                        dateEvent.setText(datePicker.getHeaderText());
                        dateEvent.setText(chosenDate);

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
                String nameOfEvent = nameEvent.getText().toString();
                String ageMinStr = ageMin.getText().toString();
                String ageMaxStr = ageMax.getText().toString();
//                int ageMinInt = Integer.parseInt(ageMinStr);
//                int ageMaxInt = Integer.parseInt(ageMaxStr);
                String numberVStr = numberOfV.getText().toString();
//                String

                if(isEmpty(nameOfEvent)){
                    nameEvent.setError("Organisation Name Required");
                    return;
                }

                if(isEmpty(lat_lng)){
                    loc.setText("Please select location!");
                    return;
                }

                if(isEmpty(ageMinStr)){
                    ageMin.setError("Minimum Age Required");
                    return;
                }

                if(isEmpty(ageMaxStr)){
                    ageMax.setError("Maximum Age Required");
                    return;
                }

//                if(ageMinInt > ageMaxInt){
//                    ageMax.setError("Minimum Age cannot be higher than Maximum Age");
//                    return;
//                }

                if(isEmpty(numberVStr)){
                    numberOfV.setError("Number Of Volunteers Required");
                    return;
                }

                if(selectedType.equals("One-Time") && isEmpty(chosenDate)){
                    dateEvent.setError("Date of the Event Required");
                    return;
                }

                boolean empt = daysOfWeek.isEmpty();
                if(selectedType.equals("Regular") && empt){
                    mess.setText("Please select at least one option!");
//                    mess.setTextColor(Integer.parseInt("red"));
                    return;
                }

                userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
//                String eventID = fStore.collection("users").document(userID).getId();
                Map<String,Object> event = new HashMap<>();
                event.put("locationID", placeid);
                event.put("locationName", placeName);
                event.put("lat_lng", lat_lng);
                event.put("nameOfEvent", nameOfEvent);
                event.put("formOfVolunteering", selectedForm);
                event.put("ageMin", ageMin.getText().toString());
                event.put("ageMax", ageMax.getText().toString());
                event.put("numberOfVolunteers", numberOfV.getText().toString());
                event.put("typeOfVolunteering", selectedType);
                event.put("dateOfEvent", chosenDate);
                event.put("daysOfTheWeek", daysOfWeek);
                event.put("organisationID", userID);

//                fStore.collection("users").document(userID).collection("events").document(nameOfEvent).set(event).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                fStore.collection("events").document(nameOfEvent).set(event).addOnSuccessListener(new OnSuccessListener<Void>() {
//                fStore.collection("organisers").document(nameOfEvent).set(event).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d(TAG, "Event saved for " + userID);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, "Fail: " + e.toString());
//                    }
//                });
                fStore.collection("events").add(event).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Event saved for " + userID);
                        eventID = documentReference.getId();

                        Map<String,Object> user = new HashMap<>();
                        user.put("nameOfEvent", nameOfEvent);
                        user.put("locationName", placeName);
                        user.put("dateOfEvent", chosenDate);
                        user.put("eventID", eventID);

                        fStore.collection("users").document(userID).collection("events").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "Event saved for " + userID);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "Fail: " + e.toString());
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Fail: " + e.toString());
                    }
                });


                openEvents();
            }
        });







    }

    public void openEvents() {
        Intent intent = new Intent(this, DisplayEvents.class);
        startActivity(intent);
    }
}