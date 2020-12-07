package com.example.gruppe3_movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static com.example.gruppe3_movieapp.MainActivity.dbRepo;

import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvTitle, tvDescription, tvRating;
    ImageView ivCover;
    ImageButton ibtnFavorite, ibtnWatched, ibtnShare;
    ArrayList<MotionPicture> motionPictureList = new ArrayList<>();

    boolean favorite = false;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ivCover = findViewById(R.id.ivCover);

        tvTitle = findViewById(R.id.tvTitle);
        tvRating = findViewById(R.id.tvRating);
        tvDescription = findViewById(R.id.tvDescription);

        ibtnFavorite = findViewById(R.id.ibtnFavorite);
        ibtnShare = findViewById(R.id.ibtnShare);
        ibtnWatched = findViewById(R.id.ibtnWatched);
    }

    @SuppressLint({"StringFormatMatches", "StringFormatInvalid"})
    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String imdbId = intent.getStringExtra(Intent.EXTRA_TEXT);

        // Datenbankeintrag wird anhand der imdbId hergeholt
        motionPictureList = (ArrayList<MotionPicture>) dbRepo.getMotionPicture(imdbId);



        // Felder werden aus der Datenbank / API gesetz
        Picasso.get().load(motionPictureList.get(0).cover).into(ivCover);

        tvTitle.setText(motionPictureList.get(0).title);
        if (motionPictureList.get(0).ratings != null){
            tvRating.setText(getString(R.string.tvMovieRating, motionPictureList.get(0).ratings));
        }
        else {
            tvRating.setText(getString(R.string.tvMovieRatingNull, motionPictureList.get(0).ratings));
        }

        tvDescription.setText("");

        // Überprüft ob der Film in der Favoritenliste ist
        // Je nach dem wird der Button gesetzt
        if (!favorite){
            ibtnFavorite.setImageResource(R.drawable.ic_star_set_favorite);
        }
        else {
            ibtnFavorite.setImageResource(R.drawable.ic_star_favorite);
        }

        ibtnFavorite.setOnClickListener(this);
        ibtnWatched.setOnClickListener(this);
        ibtnShare.setOnClickListener(this);

    }

    public void setFavoriteMovie(){
        favorite = true;
        ibtnFavorite.setImageResource(R.drawable.ic_star_favorite);
    }

    public void shareMovie(){
        // Das Cover aus ivMovieImage wird gesendet
        BitmapDrawable bitmapDrawable = ((BitmapDrawable) ivCover.getDrawable());
        Bitmap bitmap = bitmapDrawable.getBitmap();
        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, motionPictureList.get(0).title, "Cover from the Movie " + motionPictureList.get(0).title);
        Uri bitmapUri = Uri.parse(bitmapPath);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("image/jpeg");
        // Übergeben des Bildes an das share Intent
        shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        // Zusätzlich kommt noch der Titel des Filmes als Text hinzu
        if (motionPictureList.get(0).type == "movie"){
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.shareMovie, motionPictureList.get(0).title));
        }
        else {
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.shareSeries, motionPictureList.get(0).title));
        }
        startActivity(Intent.createChooser(shareIntent, "Share Movie"));
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
        }
    }
}
