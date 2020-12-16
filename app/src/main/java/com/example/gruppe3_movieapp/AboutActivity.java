package com.example.gruppe3_movieapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.gruppe3_movieapp.AppConstFunctions.applyBackgroundColor;

public class AboutActivity  extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        //ScrollView als oberstes Element übergeben...
        applyBackgroundColor(this, this, R.id.constrLayoutAbout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

}
