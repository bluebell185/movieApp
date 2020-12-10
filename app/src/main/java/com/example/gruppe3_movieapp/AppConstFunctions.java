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

public class AppConstFunctions {
    static MotionPictureDao dbRepo;
    static AppDatabase db;
    static SharedPreferences sp;
    static final String APP_PREFERENCES = "appPreferences";
    static final String PREF_COLOR_LIGHT = "colorLight";
    static final String PREF_COLOR_DARK = "colorDark";
    static final String PREF_LAST_SEARCH_EXPRESSION = "lastSearchExpression";
    static String currentColorPreference;


    static BiMap<Integer, Integer> mapColorToMenuItem;
    static {
        //Bidirektionale Map um ColorID zu ItemID aufzulösen und rückwärts
        mapColorToMenuItem = HashBiMap.create();


        //Lightmode
        mapColorToMenuItem.put(R.color.colorWhite, R.id.item_default_light);
        mapColorToMenuItem.put(R.color.colorRed, R.id.item_red);
        mapColorToMenuItem.put(R.color.colorBlue, R.id.item_blue);
        mapColorToMenuItem.put(R.color.colorPurple, R.id.item_purple);
        //Darkmode
        mapColorToMenuItem.put(R.color.colorDarkGrey, R.id.item_default_dark);
        mapColorToMenuItem.put(R.color.colorBlack, R.id.item_black);
        mapColorToMenuItem.put(R.color.colorGrey, R.id.item_grey);


    }

    static boolean isDarkmodeActive(Context context) {
        //Code (angepasst) aus https://stackoverflow.com/questions/44170028/android-how-to-detect-if-night-mode-is-on-when-using-appcompatdelegate-mode-ni
        int nightModeFlags =
                context.getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            //Obsolet
            //case Configuration.UI_MODE_NIGHT_YES:
            //    return true;

            case Configuration.UI_MODE_NIGHT_NO | Configuration.UI_MODE_NIGHT_UNDEFINED:
                return false;
            default:
                return true;
        }
    }

    /**
     Reads the current color preference from Shared Preferences (depending on rather NightMode active or not), and applies it to the the given Layout of the activity
     @param layoutViewId to which the color should be applied to
     */
    public static void applyBackgroundColor(Activity activity, Context context, int layoutViewId) {
        if (isDarkmodeActive(context)) {
            currentColorPreference = PREF_COLOR_DARK;
        }
        else {
            currentColorPreference = PREF_COLOR_LIGHT;
        }
        //Wenn SharedPref leer, wird Default Farbe für Light- bzw. Nightmode genommen
        activity.findViewById(layoutViewId).setBackgroundColor(ContextCompat.getColor(context, sp.getInt(currentColorPreference, isDarkmodeActive(context) ? R.color.colorPrimaryDark : R.color.colorPrimary)));
    }
}
