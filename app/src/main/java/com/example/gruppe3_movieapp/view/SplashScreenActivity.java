package com.example.gruppe3_movieapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gruppe3_movieapp.R;

/**
 * @author Mustafa Soput
 */
public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Code von https://www.codeseasy.com/android-splash-screen-with-android-studio/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class); startActivity(i);
            finish(); }, 2000);
    }
}
