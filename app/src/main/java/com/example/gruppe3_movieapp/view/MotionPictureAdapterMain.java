package com.example.gruppe3_movieapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gruppe3_movieapp.R;
import com.example.gruppe3_movieapp.model.MotionPicture;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
/**
 * @author Kathrin Ulmer
 */
public class MotionPictureAdapterMain extends RecyclerView.Adapter<MotionPictureAdapterMain.MotionPictureViewHolder> {
    ArrayList<MotionPicture> motionPictureList;

    public MotionPictureAdapterMain(ArrayList<MotionPicture> motionPictureList){
        this.motionPictureList = motionPictureList;
    }

    @NonNull
    @Override
    public MotionPictureAdapterMain.MotionPictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_motion_picture_main,
                parent, false);
        return new MotionPictureViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MotionPictureAdapterMain.MotionPictureViewHolder holder, int position) {
        MotionPicture p = motionPictureList.get(position);
        holder.tvTitleMain.setText(p.getTitle());
        Picasso.get().load(p.getCover()).into(holder.ivCoverMain);
        if (p.isMarkedAsSeen()){
            holder.ivSeenMain.setImageResource(R.drawable.ic_watched);
        }
        else{
           holder.ivSeenMain.setImageResource(R.drawable.ic_not_seen);
        }
        if(p.isMarkedAsFavorite()){
            holder.ivFavoriteMain.setImageResource(R.drawable.ic_star_favorite);
        }
        else{
            holder.ivFavoriteMain.setImageResource(R.drawable.ic_star_set_favorite);
        }
    }

    @Override
    public int getItemCount() {
        return motionPictureList.size();
    }

    public static class MotionPictureViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitleMain;
        ImageView ivCoverMain;
        ImageView ivSeenMain;
        ImageView ivFavoriteMain;

        public MotionPictureViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleMain = itemView.findViewById(R.id.tvTitleMain);
            ivCoverMain = itemView.findViewById(R.id.ivCoverMain);
            ivSeenMain = itemView.findViewById(R.id.ivSeenMain);
            ivFavoriteMain = itemView.findViewById(R.id.ivFavoriteMain);
        }
    }

    public String getItem(int id){
        return motionPictureList.get(id).getImdbId();
    }
}
