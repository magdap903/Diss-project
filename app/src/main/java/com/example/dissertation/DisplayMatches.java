package com.example.dissertation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
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

public class DisplayMatches extends AppCompatActivity {


    private RecyclerView matchesRV;
    private MatchesRVAdapter matchesRVAdapter;
    private ArrayList<Matches> matchesArrayList;


    String lat_lngV;
    int distanceV;
    String startDateV;
    String endDateV;
    String formV;
    String typeV;
    int ageV;
    ArrayList daysV;
//    LatLng locationV;
    Location locationV;

    TextView scoreTV;

//    int score;

    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Matches");
        setContentView(R.layout.activity_display_results);

//        scoreTV = findViewById(R.id.score);
        matchesRV = findViewById(R.id.matchesRV);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        fStore.collection("volunteers").document(userID).get(). addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
                daysV = (ArrayList) ds.get("daysOfTheWeek");
            }
        });


        // Getting all documents form Firestore and saving to ArrayList
        matchesArrayList = new ArrayList<>();
        matchesRV.setHasFixedSize(true);
        matchesRV.setLayoutManager(new LinearLayoutManager(this));

        matchesRVAdapter = new MatchesRVAdapter(matchesArrayList, this);
        matchesRV.setAdapter(matchesRVAdapter);

        fStore.collection("events").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d : list) {
//                        String ageE = d.get("age").toString();
//                        String ageEE = ageE + "age";
                        Matches m = d.toObject(Matches.class);
                        matchesArrayList.add(m);
                    }
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

//        for(int i=0; i<matchesArrayList.size(); i++) {
//            for(int k=0; k<i.size(); k++)
//        }

        matchesArrayList.forEach(event ->{
            String lat_lngE, dateE, formE, typeE;
            String[] latLng = null;
            int ageMinE, ageMaxE;

            lat_lngE = event.getLat_lng();
            dateE = event.getDateOfEvent();
            ageMinE = Integer.parseInt(event.getAgeMin());
            ageMaxE = Integer.parseInt(event.getAgeMax());
            formE = event.getFormOfVolunteering();
//            typeE = event.ge
            int score = 0;
            int score1;
            int score2;
            int score3;


            // String to LatLng
            latLng = lat_lngE.split(",");
            double latitudeE = Double.parseDouble(latLng[0]);
            double longitudeE = Double.parseDouble(latLng[1]);
//            LatLng locationE = new LatLng(latitudeE, longitudeE);

            Location locationE = new Location("locationB");
            locationE.setLatitude(latitudeE);
            locationE.setLongitude(longitudeE);

            // Calculate distance between locations
            double dis = locationV.distanceTo(locationE);



            if(distanceV <= dis) {
                score1 = score + 1;
            }else {score1 = score;}

            // Check if the form of volunteering is the same
            if(formV.equals(formE)) {
                score2 = score1 + 1;
            }else {score2 = score1;}


            // Check if age is within the range
            if(ageV>ageMinE && ageV<ageMaxE) {
//                score++;
                score3 = score2 + 1;
            }else {score3 = score2;}

//            scoreTV.setText(String.valueOf(score2));
//            scoreTV.setText(String.valueOf(ageMinE));

            event.setScore(score3);


        });

        matchesRVAdapter.notifyDataSetChanged();


    }
}