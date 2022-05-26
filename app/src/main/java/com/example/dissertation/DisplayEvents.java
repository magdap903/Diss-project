package com.example.dissertation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DisplayEvents extends AppCompatActivity {

    private RecyclerView eventRV;
    private ArrayList<Events> eventsArrayList;
    private EventRVAdapter eventRVAdapter;

    TextView eventsText, moreInfo;
    FloatingActionButton addNew;
    ImageButton edit, delete;
    Button signout;

    String typeE;
    ArrayList days = new ArrayList();

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Events");
        setContentView(R.layout.activity_display_events);

        eventRV = findViewById(R.id.eventsRV);
        eventsText = findViewById(R.id.eventsText);
        addNew = findViewById(R.id.addEvent);
        moreInfo = findViewById(R.id.CVmoreInfo);
        edit = findViewById(R.id.editEvent);
        delete = findViewById(R.id.deleteEvent);
        signout = findViewById(R.id.signoutEvent);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

//        fStore.collection("users").document(userID).collection("events").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()) {
//                            QuerySnapshot doc = task.getResult();
//                            String name = doc.get("nameOfEvent").toString();
//                        }
//                    }
//                })

        eventsArrayList = new ArrayList<>();
        eventRV.setHasFixedSize(true);
        eventRV.setLayoutManager(new LinearLayoutManager(this));

        eventRVAdapter = new EventRVAdapter(eventsArrayList, this);
        eventRV.setAdapter(eventRVAdapter);

        fStore.collection("events").whereEqualTo("organisationID", userID).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d : list) {
                                Events e = d.toObject(Events.class);

                                eventsArrayList.add(e);
                                eventsArrayList.forEach(event -> {

                                    typeE = event.getTypeOfVolunteering();
                                    days = event.getDaysOfTheWeek();

                                    if(typeE.equals("Regular")) {
                                        event.setDateOfEvent(days.toString());
                                    }

                                });
                            }
                            int numberOfEvents = eventsArrayList.size();
                            if(numberOfEvents != 1) {
                                eventsText.setText(numberOfEvents + " events found");
                            }
                            else{
                                eventsText.setText(numberOfEvents + " event found");
                            }

                            eventRVAdapter.notifyDataSetChanged();
                        }
                        else{
                            eventsText.setText("No events found. Create a new event");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DisplayEvents.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });

//        moreInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), EventInfo.class);
//                startActivity(intent);
//            }
//        });

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewEvent.class);
                startActivity(intent);
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }
}