package com.example.gruppe3_movieapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Muss alles in das SearchFragment übernommen werden
public class SearchActivity extends AppCompatActivity {
    ArrayList<MotionPicture> motionPictureList = new ArrayList<>();
    TextView tvTitleSearch;
    TextView tvDurationSearch;
    RatingBar rbRatingSearch;
    ImageView ivCoverSearch;
    MotionPictureAdapterSearch pa;
    MotionPictureRepo motionPictureRepo = new MotionPictureRepo();
    SharedPreferences sp;
    String lastSearchExpression = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        tvTitleSearch = findViewById(R.id.tvTitleSearch);
        tvDurationSearch = findViewById(R.id.tvDurationSearch);
        rbRatingSearch = findViewById(R.id.rbRatingSearch);
        ivCoverSearch = findViewById(R.id.ivCoverSearch);

        sp  = getPreferences(Context.MODE_PRIVATE);
        lastSearchExpression = sp.getString("lastSearchExpression", "welcome");

         getfilteredMotionPictureTitle(lastSearchExpression);
        //fillMotionPictureList();
    }

    protected void onStart(){
        super.onStart();

        RecyclerView rvSearch = findViewById(R.id.rvSearch);

        pa = new MotionPictureAdapterSearch(motionPictureList);
        rvSearch.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvSearch.setAdapter(pa);

        rvSearch.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rvSearch, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent iMovieDetailView = new Intent(getApplicationContext(), MovieDetailActivity.class);
                        // getItem holt die imdbId her, diese wird an das Intent übergeben
                        iMovieDetailView.putExtra(Intent.EXTRA_TEXT, pa.getItem(position));
                        SearchActivity.this.startActivity(iMovieDetailView);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // Kann man noch überlegen was sinnvolles einzufügen.
                    }
                })
        );
    }

  /*  private void fillMotionPictureList(){
        //später die Liste, die die API zurückgibt an motionPictureList übergeben ODER dierekt die zurückgegebne Liste in die RV

        MotionPicture m1 = new MotionPicture("1", "Titel", 300, (float) 9.8, "https://m.media-amazon.com/images/M/MV5BMzRmNjJhYTctMjY5My00ZWE4LWFiMTEtZGMzYzMxNmQ5OTllL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyOTc2Mzg5OQ@@._V1_SX300.jpg");
        MotionPicture m2 = new MotionPicture("2","Title", 301, (float) 3.2, "https://m.media-amazon.com/images/M/MV5BMzRmNjJhYTctMjY5My00ZWE4LWFiMTEtZGMzYzMxNmQ5OTllL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyOTc2Mzg5OQ@@._V1_SX300.jpg");
        MotionPicture m3 = new MotionPicture("3","Titel", 30, (float) 5.5, "https://m.media-amazon.com/images/M/MV5BMDhhN2QwNGUtODI1OC00NDRkLWJkMjgtZmM3MDY4MDI0NGE2XkEyXkFqcGdeQXVyNjE4MDMwMjk@._V1_SX300.jpg");


        m1.getRatings().add(new Rating("IMDB", "2/10"));
        motionPictureList.add(m1);
        motionPictureList.add(m2);
        motionPictureList.add(m3);

        //START TEST
        //db.clearAllTables(); //Um Tabellen zu leeren, sonst gibts Fehler bei doppelter imdbId!
        //Kurzer Test: Du hast 3 Objekte erstellt (m1,m2,m3). Diese in der DB speichern mit Insert,
        dbRepo.insert(m1,m2,m3);

        //und anschließend wieder holen mit getAll()
        motionPictureList = (ArrayList<MotionPicture>) dbRepo.getAll();
        //END
    } */

    // Sobald die Daten und Inhalte fest sind, muss noch festegelegt werden wann was angezeigt wird und welche Errormessage angezeigt werden kann
    private void getfilteredMotionPictureTitle(String title){
        motionPictureRepo.getFilteredMotionPictureTitle( title, new Callback<MotionPictureApiResults>() {
            @Override
            public void onResponse(Call<MotionPictureApiResults> call, Response<MotionPictureApiResults> response) {
                if (response.isSuccessful()){
                    Log.d("MainActivity", "getMotionPicture: onResponse successfull");
                    MotionPictureApiResults motionPictureApiResults = response.body();
                    motionPictureList.addAll( motionPictureApiResults.getMotionPicture());
                    pa.notifyDataSetChanged();

                  /*  MotionPicture motionPicture = motionPictureApiResults.getMotionPicture();
                    if (motionPicture != null){

                        // Daten ausgeben!
                    }
                    else {
                        // textview.setText(R.string.ErrorMessage);
                    } */
                }
                else {
                    Log.d("MainActivity", "getMotionPicture: onResponse NOT successfull");
                    // textview.setText(R.string.ErrorMessage);
                }
            }

            @Override
            public void onFailure(Call<MotionPictureApiResults> call, Throwable t) {
                Log.d("MainActivity", "getMotionPicture: onFailure " + t.getMessage());
                // textview.setText(R.string.ErrorMessage);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView sv = (SearchView) item.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //setzen der Shared Preferences
                SharedPreferences.Editor spe = sp.edit();
                spe.putString("lastSearchExpression", query);
                spe.apply();

                motionPictureList.clear();
                //API-Aufruf mit Titelfilterung
                getfilteredMotionPictureTitle(query);
                //Aktualisieren des Adapters
                pa.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //keine Funktion hier, da sonst bei jedem Buchstaben eine neue API-Anfrage gestellt werden muss
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.search:{
                // Suchfeld aufklappen oder so
                //Wird vermutlich nicht benötigt, das hier -> TODO entfernen u.U.
                break;
            }
        }
        pa.notifyDataSetChanged();
        return true;
    }
}
