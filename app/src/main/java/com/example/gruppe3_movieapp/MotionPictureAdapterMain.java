package com.example.gruppe3_movieapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MotionPictureAdapterMain extends RecyclerView.Adapter<MotionPictureAdapterMain.MotionPictureViewHolder> {
    ArrayList<MotionPicture> motionPictureList = new ArrayList<>();

    public MotionPictureAdapterMain(ArrayList<MotionPicture> motionPictureList){
        this.motionPictureList = motionPictureList;
    }

    @NonNull
    @Override
    public MotionPictureAdapterMain.MotionPictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_motion_picture_main,
                parent, false);
        MotionPictureAdapterMain.MotionPictureViewHolder vh = new MotionPictureAdapterMain.MotionPictureViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MotionPictureAdapterMain.MotionPictureViewHolder holder, int position) {
        MotionPicture p = motionPictureList.get(position);
        holder.tvTitleMain.setText(p.getTitle());
        holder.ivCoverMain.setImageResource(p.getCover());
    }

    @Override
    public int getItemCount() {
        return motionPictureList.size();
    }

    public static class MotionPictureViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitleMain;
        ImageView ivCoverMain;

        public MotionPictureViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleMain = itemView.findViewById(R.id.tvTitleMain);
            ivCoverMain = itemView.findViewById(R.id.ivCoverMain);
        }
    }
}
