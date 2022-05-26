package com.example.dissertation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DisplayMatches extends AppCompatActivity {

    private ArrayList<Matches> matchesArrayList, matchesArrayListSorted;
    private RecyclerView matchesRV;
    private MatchesRVAdapter matchesRVAdapter;

    String lat_lngV;
//    LocalDate startDateV;
//    LocalDate endDateV;
    LocalDate startDateV, endDateV;
    String startDateVStr, endDateVStr;
    String formV;
    String typeV;
    int distanceV, ageV;
    Location locationV, locationE;
    ArrayList<String> daysV = new ArrayList<>();
    ArrayList<String> applied = new ArrayList<>();
    Button account, signout;
    List<LocalDate> dates = new ArrayList<>();

    public static String TAG = "TAG";

    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Matched Results");
        setContentView(R.layout.activity_display_matches);

        matchesRV = findViewById(R.id.matchesRV1);
        account = findViewById(R.id.account);
        signout = findViewById(R.id.signoutAcc);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AccountVolunteer.class));
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        // Get Volunteers data
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        fStore.collection("volunteers").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot ds = task.getResult();

//                Matches mat = ds.toObject(Matches.class);

                lat_lngV = ds.get("lat_lng").toString();

//                lat_lngV = mat.getLat_lng();

                // String to LatLng
                String [] latLngV = lat_lngV.split(",");
                double latitudeV = Double.parseDouble(latLngV[0]);
                double longitudeV = Double.parseDouble(latLngV[1]);

                // Set Lat and Lng
                locationV = new Location("locationA");
                locationV.setLatitude(latitudeV);
                locationV.setLongitude(longitudeV);

                distanceV = Integer.parseInt(ds.get("distance").toString());
                ageV = Integer.parseInt(ds.get("age").toString());
                formV = ds.get("formOfVolunteering").toString();
                typeV = ds.get("typeOfVolunteering").toString();
                if(typeV.equals("One-Time")) {

                    startDateVStr = ds.get("startDate").toString();
                    endDateVStr = ds.get("endDate").toString();

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    startDateV = LocalDate.parse(startDateVStr, formatter);
                    endDateV = LocalDate.parse(endDateVStr, formatter);

                    long numOfDays = ChronoUnit.DAYS.between(startDateV, endDateV);

                    dates = Stream.iterate(startDateV, date -> date.plusDays(1)).limit(numOfDays).collect(Collectors.toList());

                }
                else if(typeV.equals("Regular")){
                    daysV = (ArrayList<String>) ds.get("daysOfTheWeek");

//                    Matches mat = ds.toObject(Matches.class);
//                    daysV = mat.getDays();
                }
                
                // Get IDs of already applied events
                
                fStore.collection("volunteers").document(userID).collection("applied")
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot document : list) {
//                                String evID = (String) document.get("eventID");
                                Matches n = document.toObject(Matches.class);
                                String evID = (String) n.getEventID();
                                applied.add(evID);
                            }
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Fail: " + e.toString());
            }
        });

        matchesArrayList = new ArrayList<>();

        // Get all Events from Firestore
        fStore.collection("events").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot doc : list) {

                        Matches m;

                        if(!applied.contains(doc.getId())) {
                            m = doc.toObject(Matches.class);


//                        Matches m = doc.toObject(Matches.class);

                            if (m == null) {
                                Toast.makeText(DisplayMatches.this, "m is Null", Toast.LENGTH_SHORT).show();
                            }

                            matchesArrayList.add(m);

//                            if (!(m.getTypeOfVolunteering().equals(typeV))) {
//                                matchesArrayList.remove(m);
//                            }
                        }
                        matchesArrayList.forEach(event -> {
                            String lat_lngE, formE, typeE, dateEStr;
                            LocalDate dateE = null;
//                            ArrayList daysE = new ArrayList<>();
                            String[] latLng;
                            int ageMinE, ageMaxE;
                            int score = 0;

//                            event.setEventID(doc.getId());
//                            String locE = event.getLocationName();

                            lat_lngE = event.getLat_lng();
                            dateEStr = event.getDateOfEvent();
                            ageMinE = Integer.parseInt(event.getAgeMin());
                            ageMaxE = Integer.parseInt(event.getAgeMax());
                            formE = event.getFormOfVolunteering();
                            typeE = event.getTypeOfVolunteering();
                            ArrayList<String> daysE = event.getDays();
                            
                            // String to LatLng
                            latLng = lat_lngE.split(",");
                            double latitudeE = Double.parseDouble(latLng[0]);
                            double longitudeE = Double.parseDouble(latLng[1]);
//                          LatLng locationE = new LatLng(latitudeE, longitudeE);

                            locationE = new Location("locationB");
                            locationE.setLatitude(latitudeE);
                            locationE.setLongitude(longitudeE);

                            double dis;

                            if(!(locationE == null) && !(locationV == null)) {
                                dis = (locationV.distanceTo(locationE)) / 1000;
                            }
                            else {
                                dis = 69;
                            }


                            // Calculate distance between locations
//                            double dis = (locationV.distanceTo(locationE)) / 1000;
                            int distInt = (int)Math.round(dis);
                            String dist = String.valueOf(distInt);

                            if(distanceV == 500){
                                score++;
                            }else if(distanceV >= distInt) {
                                score++;
                            }

//                                event.setLocationName(locE + " - " + distInt + "km");
                            else {
//                                event.setDistance(locE + " - " + distInt + " - needs adjustment");
                                event.setDistance(dist);
//                                score1 = score;
                            }
                            event.setDistance(dist);
//                            else {score1 = score;}

                            // Check if form is the same
                            if(formV.equals(formE)) {
//                                score2 = score1 + 1;
                                score++;
                            }
                            event.setFormOfVolunteering(formE);


                            // Check if age is within the range
                            if(ageV>ageMinE && ageV<ageMaxE) {
                                score++;
                                event.setAge("Ages: " + ageMinE + " - " + ageMaxE);
                            }

                            // Check if type is the same
                            if(typeV.equals(typeE)) {
                                score++;
//                                score4 = score3 + 1;
                            } else{
//                                score4 = score3;
//                                event.setTypeOfVolunteering(typeE + " - needs adjustment");
                            }

                            // Check if the availability is the same
                            if(typeV.equals("One-Time")) {
                                
                                DateTimeFormatter formatter = null;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                }
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    dateE = LocalDate.parse(dateEStr, formatter);
                                }

                                if(dates.contains(dateE)){
                                    score++;
                                }

                            } else if (typeV.equals("Regular")){
//                                if(daysE.contains("Tuesday")) {
//                                    score++;
//                                }

                                event.setDateOfEvent(daysE.toString());

//
//                                for(String day : daysE){
//                                    if(!daysV.contains(day)) {
//
//                                    }
//                                }

                                event.setDateOfEvent(String.valueOf(daysE));
                            }
                            
                            String scoreFinal = String.valueOf(score);
                            event.setScore(scoreFinal);


                        });
                        if(matchesArrayList.isEmpty()){
                            Toast.makeText(DisplayMatches.this, "ArrayList is Empty", Toast.LENGTH_SHORT).show();

                        }

                        Collections.sort(matchesArrayList, new Comparator<Matches>() {
                            @Override
                            public int compare(Matches matches, Matches t1) {
                                return matches.getScore().compareTo(t1.getScore());
                            }
                        });

                        Collections.reverse(matchesArrayList);

                    }
                    matchesRVAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(DisplayMatches.this, "No events found in Database", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DisplayMatches.this, "Fail to get data", Toast.LENGTH_SHORT).show();

            }
        });

        matchesRV.setLayoutManager(new LinearLayoutManager(this));
        matchesRVAdapter = new MatchesRVAdapter(matchesArrayList, this);
        matchesRV.setAdapter(matchesRVAdapter);
    }
}