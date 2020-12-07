package com.example.gruppe3_movieapp;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Muss alles in das SearchFragment übernommen werden
public class SearchActivity extends AppCompatActivity {
    ArrayList<MotionPicture> motionPictureList = new ArrayList<>();
    TextView tvTitleSearch;
    TextView tvYearSearch;
    ImageView ivTypeSearch;
    TextView tvDurationSearch;

    TextView tvApiErrorMessage;
    TextView tvOutputApiObject;
    RecyclerView rvSearch;

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
        tvYearSearch = findViewById(R.id.tvYearSearch);
        ivTypeSearch = findViewById(R.id.ivTypeSearch);
        ivCoverSearch = findViewById(R.id.ivCoverSearch);

        tvApiErrorMessage = findViewById(R.id.tvApiErrorMessage);
        tvOutputApiObject = findViewById(R.id.tvOutputApiObject);
        rvSearch = findViewById(R.id.rvSearch);


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

                            showData(motionPictureApiResults);

                        } else {

                            tvOutputApiObject.setText(R.string.outputApiObject);
                        }

                  /*  MotionPicture motionPicture = motionPictureApiResults.getMotionPicture().get(0); -- muss in die DetailActivity rein das alles auskommenntierte
                    if (motionPicture != null){

                        // Daten ausgeben!
                    }
                    else {
                        // outputApiObject.setText(R.string.outputApiObject); // hier kommt aber eine andere TextView rein extra für die DetailView
                    } */
                }
                else {
                    Log.d("MainActivity", "getMotionPicture: onResponse NOT successfull");
                    showErrorMessage(getString(R.string.motionPicture_error_NOT_succsessfull));

                }
            }

            @Override
            public void onFailure(Call<MotionPictureApiResults> call, Throwable t) {
                Log.d("MainActivity", "getMotionPicture: onFailure " + t.getMessage());

                showErrorMessage(getString(R.string.motionPicture_error_on_failure));


            }
        });
    }

    private void showData(MotionPictureApiResults motionPictureApiResults){
        rvSearch.setVisibility(View.VISIBLE);
        tvOutputApiObject.setVisibility(View.INVISIBLE);
        //output ausblenden
        motionPictureList.addAll(motionPictureApiResults.getMotionPicture());
        pa.notifyDataSetChanged();
        tvApiErrorMessage.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage(String failure){
        tvApiErrorMessage.setVisibility(View.VISIBLE);
        tvApiErrorMessage.setText(failure);
        tvOutputApiObject.setVisibility(View.INVISIBLE);
        rvSearch.setVisibility(View.INVISIBLE);
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
