package com.example.gruppe3_movieapp.api;

/*import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
*/

import com.example.gruppe3_movieapp.model.MotionPicture;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Mohamed Ali El-Maoula
 */

public class MotionPictureRepo {
    MotionPictureApi motionPictureApi;

    public MotionPictureRepo(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        motionPictureApi = retrofit.create(MotionPictureApi.class);
    }

    // Show filtered MotionPicture - Title
    public void getFilteredMotionPictureTitle( String title, Callback<MotionPictureApiResults> callback){
        Call<MotionPictureApiResults> motionPictureApiResultsCall = motionPictureApi.filteredMotionPictureTitle(title);
        motionPictureApiResultsCall.enqueue(callback);
    }

    // Show filtered MotionPicture - ImdB
    public void filteredMotionPictureImdb( String imdb, Callback<MotionPicture> callback){
        Call<MotionPicture> motionPictureApiResultsCall = motionPictureApi.filteredMotionPictureImdb(imdb);
        motionPictureApiResultsCall.enqueue(callback);
    }




}
