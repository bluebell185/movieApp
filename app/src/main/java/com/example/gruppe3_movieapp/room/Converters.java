package com.example.gruppe3_movieapp.room;

import android.text.TextUtils;

import androidx.room.TypeConverter;

import com.example.gruppe3_movieapp.Rating;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mustafa
 */
public class Converters {
    final static String LINE_DELIMITER = "\r\n";
    final static String VALUE_DELIMITER = ";";
    @TypeConverter
    public static List<Rating> ratingsFromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        else {
            List<String> preResult = new ArrayList<String>();
            List<Rating> result = new ArrayList<Rating>();

            preResult = Arrays.asList(value.split(LINE_DELIMITER));
            for (String string : preResult) {
                String[] values = string.split(VALUE_DELIMITER);
                result.add(new Rating(values[0], values[1]));
            }
            return result;
        }
    }

    @TypeConverter
    public static String stringFromRatings(List<Rating> ratings) {
        if (ratings == null) {
            return null;
        }
        else {
            List<String> preResult = new ArrayList<String>();
            //Idee: Aus Rating Objekten eine plain-text CSV machen:
            //bspw. R1 mit IMDB und 10/10, R2 mit IMDB und 7/10 wird zu:
            //IMDB;10/10
            //IMDB;7/10
            for (Rating rating : ratings) {
                preResult.add(rating.getSource() + VALUE_DELIMITER + rating.getValue());
            }
            return TextUtils.join(LINE_DELIMITER, preResult);
        }
    }

}
