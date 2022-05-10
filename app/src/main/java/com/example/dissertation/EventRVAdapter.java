package com.example.dissertation;

import android.content.Context;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.eventID.setText(events.getEventID());
//        holder.days.setText((CharSequence) events.getDays());
    }

    @Override
    public int getItemCount() {
        return eventsArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView eventName;
        private final TextView eventDate;
        private final TextView eventLocation;
        private final TextView eventID;
//        private final TextView days;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.CVEventName);
            eventDate = itemView.findViewById(R.id.CVEventDate);
            eventLocation = itemView.findViewById(R.id.CVEventLocation);
            eventID = itemView.findViewById(R.id.CVEventID);
//            days = itemView.findViewById(R.id.CVdays);
        }
    }


}
