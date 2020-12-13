package com.example.gruppe3_movieapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.gruppe3_movieapp.room.AppDatabase;
import com.example.gruppe3_movieapp.room.MotionPictureDao;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import static com.example.gruppe3_movieapp.AppConstFunctions.*;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tabFavorites, tabSearch;
    public PagerAdapter pagerAdapter;

    SubMenu colorPickItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Datenbank einmalig initialisieren...
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "MovieAppDB").
                allowMainThreadQueries().
                fallbackToDestructiveMigration().
                build();
        dbRepo = db.motionPictureDao();

        sp  = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        tabLayout = findViewById(R.id.tabLayout);
        tabFavorites = findViewById(R.id.tabFavorites);
        tabSearch = findViewById(R.id.tabSearch);
        viewPager = findViewById(R.id.viewPager);

        pagerAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0){
                    pagerAdapter.notifyDataSetChanged();
                }
                else if (tab.getPosition() == 1){
                    pagerAdapter.notifyDataSetChanged();
                }
                else if (tab.getPosition() == 2);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    protected void onResume() {
        super.onResume();
        applyBackgroundColor(this, this, R.id.linLayout);
    }

    /**
     * @author Mustafa
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem colorPickerMenuItem = menu.findItem(R.id.menu_item_change_color);
        getMenuInflater().inflate(R.menu.color_picker, colorPickerMenuItem.getSubMenu());
        colorPickItems = colorPickerMenuItem.getSubMenu();
        return true;
    }

    /**
     * @author Mustafa
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        Integer colorItemIdentifier;
        Integer preferredColor;
        MenuItem menuItem;
        //Je nach Setting werden nur bestimmte Farben angeboten...
        if (isDarkmodeActive(this)) {
            menu.setGroupVisible(R.id.group_light, false);
            currentColorPreference = PREF_COLOR_DARK;
        }
        else {
            menu.setGroupVisible(R.id.group_dark, false);
            currentColorPreference = PREF_COLOR_LIGHT;
        }

        /**
         * Vorgehen:
         * Es wird zunächst die gesetzte Farbe aus den SP ermittelt.
         * Dann wird diese Farbe zu einem Identifier eines MenuItems aufgelöst (HashMap).
         * Anschließend wird der Identifier als MenuItem gefunden und gechecked.
         * Wenn die Farbe aus den SP unbekannt, wird das Default des aktuellen Modes genommen (DEFAULT_COLOR...)
         */
        preferredColor = sp.getInt(currentColorPreference, 0);
        colorItemIdentifier = mapColorToMenuItem.get(preferredColor);
        if (colorItemIdentifier == null) {
            preferredColor = isDarkmodeActive(this) ? DEFAULT_COLOR_DARK : DEFAULT_COLOR_LIGHT;
        }
        menuItem = menu.findItem(mapColorToMenuItem.get(preferredColor));
        menuItem.setChecked(true);

        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Sonderfall für alle Items des Submenus der Farbauswahl
        if (colorPickItems.findItem(item.getItemId())!= null) {
            SharedPreferences.Editor spe = sp.edit();
            spe.putInt(currentColorPreference, mapColorToMenuItem.inverse().get(item.getItemId()));
            spe.apply();
            applyBackgroundColor(this, this, R.id.linLayout);
        }
        else {
            switch (item.getItemId()) {
                default:
                    break;
            }
        }
        return true;
    }
}