package com.example.gruppe3_movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ArrayList<MotionPicture> motionPictureList = new ArrayList<>();
    TextView tvTitleMain;
    TextView tvMain;
    ImageView ivCoverMain;
    Button btnAddMotionPictureMain;
    static MotionPictureDao dbRepo;
    static AppDatabase db;
    MotionPictureRepo motionPictureRepo = new MotionPictureRepo();
    MotionPictureAdapterMain pa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Datenbank einmalig initialisieren...
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "MovieAppDB").
                allowMainThreadQueries().
                fallbackToDestructiveMigration().
                build();
        dbRepo = db.motionPictureDao();

        tvTitleMain = findViewById(R.id.tvTitleMain);
        tvMain = findViewById(R.id.tvMain);
        ivCoverMain = findViewById(R.id.ivCoverMain);
        btnAddMotionPictureMain = findViewById(R.id.btnAddMotionPictureMain);

        fillMotionPictureList();    //hier später die favorisierten, aber noch nicht gesehenen Filme anzeigen als erste Sicht des Users
    }

    protected void onStart(){
        super.onStart();

        RecyclerView rvMain = findViewById(R.id.rvMain);

        pa = new MotionPictureAdapterMain(motionPictureList);
        rvMain.setLayoutManager(new GridLayoutManager(this, 3));
        rvMain.setAdapter(pa);

        if(motionPictureList.isEmpty()){
            tvMain.setText(getString(R.string.tv_main_empty_rv));
        }
        else{
            tvMain.setText(getString(R.string.tv_main_show_favorite));
        }

        rvMain.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rvMain, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent iMovieDetailView = new Intent(getApplicationContext(), MovieDetailActivity.class);
                        // getItem holt die imdbId her, diese wird an das Intent übergeben
                        iMovieDetailView.putExtra(Intent.EXTRA_TEXT, pa.getItem(position));
                        MainActivity.this.startActivity(iMovieDetailView);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // Kann man noch überlegen was sinnvolles einzufügen.
                    }
                })
        );

        btnAddMotionPictureMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeActivity = new Intent(getApplicationContext(), SearchActivity.class);
                MainActivity.this.startActivity(changeActivity);
            }
        });
    }

    private void fillMotionPictureList(){
        motionPictureList.addAll((ArrayList<MotionPicture>) dbRepo.getAll().stream().filter(c -> c.isMarkedAsFavorite()).collect(Collectors.toList()));

        //das hier löschen, wenn der Rest funktioniert:
        MotionPicture m1 = new MotionPicture("Titel", "https://m.media-amazon.com/images/M/MV5BN2RhMTcxNDQtM2NiZC00OTc0LWFhNGMtNWI4YjMwOWRlOGZhXkEyXkFqcGdeQXVyNTM4NDU4NDA@._V1_SX300.jpg", "1", true, true);
        MotionPicture m2 = new MotionPicture("Title", "https://m.media-amazon.com/images/M/MV5BMGJjMzViZjktYmE3NC00M2YwLTk2YWEtZWMzZWZmNGNhNTI1XkEyXkFqcGdeQXVyMjYwNDA2MDE@._V1_SX300.jpg", "2",true, false);
        MotionPicture m3 = new MotionPicture("Titel", "https://m.media-amazon.com/images/M/MV5BYzlhMDg2YTItNmRjNS00MDdjLTlhMTItMWQ4M2FiMjgwYjM2XkEyXkFqcGdeQXVyMjYwNDA2MDE@._V1_SX300.jpg", "3",false, true);
        MotionPicture m4 = new MotionPicture("Titel", "https://m.media-amazon.com/images/M/MV5BN2RhMTcxNDQtM2NiZC00OTc0LWFhNGMtNWI4YjMwOWRlOGZhXkEyXkFqcGdeQXVyNTM4NDU4NDA@._V1_SX300.jpg","4", true, false);
        MotionPicture m5 = new MotionPicture("Title", "https://m.media-amazon.com/images/M/MV5BMGJjMzViZjktYmE3NC00M2YwLTk2YWEtZWMzZWZmNGNhNTI1XkEyXkFqcGdeQXVyMjYwNDA2MDE@._V1_SX300.jpg", "5", false, true);
        MotionPicture m6 = new MotionPicture("Titel", "https://m.media-amazon.com/images/M/MV5BYzlhMDg2YTItNmRjNS00MDdjLTlhMTItMWQ4M2FiMjgwYjM2XkEyXkFqcGdeQXVyMjYwNDA2MDE@._V1_SX300.jpg", "6", true, true);
        MotionPicture m7 = new MotionPicture("Titel", "https://m.media-amazon.com/images/M/MV5BN2RhMTcxNDQtM2NiZC00OTc0LWFhNGMtNWI4YjMwOWRlOGZhXkEyXkFqcGdeQXVyNTM4NDU4NDA@._V1_SX300.jpg", "7", false, true);
        MotionPicture m8 = new MotionPicture("Title", "https://m.media-amazon.com/images/M/MV5BMGJjMzViZjktYmE3NC00M2YwLTk2YWEtZWMzZWZmNGNhNTI1XkEyXkFqcGdeQXVyMjYwNDA2MDE@._V1_SX300.jpg", "8", true, true);
        MotionPicture m9 = new MotionPicture("Titel", "https://m.media-amazon.com/images/M/MV5BYzlhMDg2YTItNmRjNS00MDdjLTlhMTItMWQ4M2FiMjgwYjM2XkEyXkFqcGdeQXVyMjYwNDA2MDE@._V1_SX300.jpg", "9", true, false);

        //START TEST
        db.clearAllTables(); //Um Tabellen zu leeren, sonst gibts Fehler bei doppelter imdbId!
        //Kurzer Test: Du hast 3 Objekte erstellt (m1,m2,m3). Diese in der DB speichern mit Insert,
        dbRepo.insert(m1,m2,m3,m4,m5,m6,m7,m8,m9);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        motionPictureList.clear();
        switch(item.getItemId()) {
            case R.id.menu_item_show_all_main:{
                // alle Filme/Serien anzeigen, die favorisiert und/oder angesehen wurden
                tvMain.setText(getString(R.string.tv_main_show_all));
                motionPictureList.addAll((ArrayList<MotionPicture>) dbRepo.getAll());
                break;
            }
            case R.id.menu_item_favorite_main:{
                // alle favorisierten Filme/Serien anzeigen
                tvMain.setText(getString(R.string.tv_main_show_favorite));
                motionPictureList.addAll((ArrayList<MotionPicture>) dbRepo.getAll().stream().filter(c -> c.isMarkedAsFavorite()).collect(Collectors.toList()));
                break;
            }
            case R.id.menu_item_watched_main:{
                // alle angesehenen Filme/Serien anzeigen
                tvMain.setText(getString(R.string.tv_main_show_seen));
                motionPictureList.addAll((ArrayList<MotionPicture>) dbRepo.getAll().stream().filter(c -> c.isMarkedAsSeen()).collect(Collectors.toList()));
                break;
            }
        }
        pa.notifyDataSetChanged();
        return true;
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