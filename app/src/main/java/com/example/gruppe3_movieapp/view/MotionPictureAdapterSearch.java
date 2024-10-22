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
public class MotionPictureAdapterSearch extends RecyclerView.Adapter<MotionPictureAdapterSearch.MotionPictureViewHolder> {
    ArrayList<MotionPicture> motionPictureList;

    public MotionPictureAdapterSearch(ArrayList<MotionPicture> motionPictureList){
        this.motionPictureList = motionPictureList;
    }

    @NonNull
    @Override
    public MotionPictureAdapterSearch.MotionPictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_motion_picture_search,
                parent, false);
        return new MotionPictureViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MotionPictureAdapterSearch.MotionPictureViewHolder holder, int position) {
        MotionPicture p = motionPictureList.get(position);
        holder.tvTitleSearch.setText(p.getTitle());
        holder.tvYearSearch.setText(p.getYear().substring(0, 4));
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
        return motionPictureList.get(id).getImdbId();
    }
}
