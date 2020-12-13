package com.example.gruppe3_movieapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author Mohamed Ali El-Maoula
 */

public class MotionPictureApiResults {
    @SerializedName("Search")
    ArrayList<MotionPicture> motionPicture;

    public ArrayList<MotionPicture> getMotionPicture(){
        return motionPicture;
    }

    public MotionPictureApiResults(ArrayList<MotionPicture> motionPicture) {
        this.motionPicture = motionPicture;
    }
}
