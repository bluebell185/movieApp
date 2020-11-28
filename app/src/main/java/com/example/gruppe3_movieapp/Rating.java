package com.example.gruppe3_movieapp;

import com.google.gson.annotations.SerializedName;

public class Rating {
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
    // double? wegen "/" string gewählt!

}
