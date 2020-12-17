package com.example.gruppe3_movieapp.api;

import com.example.gruppe3_movieapp.model.MotionPicture;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Mohamed Ali El-Maoula
 */

public interface MotionPictureApi {

    @GET("?apikey=dd6c1031") // Title
    Call<MotionPictureApiResults> filteredMotionPictureTitle(@Query("s") String title);  // @Query("apikey") String apikey,

    @GET("?apikey=dd6c1031&plot=full") // Imdb
    Call<MotionPicture> filteredMotionPictureImdb(@Query("i") String imdb);
}
