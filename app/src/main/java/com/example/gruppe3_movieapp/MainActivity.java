package com.example.gruppe3_movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<MotionPicture> motionPictureList = new ArrayList<>();
    TextView tvTitleMain;
    ImageView ivCoverMain;
    Button btnAddMotionPictureMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitleMain = findViewById(R.id.tvTitleMain);
        ivCoverMain = findViewById(R.id.ivCoverMain);
        btnAddMotionPictureMain = findViewById(R.id.btnAddMotionPictureMain);
    }

    protected void onStart(){
        super.onStart();
        fillMotionPictureList();

        RecyclerView rvMain = findViewById(R.id.rvMain);

        final MotionPictureAdapterMain pa = new MotionPictureAdapterMain(motionPictureList);
        rvMain.setLayoutManager(new GridLayoutManager(this, 2));
        rvMain.setAdapter(pa);

        btnAddMotionPictureMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeActivity = new Intent(getApplicationContext(), SearchActivity.class);
                MainActivity.this.startActivity(changeActivity);
            }
        });
    }

    private void fillMotionPictureList(){
        //später die Attribute aus der DB als Objekte in Liste geben oder so

        MotionPicture m1 = new MotionPicture("Titel", R.drawable.ic_launcher_foreground);
        MotionPicture m2 = new MotionPicture("Title", R.drawable.ic_launcher_foreground);
        MotionPicture m3 = new MotionPicture("Titel", R.drawable.ic_launcher_foreground);

        motionPictureList.add(m1);
        motionPictureList.add(m2);
        motionPictureList.add(m3);
    }
}