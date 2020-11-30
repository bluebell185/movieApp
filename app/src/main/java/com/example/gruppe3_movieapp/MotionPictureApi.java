package com.example.gruppe3_movieapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MotionPictureApi {



    @GET("api")
    Call<MotionPictureApiResults> randomMotionPicture();

    @GET("api") // Title
    Call<MotionPictureApiResults> filteredMotionPictureTitle(@Query("t") String title);

    @GET("api") // Imdb
    Call<MotionPictureApiResults> filteredMotionPictureImdb(@Query("i") String imdb);
}
