package com.example.gruppe3_movieapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.core.content.ContextCompat;

import com.example.gruppe3_movieapp.room.AppDatabase;
import com.example.gruppe3_movieapp.room.MotionPictureDao;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * @author Mustafa
 */
public class AppConstFunctions {
    static MotionPictureDao dbRepo;
    static AppDatabase db;
    static SharedPreferences sp;
    static final String APP_PREFERENCES = "appPreferences";
    static final String PREF_COLOR_LIGHT = "colorLight";
    static final int DEFAULT_COLOR_LIGHT = R.color.colorWhite;
    static final int DEFAULT_COLOR_DARK = R.color.colorDarkGrey;
    static final String PREF_COLOR_DARK = "colorDark";
    static final String PREF_LAST_SEARCH_EXPRESSION = "lastSearchExpression";
    static String currentColorPreference;
    static boolean delete = false;


    static BiMap<Integer, Integer> mapColorToMenuItem;
    static {
        //Bidirektionale Map um ColorID zu ItemID aufzulösen und vice versa
        mapColorToMenuItem = HashBiMap.create();


        //Lightmode
        mapColorToMenuItem.put(R.color.colorWhite, R.id.item_default_light);
        mapColorToMenuItem.put(R.color.colorBeige, R.id.item_beige);
        mapColorToMenuItem.put(R.color.colorBlue, R.id.item_blue);
        mapColorToMenuItem.put(R.color.colorCyan, R.id.item_cyan);
        mapColorToMenuItem.put(R.color.colorLightGrey, R.id.item_light_grey);
        //Darkmode
        mapColorToMenuItem.put(R.color.colorDarkGrey, R.id.item_default_dark);
        mapColorToMenuItem.put(R.color.colorBlack, R.id.item_black);
        mapColorToMenuItem.put(R.color.colorGrey, R.id.item_grey);
        mapColorToMenuItem.put(R.color.colorDarkCyan, R.id.item_dark_cyan);
        mapColorToMenuItem.put(R.color.colorDarkBlue, R.id.item_dark_blue);

    }

    static boolean isDarkmodeActive(Context context) {
        //Code (angepasst) aus https://stackoverflow.com/questions/44170028/android-how-to-detect-if-night-mode-is-on-when-using-appcompatdelegate-mode-ni
        int nightModeFlags =
                context.getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags != Configuration.UI_MODE_NIGHT_NO &&
                nightModeFlags != Configuration.UI_MODE_NIGHT_UNDEFINED;
    }

    /**
     Reads the current color preference from Shared Preferences (depending on rather NightMode active or not), and applies it to the the given Layout of the activity
     @param layoutViewId to which the color should be applied to (shall be a Top-layer view!)
     */
    public static void applyBackgroundColor(Activity activity, Context context, int layoutViewId) {
        if (isDarkmodeActive(context)) {
            currentColorPreference = PREF_COLOR_DARK;
        }
        else {
            currentColorPreference = PREF_COLOR_LIGHT;
        }
        //Wenn SharedPref leer, wird Default Farbe für Light- bzw. Nightmode genommen
        activity.findViewById(layoutViewId).setBackgroundColor(
                ContextCompat.getColor(
                        context, sp.getInt(
                                currentColorPreference,
                                isDarkmodeActive(context) ? DEFAULT_COLOR_DARK : DEFAULT_COLOR_LIGHT)
                )
        );
    }
}
