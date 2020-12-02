package com.example.gruppe3_movieapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.annotation.ElementType;
import java.util.List;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;

//Table names in SQLite are case-insensitive!
@Entity
public class MotionPicture implements Parcelable {
    @PrimaryKey @NonNull @SerializedName("imdbID")
    String imdbId;
    @SerializedName("Title")
    String title;
    @SerializedName("Runtime")
    double runtime;
    @SerializedName("Ratings")
    List<Rating> ratings;
    @SerializedName("Poster")
    String cover;
    @SerializedName("Year")
    double year;
    @SerializedName("Rated")
    String rated;
    @SerializedName("Released")
    String released;
    @SerializedName("Genre")
    String genre;
    @SerializedName("Director")
    String director;
    @SerializedName("Actors")
    String actors;
    @SerializedName("Plot")
    String plot;
    @SerializedName("Language")
    String language;
    @SerializedName("Country")
    String country;
    @SerializedName("Awards")
    String awards;
    @SerializedName("imdbRating")
    float imdbRating;
    @SerializedName("imdbVotes")
    String imdbVotes;

    @SerializedName("Type")
    String type;
    @SerializedName("totalSeasons")
    int total_Season;
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

    @Ignore
    public MotionPicture(String imdbId, String title, double duration, float imdbRating, String cover) {
        this.imdbId = imdbId;
        this.title = title;
        this.runtime = runtime;
        this.cover = cover;
        this.imdbRating = imdbRating;
        this.ratings = new ArrayList<>();
    }

    public MotionPicture(@NonNull String imdbId, String title, double runtime, List<Rating> ratings, String cover, double year, String rated, String released, String genre, String director, String actors, String plot, String language, String country, String awards, String type, int total_Season, float imdbRating, String imdbVotes) {
        this.imdbId = imdbId;
        this.title = title;
        this.runtime = runtime;
        this.ratings = ratings;
        this.cover = cover;
        this.year = year;
        this.rated = rated;
        this.released = released;
        this.genre = genre;
        this.director = director;
        this.actors = actors;
        this.plot = plot;
        this.language = language;
        this.country = country;
        this.awards = awards;
        this.type = type;
        this.total_Season = total_Season;
        this.imdbRating = imdbRating;
        this.imdbVotes = imdbVotes;
    }

    @Ignore
    protected MotionPicture(Parcel in) {
        imdbId = in.readString();
        title = in.readString();
        runtime = in.readDouble();
        in.readList(ratings, ElementType.class.getClassLoader());
        cover = in.readString();
        year = in.readDouble();
        rated = in.readString();
        released = in.readString();
        genre = in.readString();
        director = in.readString();
        actors = in.readString();
        plot = in.readString();
        language = in.readString();
        country = in.readString();
        awards = in.readString();
        type = in.readString();
        total_Season = in.readInt();
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
        dest.writeDouble(runtime);
        dest.writeList(ratings);
        dest.writeString(cover);
        dest.writeDouble(year);
        dest.writeString(rated);
        dest.writeString(released);
        dest.writeString(genre);
        dest.writeString(director);
        dest.writeString(actors);
        dest.writeString(plot);
        dest.writeString(language);
        dest.writeString(country);
        dest.writeString(awards);
        dest.writeString(type);
        dest.writeInt(total_Season);
    }

    @NonNull
    public String getImdbId() {
        return imdbId;
    }

    public String getTitle() {
        return title;
    }

    public double getRuntime() {
        return runtime;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public String getCover() {
        return cover;
    }

    public double getYear() {
        return year;
    }

    public String getRated() {
        return rated;
    }

    public String getReleased() {
        return released;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getAwards() {
        return awards;
    }

    public String getType() {
        return type;
    }

    public int getTotal_Season() {
        return total_Season;
    }

    public boolean isMarkedAsFavorite() {
        return markedAsFavorite;
    }

    public boolean isMarkedAsSeen() {
        return markedAsSeen;
    }

    public void setMarkedAsFavorite(boolean markedAsFavorite) {
        this.markedAsFavorite = markedAsFavorite;
    }



    public void setMarkedAsSeen(boolean markedAsSeen) {
        this.markedAsSeen = markedAsSeen;
    }

    public float getImdbRating() {
        return imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }
}
