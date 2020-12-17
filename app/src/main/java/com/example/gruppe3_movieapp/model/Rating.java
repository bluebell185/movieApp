package com.example.gruppe3_movieapp.model;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Mohamed Ali El-Maoula
 */
//@Entity
public class Rating {
    //@Expose
    //@PrimaryKey(autoGenerate = true)
    //int id;
    @SerializedName("Source")
    String source;
    @SerializedName("Value")
    String value;
    public Rating(String source, String value) {
        this.source = source;
        this.value = value;
    }
    public String getSource() {
        return source;
    }
    public String getValue() {
        return value;
    }
}