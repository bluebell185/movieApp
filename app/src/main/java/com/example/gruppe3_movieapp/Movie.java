package com.example.gruppe3_movieapp;

import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("Title")
    String title;
    @SerializedName("Year")
    double year;
    @SerializedName("Rated")
    String rated;
    @SerializedName("Released")
    String released;
    @SerializedName("Runtime")
    String runtime;
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
    @SerializedName("Poster")
    String poster;
    @SerializedName("Raitings")
    Rating raiting;
    @SerializedName("imdbID")
    String imdbID;
    @SerializedName("Type")
    String type;
    @SerializedName("total Seasions")
    int total_Season;

    public Movie(String title, double year, String rated, String released, String runtime, String genre, String director, String actors, String plot, String language, String country, String awards, String poster, Rating raiting, String imdbID, String type, int total_Season) {
        this.title = title;
        this.year = year;
        this.rated = rated;
        this.released = released;
        this.runtime = runtime;
        this.genre = genre;
        this.director = director;
        this.actors = actors;
        this.plot = plot;
        this.language = language;
        this.country = country;
        this.awards = awards;
        this.poster = poster;
        this.raiting = raiting;
        this.imdbID = imdbID;
        this.type = type;
        this.total_Season = total_Season;
    }

    public String getTitle() {
        return title;
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

    public String getRuntime() {
        return runtime;
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

    public String getPoster() {
        return poster;
    }

    public Rating getRaiting() {
        return raiting;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getType() {
        return type;
    }

    public int getTotal_Season() {
        return total_Season;
    }



}
