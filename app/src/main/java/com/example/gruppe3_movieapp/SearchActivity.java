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
    TextView tvYearSearch;
    ImageView ivTypeSearch;
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
        tvYearSearch = findViewById(R.id.tvYearSearch);
        ivTypeSearch = findViewById(R.id.ivTypeSearch);
        ivCoverSearch = findViewById(R.id.ivCoverSearch);

        sp  = getPreferences(Context.MODE_PRIVATE);
        lastSearchExpression = sp.getString("lastSearchExpression", "welcome");

         getfilteredMotionPictureTitle(lastSearchExpression);
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

    // Sobald die Daten und Inhalte fest sind, muss noch festegelegt werden wann was angezeigt wird und welche Errormessage angezeigt werden kann
    private void getfilteredMotionPictureTitle(String title){
        motionPictureRepo.getFilteredMotionPictureTitle( title, new Callback<MotionPictureApiResults>() {
            @Override
            public void onResponse(Call<MotionPictureApiResults> call, Response<MotionPictureApiResults> response) {
                if (response.isSuccessful()){
                    Log.d("MainActivity", "getMotionPicture: onResponse successfull");

                        MotionPictureApiResults motionPictureApiResults = response.body();
                        if (motionPictureApiResults.getMotionPicture() != null) {
                            motionPictureList.addAll(motionPictureApiResults.getMotionPicture());
                            pa.notifyDataSetChanged();
                        } else {
                            //Fehlermeldung einbauen wie "Keine Filme gefunden, suche nach einem anderen Titel" oder so
                            // wenn das Programm hierher kommt, ist die motionPictureList leer
                        }

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
