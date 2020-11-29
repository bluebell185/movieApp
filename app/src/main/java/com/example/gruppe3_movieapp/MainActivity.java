package com.example.gruppe3_movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.gruppe3_movieapp.room.AppDatabase;
import com.example.gruppe3_movieapp.room.MotionPictureDao;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ArrayList<MotionPicture> motionPictureList = new ArrayList<>();
    TextView tvTitleMain;
    ImageView ivCoverMain;
    Button btnAddMotionPictureMain;
    static MotionPictureDao dbRepo;
    static AppDatabase db;
    MotionPictureRepo motionPictureRepo = new MotionPictureRepo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getMotionPicture;

        //Datenbank einmalig initialisieren...
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "MovieAppDB").
                allowMainThreadQueries().
                build();
        dbRepo = db.motionPictureDao();
        //Verwendung der Datenbank mit -> db.methode()


        tvTitleMain = findViewById(R.id.tvTitleMain);
        ivCoverMain = findViewById(R.id.ivCoverMain);
        btnAddMotionPictureMain = findViewById(R.id.btnAddMotionPictureMain);

        fillMotionPictureList();
    }

    protected void onStart(){
        super.onStart();

        RecyclerView rvMain = findViewById(R.id.rvMain);

        final MotionPictureAdapterMain pa = new MotionPictureAdapterMain(motionPictureList);
        rvMain.setLayoutManager(new GridLayoutManager(this, 3));
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

        MotionPicture m1 = new MotionPicture("Titel", "https://m.media-amazon.com/images/M/MV5BN2RhMTcxNDQtM2NiZC00OTc0LWFhNGMtNWI4YjMwOWRlOGZhXkEyXkFqcGdeQXVyNTM4NDU4NDA@._V1_SX300.jpg");
        MotionPicture m2 = new MotionPicture("Title", "https://m.media-amazon.com/images/M/MV5BMGJjMzViZjktYmE3NC00M2YwLTk2YWEtZWMzZWZmNGNhNTI1XkEyXkFqcGdeQXVyMjYwNDA2MDE@._V1_SX300.jpg");
        MotionPicture m3 = new MotionPicture("Titel", "https://m.media-amazon.com/images/M/MV5BYzlhMDg2YTItNmRjNS00MDdjLTlhMTItMWQ4M2FiMjgwYjM2XkEyXkFqcGdeQXVyMjYwNDA2MDE@._V1_SX300.jpg");

        motionPictureList.add(m1);
        motionPictureList.add(m2);
        motionPictureList.add(m3);
        motionPictureList.add(m1);
        motionPictureList.add(m2);
        motionPictureList.add(m3);
        motionPictureList.add(m1);
        motionPictureList.add(m2);
        motionPictureList.add(m3);
    }

 // Sobald die Daten und Inhalte fest sind, muss noch festegelegt werden wann was angezeigt wird und welche Errormessage angezeigt werden kann
    private void getMotionPicture(){
        motionPictureRepo.getMotionPicture(new Callback<MotionPictureApiResults>() {
            @Override
            public void onResponse(Call<MotionPictureApiResults> call, Response<MotionPictureApiResults> response) {
                if (response.isSuccessful()){
                    Log.d("MainActivity", "getMotionPicture: onResponse successfull");
                    MotionPictureApiResults motionPictureApiResults = response.body();
                    MotionPicture motionPicture = motionPictureApiResults.getMotionPicture().get(0);
                    if (motionPicture != null){

                        // Daten ausgeben!
                    }
                    else {
                        // textview.setText(R.string.ErrorMessage);
                    }
                }
                else {
                    Log.d("MainActivity", "getMotionPicture: onResponse NOT successfull");
                    // textview.setText(R.string.ErrorMessage);
                }
            }

            @Override
            public void onFailure(Call<MotionPictureApiResults> call, Throwable t) {
                Log.d("MainActivity", "getMotionPicture: onFailure");
                // textview.setText(R.string.ErrorMessage);

            }
        });
    }
}