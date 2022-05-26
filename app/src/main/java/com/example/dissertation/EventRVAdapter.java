package com.example.dissertation;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EventRVAdapter extends RecyclerView.Adapter<EventRVAdapter.ViewHolder> {

    private ArrayList<Events> eventsArrayList;
    private Context context;

    public EventRVAdapter(ArrayList<Events> eventsArrayList, Context context){
        this.eventsArrayList = eventsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public EventRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.events_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventRVAdapter.ViewHolder holder, int position) {
        Events events = eventsArrayList.get(position);
        holder.eventName.setText(events.getNameOfEvent());
        holder.eventDate.setText(events.getDateOfEvent());
        holder.eventLocation.setText(events.getLocationName());
        holder.eventForm.setText(events.getFormOfVolunteering());
        holder.eventAgeMin.setText("Ages: " + events.getAgeMin() + " - " + events.getAgeMax());
//        holder.eve
//        holder.eventID.setText(events.getEventID());
//        holder.days.setText((CharSequence) events.getDays());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewEvent.class);
                view.getContext().startActivity(intent);
            }
        });

//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth fAuth = FirebaseAuth.getInstance();
//                FirebaseFirestore fStore = FirebaseFirestore.getInstance();
//
//                String eventID = events.getEventID();
//                String userID = fAuth.getCurrentUser().getUid();
//
//                fStore.collection("events").document(eventID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//
//                        fStore.collection("users").document(userID).collection("events")
//                                .whereEqualTo("eventID", eventID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                QuerySnapshot ds = task.getResult();
//                                String evnID =
//                            }
//                        };
//
//                    }
//                })
//
//
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return eventsArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView eventName;
        private final TextView eventDate;
        private final TextView eventLocation;
//        private final TextView eventID;
        private final TextView eventForm;
        private final TextView eventAgeMin;
        ImageButton edit, delete;
//        private final TextView days;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.CVEventName);
            eventDate = itemView.findViewById(R.id.CVEventDate);
            eventLocation = itemView.findViewById(R.id.CVEventLocation);
//            eventID = itemView.findViewById(R.id.CVEventID);
            edit = itemView.findViewById(R.id.editEvent);
            delete = itemView.findViewById(R.id.deleteEvent);
            eventForm = itemView.findViewById(R.id.CVEventForm);
            eventAgeMin = itemView.findViewById(R.id.CVEventAgeRange);
//            days = itemView.findViewById(R.id.CVdays);
        }
    }


}
