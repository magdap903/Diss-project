package com.example.dissertation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MatchesRVAdapter extends RecyclerView.Adapter<MatchesRVAdapter.ViewHolder> {

    private ArrayList<Matches> matchesArrayList;
    private Context context;

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            matchName = itemView.findViewById(R.id.CVMatchName);
            matchDate = itemView.findViewById(R.id.CVMatchDate);
            matchLocation = itemView.findViewById(R.id.CVMatchLocation);
            matchDistance = itemView.findViewById(R.id.CVMatchDistance);
            matchForm = itemView.findViewById(R.id.CVMatchForm);
            matchScore = itemView.findViewById(R.id.CVScore);


        }

    }



}
