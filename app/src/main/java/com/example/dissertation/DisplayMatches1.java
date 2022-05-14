package com.example.dissertation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DisplayMatches1 extends AppCompatActivity {

    private ArrayList<Matches> matchesArrayList;
    private RecyclerView matchesRV;
    private MatchesRVAdapter matchesRVAdapter;

    String lat_lngV, startDateV, endDateV, formV, typeV;
    int distanceV, ageV;
    Location locationV, locationE;

    public static String TAG = "TAG";

    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Matches 2");
        setContentView(R.layout.activity_display_matches1);

        matchesRV = findViewById(R.id.matchesRV1);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        // Get Volunteers data
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        fStore.collection("volunteers").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot ds = task.getResult();
                lat_lngV = ds.get("lat_lng").toString();

                // String to LatLng
                String [] latLngV = lat_lngV.split(",");
                double latitudeV = Double.parseDouble(latLngV[0]);
                double longitudeV = Double.parseDouble(latLngV[1]);

//                locationV = new LatLng(latitudeV, longitudeV);
                locationV = new Location("locationA");
                locationV.setLatitude(latitudeV);
                locationV.setLongitude(longitudeV);

                distanceV = Integer.parseInt(ds.get("distance").toString());
                ageV = Integer.parseInt(ds.get("age").toString());
                startDateV = ds.get("startDate").toString();
                endDateV = ds.get("endDate").toString();
                formV = ds.get("formOfVolunteering").toString();
                typeV = ds.get("typeOfVolunteering").toString();
//                daysV = (ArrayList) ds.get("daysOfTheWeek");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Fail: " + e.toString());
            }
        });

        matchesArrayList = new ArrayList<>();

        // Get all Events from FireStore
        fStore.collection("events").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot doc : list) {
//                        String ageMin = doc.get("ageMin").toString();

                        Matches m = doc.toObject(Matches.class);
//                        m.ge

                        if(m == null){
                            Toast.makeText(DisplayMatches1.this, "m is Null", Toast.LENGTH_SHORT).show();

                        }
//                        assert m != null;
//                        matchesArrayList.add(updateData(m));
                        matchesArrayList.add(m);
                        matchesArrayList.forEach(event -> {
//                            event.setScore("25");

                            String lat_lngE, dateE, formE, typeE;
                            String[] latLng;
                            int ageMinE, ageMaxE;

                            lat_lngE = event.getLat_lng();
//                          dateE = m.getDateOfEvent();
                            ageMinE = Integer.parseInt(event.getAgeMin());
                            ageMaxE = Integer.parseInt(event.getAgeMax());
                            formE = event.getFormOfVolunteering();
//                          typeE = event.ge
                            int score = 0;
                            int score1;
                            int score2;
                            int score3;

//;
                            // String to LatLng
                            latLng = lat_lngE.split(",");
                            double latitudeE = Double.parseDouble(latLng[0]);
                            double longitudeE = Double.parseDouble(latLng[1]);
//                          LatLng locationE = new LatLng(latitudeE, longitudeE);

                            locationE = new Location("locationB");
                            locationE.setLatitude(latitudeE);
                            locationE.setLongitude(longitudeE);

                            // Calculate distance between locations
                            double dis = locationV.distanceTo(locationE);
                            String dist = String.valueOf(dis);

                            if(distanceV <= dis) {
                                score1 = score + 1;
                            }else {score1 = score;}

                            // Check if the form of volunteering is the same
                            if(formV.equals(formE)) {
                                score2 = score1 + 1;
                            }else {score2 = score1;}


                            // Check if age is within the range
                            if(ageV>ageMinE && ageV<ageMaxE) {
//                              score++;
                                score3 = score2 + 1;
                            }else {score3 = score2;}

//                          scoreTV.setText(String.valueOf(score2));
//                          scoreTV.setText(String.valueOf(ageMinE));

                            String scoreFinal = String.valueOf(score3);

                            event.setScore(scoreFinal);
                            event.setDistance(dist);




                        });
                        if(matchesArrayList.isEmpty()){
                            Toast.makeText(DisplayMatches1.this, "ArrayList is Empty", Toast.LENGTH_SHORT).show();

                        }
                    }
                    matchesRVAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(DisplayMatches1.this, "No events found in Database", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DisplayMatches1.this, "Fail to get data", Toast.LENGTH_SHORT).show();

            }
        });


        matchesRV.setLayoutManager(new LinearLayoutManager(this));
        matchesRVAdapter = new MatchesRVAdapter(matchesArrayList, this);
        matchesRV.setAdapter(matchesRVAdapter);
//        matchesRVAdapter.notifyDataSetChanged();



//        fStore.collection("events").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()) {
//                    List<String> list = new ArrayList<>();
//
//                    for(DocumentSnapshot doc : task.getResult()) {
//                        list.add(doc);
//                    }
//                    Log.d(TAG, list.toString());
//                    updateData(list);
//                }
//                else {
//                    Log.d(TAG, "Error getting documents: " + task.getException());
//                }
//            }
//        });


    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public Matches updateData(Matches m) {
//
//        String lat_lngE, dateE, formE, typeE;
//        String[] latLng;
//        int ageMinE, ageMaxE;
//
//        lat_lngE = m.getLat_lng();
////        dateE = m.getDateOfEvent();
//        ageMinE = Integer.parseInt(m.getAgeMin());
//        ageMaxE = Integer.parseInt(m.getAgeMax());
//        formE = m.getFormOfVolunteering();
////            typeE = event.ge
//        int score = 0;
//        int score1;
//        int score2;
//        int score3;
//
//
//        // String to LatLng
//        latLng = lat_lngE.split(",");
//        double latitudeE = Double.parseDouble(latLng[0]);
//        double longitudeE = Double.parseDouble(latLng[1]);
////            LatLng locationE = new LatLng(latitudeE, longitudeE);
//
//        Location locationE = new Location("locationB");
//        locationE.setLatitude(latitudeE);
//        locationE.setLongitude(longitudeE);
//
//        // Calculate distance between locations
//        double dis = locationV.distanceTo(locationE);
//        String dist = String.valueOf(dis);
//
//        if(distanceV <= dis) {
//            score1 = score + 1;
//        }else {score1 = score;}
//
//        // Check if the form of volunteering is the same
//        if(formV.equals(formE)) {
//            score2 = score1 + 1;
//        }else {score2 = score1;}
//
//
//        // Check if age is within the range
//        if(ageV>ageMinE && ageV<ageMaxE) {
////                score++;
//            score3 = score2 + 1;
//        }else {score3 = score2;}
//
////            scoreTV.setText(String.valueOf(score2));
////            scoreTV.setText(String.valueOf(ageMinE));
//
//        String scoreFinal = String.valueOf(score3);
//
//        m.setScore(scoreFinal);
//        m.setDistance(dist);
//
//
//
//
////        WriteBatch batch = fStore.batch();
////
////        for(int k=0; k<list.size(); k++) {
//////            String n = k.get
////            DocumentReference docr = fStore.collection("events").document(list.get(k));
//////            String n = docr.get("ageMin").toString();
////            batch.update(docr, "ageMin", "502");
////
////        }
////
//////        list.forEach(event -> {
//////            String n = event.ge
//////
//////
//////
////
////        });
//
//
//        return m;
//    }

}