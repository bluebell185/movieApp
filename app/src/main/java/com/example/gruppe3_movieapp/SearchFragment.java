package com.example.gruppe3_movieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.widget.SearchView;
import static com.example.gruppe3_movieapp.AppConstFunctions.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    ArrayList<MotionPicture> motionPictureList = new ArrayList<>();
    List<MotionPicture> initialTitleSearchList = new ArrayList<>();
    TextView tvTitleSearch;
    TextView tvYearSearch;
    ImageView ivTypeSearch;
    ImageView ivCoverSearch;
    Button btnSearch;
    Button btnFilterTypeMovie;
    Button btnFilterTypeSeries;
    Button btnSortYear;
    EditText etSearch;
    TextView tvApiErrorMessage;
    TextView tvOutputApiObject;
    RecyclerView rvSearch;
    MotionPictureAdapterSearch pa;
    MotionPictureRepo motionPictureRepo = new MotionPictureRepo();
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
        btnSearch = view.findViewById(R.id.btnSearch);
        btnFilterTypeMovie = view.findViewById(R.id.btnFilterTypeMovie);
        btnFilterTypeSeries = view.findViewById(R.id.btnFilterTypeSeries);
        btnSortYear = view.findViewById(R.id.btnSortYear);
        etSearch = view.findViewById(R.id.etSearch);

        tvApiErrorMessage = view.findViewById(R.id.tvApiErrorMessage);
        tvOutputApiObject = view.findViewById(R.id.tvOutputApiObject);
        rvSearch = view.findViewById(R.id.rvSearch);

        lastSearchExpression = sp.getString(PREF_LAST_SEARCH_EXPRESSION, "welcome");

        getfilteredMotionPictureTitle(lastSearchExpression);

        RecyclerView rvSearch = view.findViewById(R.id.rvSearch);

        pa = new MotionPictureAdapterSearch(motionPictureList);
        rvSearch.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));
        rvSearch.setAdapter(pa);

        initialTitleSearchList.addAll(motionPictureList);

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

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etSearch.getText().toString();
                if (title.equals("")){
                    //cursor soll aktiviert werden im Feld
                    etSearch.requestFocus();
                }
                else{
                    motionPictureList.clear();
                    getfilteredMotionPictureTitle(title);
                }
            }
        });
        btnSearch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                makeToast(getText(R.string.search_title).toString());
                return true;
            }
        });

        btnFilterTypeMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnFilterTypeMovie.isSelected()){
                    motionPictureList.clear();
                    motionPictureList.addAll(initialTitleSearchList);
                    pa.notifyDataSetChanged();
                    btnFilterTypeMovie.setSelected(false);
                }
                else {
                    motionPictureList.clear();
                    motionPictureList.addAll(initialTitleSearchList.stream().filter(c -> c.getType().equals("movie")).collect(Collectors.toList()));
                    pa.notifyDataSetChanged();
                    btnFilterTypeMovie.setSelected(true);
                    btnFilterTypeSeries.setSelected(false);
                }
            }
        });
        btnFilterTypeMovie.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                makeToast(getText(R.string.filter_type_movie).toString());
                return true;
            }
        });

        btnFilterTypeSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnFilterTypeSeries.isSelected()){
                    motionPictureList.clear();
                    motionPictureList.addAll(initialTitleSearchList);
                    pa.notifyDataSetChanged();
                    btnFilterTypeSeries.setSelected(false);
                }
                else{
                    motionPictureList.clear();
                    motionPictureList.addAll(initialTitleSearchList.stream().filter(c -> c.getType().equals("series")).collect(Collectors.toList()));
                    pa.notifyDataSetChanged();
                    btnFilterTypeSeries.setSelected(true);
                    btnFilterTypeMovie.setSelected(false);
                }

            }
        });
        btnFilterTypeSeries.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                makeToast(getText(R.string.filter_type_series).toString());
                return true;
            }
        });

        btnSortYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sortiert Inhalte nach Jahr absteigend
                motionPictureList.clear();
                motionPictureList.addAll((ArrayList<MotionPicture>) initialTitleSearchList.stream().sorted((v1, v2)-> v2.getYear().compareTo(v1.getYear())).collect(Collectors.toList()));
                pa.notifyDataSetChanged();
            }
        });
        btnSortYear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                makeToast(getText(R.string.sort_year).toString());
                return true;
            }
        });

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    motionPictureList.clear();
                    getfilteredMotionPictureTitle(etSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });

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

                        showData(motionPictureApiResults);


                    } else {

                        tvOutputApiObject.setText(R.string.outputApiObject);
                    }

                }
                else {
                    Log.d("MainActivity", "getMotionPicture: onResponse NOT successfull");
                    showErrorMessage(getString(R.string.motionPicture_error_NOT_succsessfull));
                }
            }

            @Override
            public void onFailure(Call<MotionPictureApiResults> call, Throwable t) {
                Log.d("MainActivity", "getMotionPicture: onFailure " + t.getMessage());
                showErrorMessage(getString(R.string.motionPicture_error_on_failure));

            }
        });
    }

    private void showData(MotionPictureApiResults motionPictureApiResults){
        rvSearch.setVisibility(View.VISIBLE);
        tvOutputApiObject.setVisibility(View.INVISIBLE);
        //output ausblenden
        initialTitleSearchList.clear();
        motionPictureList.addAll(motionPictureApiResults.getMotionPicture().stream().filter(c-> c.getType().equals("movie") || c.getType().equals("series")).collect(Collectors.toList()));
        initialTitleSearchList.addAll(motionPictureList);
        pa.notifyDataSetChanged();
        tvApiErrorMessage.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage(String failure){
        tvApiErrorMessage.setVisibility(View.VISIBLE);
        tvApiErrorMessage.setText(failure);
        tvOutputApiObject.setVisibility(View.INVISIBLE);
        rvSearch.setVisibility(View.INVISIBLE);
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
                spe.putString(PREF_LAST_SEARCH_EXPRESSION, query);
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


    private void makeToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}