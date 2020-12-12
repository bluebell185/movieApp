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
    // Dieser Teil muss in das Fragment FavoritesFragment übernommen werden
//    ArrayList<MotionPicture> motionPictureList = new ArrayList<>();
//    TextView tvTitleMain;
//    TextView tvMain;
//    ImageView ivCoverMain;
//    Button btnAddMotionPictureMain;
//    static MotionPictureDao dbRepo;
//    static AppDatabase db;
//    MotionPictureAdapterMain pa;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        //Datenbank einmalig initialisieren...
//        db = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class, "MovieAppDB").
//                allowMainThreadQueries().
//                fallbackToDestructiveMigration().
//                build();
//        dbRepo = db.motionPictureDao();
//
//        tvTitleMain = findViewById(R.id.tvTitleMain);
//        tvMain = findViewById(R.id.tvMain);
//        ivCoverMain = findViewById(R.id.ivCoverMain);
//        btnAddMotionPictureMain = findViewById(R.id.btnAddMotionPictureMain);
//
//        fillMotionPictureList();    //hier später die favorisierten, aber noch nicht gesehenen Filme anzeigen als erste Sicht des Users
//    }
//
//    protected void onStart(){
//        super.onStart();
//
//        RecyclerView rvMain = findViewById(R.id.rvMain);
//
//        pa = new MotionPictureAdapterMain(motionPictureList);
//        rvMain.setLayoutManager(new GridLayoutManager(this, 3));
//        rvMain.setAdapter(pa);
//
//        if(motionPictureList.isEmpty()){
//            tvMain.setText(getString(R.string.tv_main_empty_rv));
//        }
//        else{
//            tvMain.setText(getString(R.string.tv_main_show_favorite));
//        }
//
//        rvMain.addOnItemTouchListener(
//                new RecyclerItemClickListener(this, rvMain, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        Intent iMovieDetailView = new Intent(getApplicationContext(), MovieDetailActivity.class);
//                        // getItem holt die imdbId her, diese wird an das Intent übergeben
//                        iMovieDetailView.putExtra(Intent.EXTRA_TEXT, pa.getItem(position));
//                        MainActivity.this.startActivity(iMovieDetailView);
//                    }
//
//                    @Override
//                    public void onLongItemClick(View view, int position) {
//                        // Kann man noch überlegen was sinnvolles einzufügen.
//                    }
//                })
//        );
//
//        btnAddMotionPictureMain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent changeActivity = new Intent(getApplicationContext(), SearchActivity.class);
//                MainActivity.this.startActivity(changeActivity);
//            }
//        });
//    }
//
//    private void fillMotionPictureList(){
//        motionPictureList.addAll((ArrayList<MotionPicture>) dbRepo.getAll().stream().filter(c -> c.isMarkedAsFavorite()).collect(Collectors.toList()));
//
//        //das hier löschen, wenn der Rest funktioniert:
//        MotionPicture m1 = new MotionPicture("Titel", "https://m.media-amazon.com/images/M/MV5BN2RhMTcxNDQtM2NiZC00OTc0LWFhNGMtNWI4YjMwOWRlOGZhXkEyXkFqcGdeQXVyNTM4NDU4NDA@._V1_SX300.jpg", "1", true, true, "movie");
//        MotionPicture m2 = new MotionPicture("Title", "https://m.media-amazon.com/images/M/MV5BMGJjMzViZjktYmE3NC00M2YwLTk2YWEtZWMzZWZmNGNhNTI1XkEyXkFqcGdeQXVyMjYwNDA2MDE@._V1_SX300.jpg", "2",true, false, "movie");
//        MotionPicture m3 = new MotionPicture("Titel", "https://m.media-amazon.com/images/M/MV5BYzlhMDg2YTItNmRjNS00MDdjLTlhMTItMWQ4M2FiMjgwYjM2XkEyXkFqcGdeQXVyMjYwNDA2MDE@._V1_SX300.jpg", "3",false, true, "movie");
//        MotionPicture m4 = new MotionPicture("Titel", "https://m.media-amazon.com/images/M/MV5BN2RhMTcxNDQtM2NiZC00OTc0LWFhNGMtNWI4YjMwOWRlOGZhXkEyXkFqcGdeQXVyNTM4NDU4NDA@._V1_SX300.jpg","4", true, false, "movie");
//        MotionPicture m5 = new MotionPicture("Title", "https://m.media-amazon.com/images/M/MV5BMGJjMzViZjktYmE3NC00M2YwLTk2YWEtZWMzZWZmNGNhNTI1XkEyXkFqcGdeQXVyMjYwNDA2MDE@._V1_SX300.jpg", "5", false, true, "movie");
//        MotionPicture m6 = new MotionPicture("Titel", "https://m.media-amazon.com/images/M/MV5BYzlhMDg2YTItNmRjNS00MDdjLTlhMTItMWQ4M2FiMjgwYjM2XkEyXkFqcGdeQXVyMjYwNDA2MDE@._V1_SX300.jpg", "6", true, true, "movie");
//        MotionPicture m7 = new MotionPicture("Titel", "https://m.media-amazon.com/images/M/MV5BN2RhMTcxNDQtM2NiZC00OTc0LWFhNGMtNWI4YjMwOWRlOGZhXkEyXkFqcGdeQXVyNTM4NDU4NDA@._V1_SX300.jpg", "7", false, true, "movie");
//        MotionPicture m8 = new MotionPicture("Title", "https://m.media-amazon.com/images/M/MV5BMGJjMzViZjktYmE3NC00M2YwLTk2YWEtZWMzZWZmNGNhNTI1XkEyXkFqcGdeQXVyMjYwNDA2MDE@._V1_SX300.jpg", "8", true, true, "movie");
//        MotionPicture m9 = new MotionPicture("Titel", "https://m.media-amazon.com/images/M/MV5BYzlhMDg2YTItNmRjNS00MDdjLTlhMTItMWQ4M2FiMjgwYjM2XkEyXkFqcGdeQXVyMjYwNDA2MDE@._V1_SX300.jpg", "9", true, false, "movie");
//        MotionPicture m10 = new MotionPicture("Warcraft: The Beginning", "https://m.media-amazon.com/images/M/MV5BMjIwNTM0Mzc5MV5BMl5BanBnXkFtZTgwMDk5NDU1ODE@._V1_SX300.jpg", "10", true, false, "movie");
//        MotionPicture m11 = new MotionPicture("NCIS", "https://m.media-amazon.com/images/M/MV5BYjVlMjZhYzYtOGQxNC00OTQxLTk2NzEtMWFmMmNhODA4YjYzXkEyXkFqcGdeQXVyNjQ3MDgwNjY@._V1_SX300.jpg", "11", true, false, "series");
//        MotionPicture m12 = new MotionPicture("The Lord of the Rings: The Return of the King", "https://m.media-amazon.com/images/M/MV5BNzA5ZDNlZWMtM2NhNS00NDJjLTk4NDItYTRmY2EwMWZlMTY3XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg", "12", true, false, "movie");
//
//        //START TEST
//        db.clearAllTables(); //Um Tabellen zu leeren, sonst gibts Fehler bei doppelter imdbId!
//        //Kurzer Test: Du hast 3 Objekte erstellt (m1,m2,m3). Diese in der DB speichern mit Insert,
//        dbRepo.insert(m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        motionPictureList.clear();
//        switch(item.getItemId()) {
//            case R.id.menu_item_show_all_main:{
//                // alle Filme/Serien anzeigen, die favorisiert und/oder angesehen wurden
//                tvMain.setText(getString(R.string.tv_main_show_all));
//                motionPictureList.addAll((ArrayList<MotionPicture>) dbRepo.getAll());
//                break;
//            }
//            case R.id.menu_item_favorite_main:{
//                // alle favorisierten Filme/Serien anzeigen, die noch nicht gesehen wurden
//                tvMain.setText(getString(R.string.tv_main_show_favorite));
//                motionPictureList.addAll((ArrayList<MotionPicture>) dbRepo.getAll().stream().filter(c -> c.isMarkedAsFavorite() &! c.isMarkedAsSeen()).collect(Collectors.toList()));
//                break;
//            }
//            case R.id.menu_item_watched_main:{
//                // alle angesehenen Filme/Serien anzeigen
//                tvMain.setText(getString(R.string.tv_main_show_seen));
//                motionPictureList.addAll((ArrayList<MotionPicture>) dbRepo.getAll().stream().filter(c -> c.isMarkedAsSeen()).collect(Collectors.toList()));
//                break;
//            }
//        }
//        pa.notifyDataSetChanged();
//        return true;
//    }

// Wenn das FavoritesFragement funktioniert diesen Teil einkommentieren
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