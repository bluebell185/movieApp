package com.example.gruppe3_movieapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MotionPictureApi {



    @GET("api")
    Call<MotionPictureApiResults> randomMotionPicture();

    @GET("?apikey=dd6c1031") // Title
    Call<MotionPictureApiResults> filteredMotionPictureTitle(@Query("s") String title);  // @Query("apikey") String apikey,

    @GET("api") // Imdb
    Call<MotionPictureApiResults> filteredMotionPictureImdb(@Query("i") String imdb);
}
