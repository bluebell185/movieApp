package com.example.gruppe3_movieapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MotionPictureAdapter extends RecyclerView.Adapter<MotionPictureAdapter.MotionPictureViewHolder> {
    ArrayList<MotionPicture> motionPictureList = new ArrayList<>();

    public MotionPictureAdapter(ArrayList<MotionPicture> motionPictureList){
        this.motionPictureList = motionPictureList;
    }

    @NonNull
    @Override
    public MotionPictureAdapter.MotionPictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_motion_picture,
                parent, false);
        MotionPictureViewHolder vh = new MotionPictureViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MotionPictureAdapter.MotionPictureViewHolder holder, int position) {
        MotionPicture p = motionPictureList.get(position);
        holder.tvTitleSearch.setText(p.getTitle());
        holder.tvDurationSearch.setText(String.valueOf(p.getDuration()));
        holder.tvRatingSearch.setText(String.valueOf(p.getRating()));
        holder.ivCoverSearch.setImageResource(p.getCover());
    }

    @Override
    public int getItemCount() {
        return motionPictureList.size();
    }

    public static class MotionPictureViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitleSearch;
        TextView tvDurationSearch;
        TextView tvRatingSearch;
        ImageView ivCoverSearch;

        public MotionPictureViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleSearch = itemView.findViewById(R.id.tvTitleSearch);
            tvDurationSearch = itemView.findViewById(R.id.tvDurationSearch);
            tvRatingSearch = itemView.findViewById(R.id.tvRatingSearch);
            ivCoverSearch = itemView.findViewById(R.id.ivCoverSearch);
        }
    }
}
