package com.example.gruppe3_movieapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ArrayList<MotionPicture> motionPictureList = new ArrayList<>();
    TextView tvTitleSearch;
    TextView tvDurationSearch;
    RatingBar rbRatingSearch;
    ImageView ivCoverSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        tvTitleSearch = findViewById(R.id.tvTitleSearch);
        tvDurationSearch = findViewById(R.id.tvDurationSearch);
        rbRatingSearch = findViewById(R.id.rbRatingSearch);
        ivCoverSearch = findViewById(R.id.ivCoverSearch);

        fillMotionPictureList();
    }

    protected void onStart(){
        super.onStart();

        RecyclerView rvSearch = findViewById(R.id.rvSearch);

        final MotionPictureAdapterSearch pa = new MotionPictureAdapterSearch(motionPictureList);
        rvSearch.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvSearch.setAdapter(pa);
    }

    private void fillMotionPictureList(){
        //später die Liste, die die API zurückgibt an motionPictureList übergeben ODER dierekt die zurückgegebne Liste in die RV

        MotionPicture m1 = new MotionPicture("Titel", 300.1, (float) 9.8, "https://m.media-amazon.com/images/M/MV5BMzRmNjJhYTctMjY5My00ZWE4LWFiMTEtZGMzYzMxNmQ5OTllL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyOTc2Mzg5OQ@@._V1_SX300.jpg");
        MotionPicture m2 = new MotionPicture("Title", 300.1, (float) 3.2, "https://m.media-amazon.com/images/M/MV5BMzRmNjJhYTctMjY5My00ZWE4LWFiMTEtZGMzYzMxNmQ5OTllL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyOTc2Mzg5OQ@@._V1_SX300.jpg");
        MotionPicture m3 = new MotionPicture("Titel", 300.1, (float) 5.5, "https://m.media-amazon.com/images/M/MV5BMDhhN2QwNGUtODI1OC00NDRkLWJkMjgtZmM3MDY4MDI0NGE2XkEyXkFqcGdeQXVyNjE4MDMwMjk@._V1_SX300.jpg");

        motionPictureList.add(m1);
        motionPictureList.add(m2);
        motionPictureList.add(m3);
    }
}
