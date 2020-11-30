package com.example.gruppe3_movieapp;

import java.util.ArrayList;

public class MotionPictureApiResults {
    ArrayList<MotionPicture> motionPicture;

    public ArrayList<MotionPicture> getMotionPicture(){
        return motionPicture;
    }

    public MotionPictureApiResults(ArrayList<MotionPicture> motionPicture) {
        this.motionPicture = motionPicture;
    }
}
