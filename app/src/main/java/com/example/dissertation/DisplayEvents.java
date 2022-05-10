package com.example.dissertation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
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

public class DisplayEvents extends AppCompatActivity {

    private RecyclerView eventRV;
    private ArrayList<Events> eventsArrayList;
    private EventRVAdapter eventRVAdapter;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    String userID;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Events");
        setContentView(R.layout.activity_display_events);

        eventRV = findViewById(R.id.eventsRV);
        text = findViewById(R.id.textView37);

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

        fStore.collection("users").document(userID).collection("events").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d : list) {
                                Events e = d.toObject(Events.class);

                                eventsArrayList.add(e);
                            }
                            eventRVAdapter.notifyDataSetChanged();
                            text.setText(eventsArrayList.toString());
                        }
                        else {
                            Toast.makeText(DisplayEvents.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DisplayEvents.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });


    }
}