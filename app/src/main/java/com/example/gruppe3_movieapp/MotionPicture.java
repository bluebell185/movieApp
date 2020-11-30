package com.example.gruppe3_movieapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

//Table names in SQLite are case-insensitive!
@Entity
public class MotionPicture implements Parcelable {
    @PrimaryKey @NonNull
    String imdbId;
    //hier noch @SerializedName drüber und Attribute ergänzen @Mo
    String title;
    double duration;
    float rating;
    String cover;
    @Expose
    boolean markedAsFavorite = false;    //Nicht in JSON
    @Expose
    boolean markedAsSeen = false;       //Nicht in JSON

    @Ignore
    public MotionPicture(String title, String cover, String imdbId, boolean markedAsSeen, boolean markedAsFavorite) {
        //Für Testzwecke?
        this.title = title;
        this.cover = cover;
        this.imdbId = imdbId;
        this.markedAsSeen = markedAsSeen;
        this.markedAsFavorite = markedAsFavorite;
    }

    public MotionPicture(String imdbId, String title, double duration, float rating, String cover) {
        this.imdbId = imdbId;
        this.title = title;
        this.duration = duration;
        this.rating = rating;
        this.cover = cover;

    }

    @Ignore
    protected MotionPicture(Parcel in) {
        imdbId = in.readString();
        title = in.readString();
        duration = in.readDouble();
        rating = in.readFloat();
        cover = in.readString();
    }

    public static final Creator<MotionPicture> CREATOR = new Creator<MotionPicture>() {
        @Override
        public MotionPicture createFromParcel(Parcel in) {
            return new MotionPicture(in);
        }

        @Override
        public MotionPicture[] newArray(int size) {
            return new MotionPicture[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imdbId);
        dest.writeString(title);
        dest.writeDouble(duration);
        dest.writeDouble(rating);
        dest.writeString(cover);
    }

    public String getImdbId() { return imdbId; }

    public String getTitle() {
        return title;
    }

    public double getDuration() {
        return duration;
    }

    public float getRating() {
        return rating;
    }

    public String getCover() {
        return cover;
    }

    public boolean isMarkedAsFavorite() {
        return markedAsFavorite;
    }

    public void setMarkedAsFavorite(boolean markedAsFavorite) {
        this.markedAsFavorite = markedAsFavorite;
    }

    public boolean isMarkedAsSeen() {
        return markedAsSeen;
    }

    public void setMarkedAsSeen(boolean markedAsSeen) {
        this.markedAsSeen = markedAsSeen;
    }
}
