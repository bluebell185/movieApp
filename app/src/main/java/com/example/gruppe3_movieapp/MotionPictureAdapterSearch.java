package com.example.gruppe3_movieapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MotionPictureAdapterSearch extends RecyclerView.Adapter<MotionPictureAdapterSearch.MotionPictureViewHolder> {
    ArrayList<MotionPicture> motionPictureList = new ArrayList<>();

    public MotionPictureAdapterSearch(ArrayList<MotionPicture> motionPictureList){
        this.motionPictureList = motionPictureList;
    }

    @NonNull
    @Override
    public MotionPictureAdapterSearch.MotionPictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_motion_picture_search,
                parent, false);
        MotionPictureViewHolder vh = new MotionPictureViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MotionPictureAdapterSearch.MotionPictureViewHolder holder, int position) {
        MotionPicture p = motionPictureList.get(position);
        holder.tvTitleSearch.setText(p.getTitle());
        holder.tvYearSearch.setText(String.valueOf(p.getYear().substring(0,4)));
        if (p.getType().equals("movie")){
            holder.ivTypeSearch.setImageResource(R.drawable.ic_movie);
        }
        else if(p.getType().equals("series")){
            holder.ivTypeSearch.setImageResource(R.drawable.ic_series);
        }
        if (p.getCover().equals("N/A")){
            //falls kein Cover mitgeliefert wird
            holder.ivCoverSearch.setImageResource(R.drawable.nomoviepicture);
        }
        else {
            Picasso.get().load(p.getCover()).into(holder.ivCoverSearch);
        }
    }

    @Override
    public int getItemCount() {
        return motionPictureList.size();
    }

    public static class MotionPictureViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitleSearch;
        TextView tvYearSearch;
        ImageView ivTypeSearch;
        ImageView ivCoverSearch;

        public MotionPictureViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleSearch = itemView.findViewById(R.id.tvTitleSearch);
            tvYearSearch = itemView.findViewById(R.id.tvYearSearch);
            ivTypeSearch = itemView.findViewById(R.id.ivTypeSearch);
            ivCoverSearch = itemView.findViewById(R.id.ivCoverSearch);
        }
    }

    public String getItem(int id){
        return motionPictureList.get(id).imdbId;
    }
}
