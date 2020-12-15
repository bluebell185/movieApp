package com.example.gruppe3_movieapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.gruppe3_movieapp.AppConstFunctions.applyBackgroundColor;
import static com.example.gruppe3_movieapp.AppConstFunctions.dbRepo;
import static com.example.gruppe3_movieapp.AppConstFunctions.delete;
import static com.example.gruppe3_movieapp.AppConstFunctions.sp;

/**
 * @author Elena Ozsvald
 */
public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvTitle, tvDescription, tvRating, tvActor, tvDuration, tvGenre, tvYear, tvSeasons, tvErrorAPI ;
    ImageView ivCover;
    ImageButton ibtnFavorite, ibtnWatched, ibtnShare;
    ArrayList<MotionPicture> motionPictureList = new ArrayList<>();
    MotionPicture currentMotionPicture;
    MotionPictureRepo motionPictureRepo = new MotionPictureRepo();
    Group group;

    private Bitmap mBitmap;

    @Override
    protected void onResume() {
        super.onResume();
        //ScrollView als oberstes Element übergeben...
        applyBackgroundColor(this, this, R.id.scrollView);
    }
    /**
     * @author Mohamed Ali El-Maoula
     * @author Elena Ozsvald
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ivCover = findViewById(R.id.ivCover);

        tvTitle = findViewById(R.id.tvTitle);
        tvRating = findViewById(R.id.tvRating);
        tvDescription = findViewById(R.id.tvDescription);
        tvActor = findViewById(R.id.tvActors);
        tvGenre = findViewById(R.id.tvGenre);
        tvDuration = findViewById(R.id.tvDuration);
        tvYear = findViewById(R.id.tvYear);
        tvSeasons = findViewById(R.id.tvSeasons);

        ibtnFavorite = findViewById(R.id.ibtnFavorite);
        ibtnShare = findViewById(R.id.ibtnShare);
        ibtnWatched = findViewById(R.id.ibtnWatched);

        tvErrorAPI = findViewById(R.id. tvErrorAPI);
        group = findViewById(R.id.groupMovieData);
    }

    @SuppressLint({"StringFormatMatches", "StringFormatInvalid"})
    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String imdbId = intent.getStringExtra(Intent.EXTRA_TEXT);

        // Datenbankeintrag wird anhand der imdbId hergeholt
        motionPictureList = (ArrayList<MotionPicture>) dbRepo.getMotionPicture(imdbId);

        if (motionPictureList.size() == 0){
            // Ist der Film noch nicht in der Datenbank, deswegen kann der Film auf jedenfall
            // kein Favorit und auch nicht gesehen sein
            ibtnFavorite.setImageResource(R.drawable.ic_star_set_favorite);
            //ibtnWatched.setImageResource(R.drawable.ic_not_seen);
            getfilteredMotionPictureImdb(imdbId);

        }
        else {
            MotionPicture motionPictureDB = motionPictureList.get(0);
            currentMotionPicture = motionPictureDB;

            // Überprüft ob der Film in der Favoritenliste ist
            // Je nach dem wird der Button gesetzt
            if (currentMotionPicture.markedAsFavorite){
                ibtnFavorite.setImageResource(R.drawable.ic_star_favorite);
            }
            else {
                ibtnFavorite.setImageResource(R.drawable.ic_star_set_favorite);
            }

            if (currentMotionPicture.markedAsSeen){
                ibtnWatched.setImageResource(R.drawable.ic_watched);
            }
            else {
                ibtnWatched.setImageResource(R.drawable.ic_not_seen);
            }

            setFields();
        }

        ibtnFavorite.setOnClickListener(this);
        ibtnWatched.setOnClickListener(this);
        ibtnShare.setOnClickListener(this);

    }

    public void setFields(){
        // Felder werden aus der Datenbank / API gesetz
        if (!currentMotionPicture.cover.equals("N/A")){
            Picasso.get().load(currentMotionPicture.cover).into(ivCover);
        }
        else {
            // Falls die API kein Filmcover besitzt wird ein Standardbild gesetzt
            Picasso.get().load(R.drawable.nomoviepicture).into(ivCover);
        }

        tvTitle.setText(currentMotionPicture.title);

        // Falls kein Rating vorhanden wird ein anderer Text angezeigt
        Float rating = currentMotionPicture.imdbRating;
            if (rating != null){
                tvRating.setText(getString(R.string.tvMovieRating, currentMotionPicture.imdbRating));
            }
            else {
                tvRating.setText(getString(R.string.tvMovieRatingNull));
            }

        tvRating.setText(getString(R.string.tvMovieRating, currentMotionPicture.imdbRating));
        tvDescription.setText(currentMotionPicture.plot);
        //tvActor.setText(getString(R.string.tvActor, currentMotionPicture.actors));
        String actors = "<b>" + getString(R.string.tvActor) +"</b> " + currentMotionPicture.actors;
        tvActor.setText(Html.fromHtml(actors));
        tvDuration.setText(currentMotionPicture.runtime);
        tvGenre.setText(currentMotionPicture.genre);

        String year = currentMotionPicture.year;
        // Überprüft ob der das letzte Zeichen – ist,
        // wenn ja wird der – gelöscht und es steht dran die Serie gibt es seit xxxx
        String last = year.substring(year.length()-1);
        if (last.equals("–")){
            year = year.replace("–", "");
            year = getString(R.string.tvYearSince, year);
        }

        // Bei einer Abgeschlossenen Serie wird vor- und nachdem Bindestrich
        // ein Leerzeichen eingefügt
        year = year.replace("–", " - ");

        tvYear.setText(year);

        if (currentMotionPicture.type.equals("series")){
            tvSeasons.setVisibility(View.VISIBLE);
            tvSeasons.setText(getString(R.string.tvSeasons, currentMotionPicture.total_Season));
        }
        else{
            tvSeasons.setVisibility(View.INVISIBLE);
        }
    }

    public void setFavoriteMovie(){
        if (!currentMotionPicture.markedAsFavorite){
            currentMotionPicture.setMarkedAsFavorite(true);
            dbRepo.insert(currentMotionPicture);
            ibtnFavorite.setImageResource(R.drawable.ic_star_favorite);
        }
        else {
            currentMotionPicture.setMarkedAsFavorite(false);
            dbRepo.update(currentMotionPicture);
            ibtnFavorite.setImageResource(R.drawable.ic_star_set_favorite);
        }

        // Wenn der Film kein Favorite und nicht mehr als gesehen makiert ist
        // wird dieser aus der Datenbank gelöscht
        if(!currentMotionPicture.markedAsSeen && !currentMotionPicture.markedAsFavorite){
            dbRepo.delete(currentMotionPicture);

            // Wenn diese Variable true ist wird die MainActivity beim aufrufen aktualisiert
            // dadurch wird der gelöschte Film nicht erneut angezeigt
            delete = true;
        }
    }

    public void shareMovie(){
        // Das Cover aus ivMovieImage wird gesendet
        BitmapDrawable bitmapDrawable = ((BitmapDrawable) ivCover.getDrawable());
        Bitmap bitmap = bitmapDrawable.getBitmap();
        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, currentMotionPicture.title, "Cover from the Movie " + currentMotionPicture.title);
        Uri bitmapUri = Uri.parse(bitmapPath);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("image/jpeg");
        // Übergeben des Bildes an das share Intent
        shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        // Zusätzlich kommt noch der Titel des Filmes als Text hinzu
        if (currentMotionPicture.type.equals("movie")){
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.shareMovie, currentMotionPicture.title));
        }
        else {
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.shareSeries, currentMotionPicture.title));
        }
        startActivity(Intent.createChooser(shareIntent, "Share Movie"));
    }

    /**
     * @author Mohamed Ali El-Maoula
     */
    private void getfilteredMotionPictureImdb(String imdb){
        motionPictureRepo.filteredMotionPictureImdb(imdb, new Callback<MotionPicture>() {
            @Override
            public void onResponse(Call<MotionPicture> call, Response<MotionPicture> response) {
                if(response.isSuccessful()){
                    Log.d("DetailActivity", "getMotionPicture: onResponse successfull");
                    MotionPicture motionPictureResult = response.body();

                    if (motionPictureResult != null){

                         showData(motionPictureResult);
                    }
                    else {
                        tvErrorAPI.setText(R.string.outputApiObject);

                    }
                }
                else {
                    Log.d("DetailAcitivity", "getMotionPicture: onResponse NOT successfull");
                    showErrorMessage(getString(R.string.motionPicture_error_NOT_succsessfull));
                }

            }

            @Override
            public void onFailure(Call<MotionPicture> call, Throwable t) {
                Log.d("DetailActivity", "getMotionPicture: onFailure " + t.getMessage());
                showErrorMessage(getString(R.string.motionPicture_error_on_failure));

            }
        });

    }

    /**
     * @author Mohamed Ali El-Maoula
     */

    private void showData(MotionPicture motionPicture){
        group.setVisibility(View.VISIBLE);
        tvErrorAPI.setVisibility(View.INVISIBLE);
        // Daten aus der API werden an currentMotionPictuere übergeben
        currentMotionPicture = motionPicture;

        // Wenn die API kein Cover zurückgibt, wird das Standardbild geladen
        if (motionPicture.cover.equals("N/A")){
            Uri pathNoMovieImage = Uri.parse("android.resource://"+ R.class.getPackage().getName()+"/" + R.drawable.nomoviepicture);
            currentMotionPicture.cover = pathNoMovieImage.toString();
        }

        // Methode um die Daten in die Views zuschreiben wird aufgerufen
        setFields();
        tvErrorAPI.setVisibility(View.INVISIBLE);
    }

    /**
     * @author Mohamed Ali El-Maoula
     */
    private void showErrorMessage(String failure){
        tvErrorAPI.setVisibility(View.VISIBLE);
        tvErrorAPI.setText(failure);
        tvErrorAPI.setVisibility(View.INVISIBLE);
        group.setVisibility(View.INVISIBLE);
    }

    public void setWatched(){
        if (!currentMotionPicture.markedAsSeen){
            currentMotionPicture.setMarkedAsSeen(true);
            dbRepo.insert(currentMotionPicture);
            ibtnWatched.setImageResource(R.drawable.ic_watched);
        }
        else {
            currentMotionPicture.setMarkedAsSeen(false);
            dbRepo.update(currentMotionPicture);
            ibtnWatched.setImageResource(R.drawable.ic_not_seen);
        }

        // Wenn der Film kein Favorite und nicht mehr als gesehen makiert ist
        // wird dieser aus der Datenbank gelöscht
        if(!currentMotionPicture.markedAsSeen && !currentMotionPicture.markedAsFavorite){
            dbRepo.delete(currentMotionPicture);

            delete = true;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibtnFavorite:
                setFavoriteMovie();
                break;
            case R.id.ibtnShare:
                try{
                    shareMovie();
                }
                catch (Exception ex){
                    // Wenn auf dem Gerät keine Messenger App installiert wird eine Meldung ausgegeben
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, R.string.errorMessageShare, duration);
                    toast.show();
                }
                break;
            case R.id.ibtnWatched:
                setWatched();
        }
    }
}
