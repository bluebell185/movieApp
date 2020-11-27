package com.example.gruppe3_movieapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ArrayList<MotionPicture> motionPictureList = new ArrayList<>();
    TextView tvTitleSearch;
    TextView tvDurationSearch;
    TextView tvRatingSearch;
    ImageView ivCoverSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        tvTitleSearch = findViewById(R.id.tvTitleSearch);
        tvDurationSearch = findViewById(R.id.tvDurationSearch);
        tvRatingSearch = findViewById(R.id.tvRatingSearch);
        ivCoverSearch = findViewById(R.id.ivCoverSearch);
    }

    protected void onStart(){
        super.onStart();
        fillMotionPictureList();

        RecyclerView motionPictureView = findViewById(R.id.rvSearch);

        final MotionPictureAdapter pa = new MotionPictureAdapter(motionPictureList);
        motionPictureView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        motionPictureView.setAdapter(pa);
    }

    private void fillMotionPictureList(){
        //später die Liste, die die API zurückgibt an motionPictureList übergeben ODER dierekt die zurückgegebne Liste in die RV

        MotionPicture m1 = new MotionPicture("Titel", 300.1, 9.8, R.drawable.ic_launcher_foreground);
        MotionPicture m2 = new MotionPicture("Title", 300.1, 9.8, R.drawable.ic_launcher_foreground);
        MotionPicture m3 = new MotionPicture("Titel", 300.1, 9.8, R.drawable.ic_launcher_foreground);

        motionPictureList.add(m1);
        motionPictureList.add(m2);
        motionPictureList.add(m3);
    }
}
