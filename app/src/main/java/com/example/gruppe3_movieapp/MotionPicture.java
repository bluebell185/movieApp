package com.example.gruppe3_movieapp;

import android.os.Parcel;
import android.os.Parcelable;

public class MotionPicture implements Parcelable {
    //hier noch @SerializedName drüber und Attribute ergänzen @Mo
    String title;
    double duration;
    float rating;
    String cover;

    public MotionPicture(String title, String cover) {
        this.title = title;
        this.cover = cover;
    }

    public MotionPicture(String title, double duration, float rating, String cover) {
        this.title = title;
        this.duration = duration;
        this.rating = rating;
        this.cover = cover;
    }

    protected MotionPicture(Parcel in) {
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
        dest.writeString(title);
        dest.writeDouble(duration);
        dest.writeDouble(rating);
        dest.writeString(cover);
    }

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
}
