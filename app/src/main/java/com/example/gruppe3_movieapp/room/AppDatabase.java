package com.example.gruppe3_movieapp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.gruppe3_movieapp.MotionPicture;

@Database(entities = MotionPicture.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MotionPictureDao motionPictureDao();
}
