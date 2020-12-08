package com.example.gruppe3_movieapp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.gruppe3_movieapp.MotionPicture;

@Database(entities = MotionPicture.class, version = 6)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract MotionPictureDao motionPictureDao();
    //Bei Migration Errors: https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
}
