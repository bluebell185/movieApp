package com.example.gruppe3_movieapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.widget.SearchView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    ArrayList<MotionPicture> motionPictureList = new ArrayList<>();
    TextView tvTitleSearch;
    TextView tvYearSearch;
    ImageView ivTypeSearch;
    ImageView ivCoverSearch;
    MotionPictureAdapterSearch pa;
    MotionPictureRepo motionPictureRepo = new MotionPictureRepo();
    SharedPreferences sp;
    String lastSearchExpression = "";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        tvTitleSearch = view.findViewById(R.id.tvTitleSearch);
        tvYearSearch = view.findViewById(R.id.tvYearSearch);
        ivTypeSearch = view.findViewById(R.id.ivTypeSearch);
        ivCoverSearch = view.findViewById(R.id.ivCoverSearch);

        sp  = getActivity().getPreferences(Context.MODE_PRIVATE);
        lastSearchExpression = sp.getString("lastSearchExpression", "welcome");

        getfilteredMotionPictureTitle(lastSearchExpression);

        RecyclerView rvSearch = view.findViewById(R.id.rvSearch);

        pa = new MotionPictureAdapterSearch(motionPictureList);
        rvSearch.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));
        rvSearch.setAdapter(pa);

        rvSearch.addOnItemTouchListener(
                new RecyclerItemClickListener(view.getContext(), rvSearch, new RecyclerItemClickListener.OnItemClickListener() {
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

        return view;
    }

    // Sobald die Daten und Inhalte fest sind, muss noch festegelegt werden wann was angezeigt wird und welche Errormessage angezeigt werden kann
    private void getfilteredMotionPictureTitle(String title){
        motionPictureRepo.getFilteredMotionPictureTitle( title, new Callback<MotionPictureApiResults>() {
            @Override
            public void onResponse(Call<MotionPictureApiResults> call, Response<MotionPictureApiResults> response) {
                if (response.isSuccessful()){
                    Log.d("MainActivity", "getMotionPicture: onResponse successfull");

                    MotionPictureApiResults motionPictureApiResults = response.body();
                    if (motionPictureApiResults.getMotionPicture() != null) {
                        motionPictureList.addAll(motionPictureApiResults.getMotionPicture());
                        pa.notifyDataSetChanged();
                    } else {
                        //Fehlermeldung einbauen wie "Keine Filme gefunden, suche nach einem anderen Titel" oder so
                        // wenn das Programm hierher kommt, ist die motionPictureList leer
                    }

                  /*  MotionPicture motionPicture = motionPictureApiResults.getMotionPicture();
                    if (motionPicture != null){

                        // Daten ausgeben!
                    }
                    else {
                        // textview.setText(R.string.ErrorMessage);
                    } */
                }
                else {
                    Log.d("MainActivity", "getMotionPicture: onResponse NOT successfull");
                    // textview.setText(R.string.ErrorMessage);
                }
            }

            @Override
            public void onFailure(Call<MotionPictureApiResults> call, Throwable t) {
                Log.d("MainActivity", "getMotionPicture: onFailure " + t.getMessage());
                // textview.setText(R.string.ErrorMessage);

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView sv = (SearchView) item.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //setzen der Shared Preferences
                SharedPreferences.Editor spe = sp.edit();
                spe.putString("lastSearchExpression", query);
                spe.apply();

                motionPictureList.clear();
                //API-Aufruf mit Titelfilterung
                getfilteredMotionPictureTitle(query);
                //Aktualisieren des Adapters
                pa.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //keine Funktion hier, da sonst bei jedem Buchstaben eine neue API-Anfrage gestellt werden muss
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.search:{
                // Suchfeld aufklappen oder so
                //Wird vermutlich nicht benötigt, das hier -> TODO entfernen u.U.
                break;
            }
        }
        pa.notifyDataSetChanged();
        return true;
    }
}