package com.example.gruppe3_movieapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gruppe3_movieapp.room.AppDatabase;
import com.example.gruppe3_movieapp.room.MotionPictureDao;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
/**
 * @author Elena Ozsvald
 */
public class FavoritesFragment extends Fragment {
    ArrayList<MotionPicture> motionPictureList = new ArrayList<>();
    TextView tvTitleMain, tvMain, tvWelcome;
    ImageView ivCoverMain;
    Button btnShowAll, btnShowFavorites, btnShowSeen;
    static MotionPictureDao dbRepo;
    static AppDatabase db;
    MotionPictureAdapterMain pa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * @author Kathrin Ulmer
     * @author Elena Oszvald
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        //Datenbank einmalig initialisieren...
        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                AppDatabase.class, "MovieAppDB").
                allowMainThreadQueries().
                fallbackToDestructiveMigration().
                build();
        dbRepo = db.motionPictureDao();

        tvTitleMain = view.findViewById(R.id.tvTitleMain);
        tvMain = view.findViewById(R.id.tvMain);
        tvWelcome = view.findViewById(R.id.tvWelcome);
        ivCoverMain = view.findViewById(R.id.ivCoverMain);
        btnShowAll = view.findViewById(R.id.btnShowAll);
        btnShowFavorites = view.findViewById(R.id.btnShowFavorites);;
        btnShowSeen = view.findViewById(R.id.btnShowSeen);;

        fillMotionPictureList();

        RecyclerView rvMain = view.findViewById(R.id.rvMain);

        pa = new MotionPictureAdapterMain(motionPictureList);
        rvMain.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
        rvMain.setAdapter(pa);

        if(motionPictureList.isEmpty()){
            tvMain.setText(getString(R.string.tv_main_empty_rv_welcome));
            tvWelcome.setVisibility(View.VISIBLE);
            tvWelcome.setText(getString(R.string.tv_main_empty_rv));
        }
        else{
            tvMain.setText(getString(R.string.tv_main_show_favorite));
            tvWelcome.setVisibility(View.INVISIBLE);
        }

        rvMain.addOnItemTouchListener(
                new RecyclerItemClickListener(view.getContext(), rvMain, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent iMovieDetailView = new Intent(getActivity().getApplicationContext(), MovieDetailActivity.class);
                        // getItem holt die imdbId her, diese wird an das Intent übergeben
                        iMovieDetailView.putExtra(Intent.EXTRA_TEXT, pa.getItem(position));
                        startActivity(iMovieDetailView);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // Kann man noch überlegen was sinnvolles einzufügen.
                    }
                })
        );

        /**
         * @author Kathrin Ulmer
         */
        btnShowAll.setOnClickListener(v -> {
            // alle Filme/Serien anzeigen, die favorisiert und/oder angesehen wurden
            motionPictureList.clear();
            tvMain.setText(getString(R.string.tv_main_show_all));
            motionPictureList.addAll(dbRepo.getAll());
            pa.notifyDataSetChanged();
            setButtonSelected(true, false, false);
        });
        btnShowAll.setOnLongClickListener(v -> {
            makeToast(getText(R.string.menu_item_show_all_main).toString());
            return true;
        });

        btnShowFavorites.setOnClickListener((View.OnClickListener) v -> {
            // alle favorisierten Filme/Serien anzeigen, die noch nicht gesehen wurden
            motionPictureList.clear();
            tvMain.setText(getString(R.string.tv_main_show_favorite));
            motionPictureList.addAll(dbRepo.getAll().stream().filter(c -> !c.isMarkedAsSeen() && c.isMarkedAsFavorite()).collect(Collectors.toList()));
            pa.notifyDataSetChanged();
            setButtonSelected(false, false, true);
        });
        btnShowFavorites.setOnLongClickListener(v -> {
            makeToast(getText(R.string.menu_item_favorite_main).toString());
            return true;
        });

        btnShowSeen.setOnClickListener((View.OnClickListener) v -> {
            // alle angesehenen Filme/Serien anzeigen
            motionPictureList.clear();
            tvMain.setText(getString(R.string.tv_main_show_seen));
            motionPictureList.addAll(dbRepo.getAll().stream().filter(c -> c.isMarkedAsSeen()).collect(Collectors.toList()));
            pa.notifyDataSetChanged();
            setButtonSelected(false, true, false);
        });
        btnShowSeen.setOnLongClickListener(v -> {
            makeToast(getText(R.string.menu_item_watched_main).toString());
            return true;
        });


        return view;
    }

    /**
     * @author Kathrin Ulmer
     */
    private void fillMotionPictureList(){
        motionPictureList.clear();
        motionPictureList.addAll(dbRepo.getAll().stream().filter(c -> !c.isMarkedAsSeen() && c.isMarkedAsFavorite()).collect(Collectors.toList()));
        btnShowFavorites.setSelected(true);
    }

    /**
     * @author Kathrin Ulmer
     */
    private void makeToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * @author Kathrin Ulmer
     */
    private void setButtonSelected(boolean all, boolean seen, boolean favorites){
        btnShowAll.setSelected(all);
        btnShowSeen.setSelected(seen);
        btnShowFavorites.setSelected(favorites);
    }
}