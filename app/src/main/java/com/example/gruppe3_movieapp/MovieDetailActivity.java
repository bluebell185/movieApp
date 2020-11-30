package com.example.gruppe3_movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import com.squareup.picasso.Picasso;

import static com.example.gruppe3_movieapp.MainActivity.dbRepo;

import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvMovieTitle, tvMovieDescription, tvMovieRating;
    ImageView ivMovieImage;
    ImageButton ibtnFavorite, ibtnWatched, ibtnShare;
    ArrayList<MotionPicture> motionPictureList = new ArrayList<>();

    boolean favorite = false;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
    }

    @SuppressLint("StringFormatMatches")
    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String imdbId = intent.getStringExtra(Intent.EXTRA_TEXT);

        // Datenbankeintrag wird anhand der imdbId hergeholt
        motionPictureList = (ArrayList<MotionPicture>) dbRepo.getMotionPicture(imdbId);

        ivCover = findViewById(R.id.ivCover);

        tvMovieTitle = findViewById(R.id.tvMovieTitle);
        tvMovieRating = findViewById(R.id.tvMovieRating);
        tvMovieDescription = findViewById(R.id.tvMovieDescription);

        ibtnFavorite = findViewById(R.id.ibtnFavorite);
        ibtnShare = findViewById(R.id.ibtnShare);
        ibtnWatched = findViewById(R.id.ibtnWatched);

        // Felder werden aus der Datenbank / API gesetz
        Picasso.get().load(motionPictureList.get(0).cover).into(ivMovieImage);

        tvMovieTitle.setText(motionPictureList.get(0).title);
        tvMovieRating.setText(getString(R.string.tvMovieRating, motionPictureList.get(0).rating));
        tvMovieDescription.setText("");

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
        BitmapDrawable bitmapDrawable = ((BitmapDrawable) ivMovieImage.getDrawable());
        Bitmap bitmap = bitmapDrawable.getBitmap();
        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, motionPictureList.get(0).title, "Cover from the Movie " + motionPictureList.get(0).title);
        Uri bitmapUri = Uri.parse(bitmapPath);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("image/jpeg");
        // Übergeben des Bildes an das share Intent
        shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        // Zusätzlich kommt noch der Titel des Filmes als Text hinzu
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.shareMovie, motionPictureList.get(0).title));
        startActivity(Intent.createChooser(shareIntent, "Share Movie"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibtnFavorite:
                setFavoriteMovie();
                break;
            case R.id.ibtnShare:
                shareMovie();
                break;
        }
    }
}