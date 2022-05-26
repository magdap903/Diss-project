package com.example.dissertation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MatchesRVAdapter extends RecyclerView.Adapter<MatchesRVAdapter.ViewHolder> {

    private ArrayList<Matches> matchesArrayList;
    private Context context;
    int size;

    public MatchesRVAdapter(ArrayList<Matches> matchesArrayList, Context context) {
        this.matchesArrayList = matchesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MatchesRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(context).inflate(R.layout.matches_view, parent, false));
    }

    public void onBindViewHolder(@NonNull MatchesRVAdapter.ViewHolder holder, int position) {
        Matches matches = matchesArrayList.get(position);
        holder.matchName.setText(matches.getNameOfEvent());
        holder.matchDate.setText(matches.getDateOfEvent());
        holder.matchLocation.setText(matches.getLocationName());
        holder.matchDistance.setText(matches.getDistance());
        holder.matchForm.setText(matches.getFormOfVolunteering());
        holder.matchScore.setText(matches.getScore());
        holder.matchAge.setText(matches.getAge());



        holder.apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore fStore = FirebaseFirestore.getInstance();
                FirebaseAuth fAuth = FirebaseAuth.getInstance();

//                String eventID = matches.getEventID();

                String eventID = matches.getEventID();
//                fStore.collection("events").
//                String eventID = documentReference.getId();
                String userID = fAuth.getCurrentUser().getUid();

                Map<String, Object> event = new HashMap<>();
                event.put("userID", userID);

                Map<String, Object> user = new HashMap<>();
                user.put("eventID", eventID);

//                ArrayList<String> participants = new ArrayList<>();
//
//                fStore.collection("events").document(eventID).collection("participants")
//                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()) {
//                            for(QueryDocumentSnapshot document : task.getResult()) {
//                                String evID = document.get("userID").toString();
//                                participants.add(evID);
//                            }
//                             size = participants.size();
//
//                        }
//                    }
//                });
//
//                int numb = Integer.parseInt(matches.getNumberOfVolunteers());
//
//                if(size >= numb) {
//                    holder.apply.setEnabled(false);
//                    holder.maxPart.setVisibility(View.VISIBLE);
//
//                }
//                else {
                    fStore.collection("events").document(eventID).collection("participants")
                            .add(event).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference docReference) {

                            fStore.collection("volunteers").document(userID).collection("applied")
                                    .add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Intent intent = new Intent(view.getContext(), ApplySuccess.class);
                                    view.getContext().startActivity(intent);                            }
                            });
                        }
                    });
//                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return matchesArrayList.size();
    }


    class ViewHolder extends  RecyclerView.ViewHolder {
        private final TextView matchName;
        private final TextView matchDate;
        private final TextView matchLocation;
        private final TextView matchDistance;
        private final TextView matchForm;
        private final TextView matchScore;
        private final TextView matchAge;
        private final Button apply;
        private final TextView maxPart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            matchName = itemView.findViewById(R.id.CVMatchName);
            matchDate = itemView.findViewById(R.id.CVMatchDate);
            matchLocation = itemView.findViewById(R.id.CVMatchLocation);
            matchDistance = itemView.findViewById(R.id.CVMatchDistance);
            matchForm = itemView.findViewById(R.id.CVMatchForm);
            matchScore = itemView.findViewById(R.id.score);
            matchAge = itemView.findViewById(R.id.CVMatchAge);
            apply = itemView.findViewById(R.id.CVapplyMatch);
            maxPart = itemView.findViewById(R.id.CVMaxPart);
        }

    }



}
