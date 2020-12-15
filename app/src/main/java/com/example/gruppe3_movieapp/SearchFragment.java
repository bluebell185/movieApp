package com.example.gruppe3_movieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.gruppe3_movieapp.AppConstFunctions.PREF_LAST_SEARCH_EXPRESSION;
import static com.example.gruppe3_movieapp.AppConstFunctions.sp;

/**
 * @author Elena Ozsvald
 */
public class SearchFragment extends Fragment {
    ArrayList<MotionPicture> motionPictureList = new ArrayList<>();
    List<MotionPicture> initialTitleSearchList = new ArrayList<>();
    TextView tvTitleSearch, tvYearSearch, tvApiErrorMessage, tvOutputApiObject;
    ImageView ivTypeSearch, ivCoverSearch;
    Button btnSearch, btnFilterTypeMovie, btnFilterTypeSeries, btnSortYear;
    EditText etSearch;
    RecyclerView rvSearch;
    MotionPictureAdapterSearch pa;
    MotionPictureRepo motionPictureRepo = new MotionPictureRepo();
    String lastSearchExpression = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * @author Kathrin Ulmer
     * @author Elena Ozsvald
     * @author Mohamed Ali El-Maoula
     */
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
        SharedPreferences.Editor spe = sp.edit();

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
                    }
                })
        );

        /**
         * @author Kathrin Ulmer
         */
        btnSearch.setOnClickListener(v -> {
            String title = etSearch.getText().toString();
            if (title.equals("")){
                //cursor soll aktiviert werden im Feld
                etSearch.requestFocus();
            }
            else{
                motionPictureList.clear();
                spe.putString(PREF_LAST_SEARCH_EXPRESSION, title);
                spe.apply();
                getfilteredMotionPictureTitle(title);
            }
        });
        btnSearch.setOnLongClickListener(v -> {
            makeToast(getText(R.string.search_title).toString());
            return true;
        });

        btnFilterTypeMovie.setOnClickListener(v -> {
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
        });
        btnFilterTypeMovie.setOnLongClickListener(v -> {
            makeToast(getText(R.string.filter_type_movie).toString());
            return true;
        });

        btnFilterTypeSeries.setOnClickListener(v -> {
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

        });
        btnFilterTypeSeries.setOnLongClickListener(v -> {
            makeToast(getText(R.string.filter_type_series).toString());
            return true;
        });

        btnSortYear.setOnClickListener(v -> {
            //sortiert Inhalte nach Jahr absteigend
            ArrayList<MotionPicture> sortedList = (ArrayList<MotionPicture>) motionPictureList.stream().sorted((v1, v2)-> v2.getYear().compareTo(v1.getYear())).collect(Collectors.toList());
            motionPictureList.clear();
            motionPictureList.addAll(sortedList);
            pa.notifyDataSetChanged();
        });
        btnSortYear.setOnLongClickListener(v -> {
            makeToast(getText(R.string.sort_year).toString());
            return true;
        });

        etSearch.setOnKeyListener((v, keyCode, event) -> {
            // wenn Enter gedrückt wird
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                motionPictureList.clear();
                spe.putString(PREF_LAST_SEARCH_EXPRESSION, etSearch.getText().toString());
                spe.apply();
                if (etSearch.getText().toString().equals("")){
                    showErrorMessage(getString(R.string.outputApiObject));
                }
                else{
                    getfilteredMotionPictureTitle(etSearch.getText().toString());
                }
                return true;
            }
            return false;
        });

        return view;
    }

    /**
     * @author Mohamed Ali El-Maoula
     */
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

    /**
     * @author Mohamed Ali El-Maoula
     */

    private void showData(MotionPictureApiResults motionPictureApiResults){
        rvSearch.setVisibility(View.VISIBLE);
        tvOutputApiObject.setVisibility(View.INVISIBLE);
        //output ausblenden
        initialTitleSearchList.clear();
        motionPictureList.clear();
        motionPictureList.addAll(motionPictureApiResults.getMotionPicture().stream().filter(c-> c.getType().equals("movie") || c.getType().equals("series")).collect(Collectors.toList()));
        initialTitleSearchList.addAll(motionPictureList);
        pa.notifyDataSetChanged();
        tvApiErrorMessage.setVisibility(View.INVISIBLE);
    }

    /**
     * @author Mohamed Ali El-Maoula
     */

    private void showErrorMessage(String failure){
        tvApiErrorMessage.setVisibility(View.VISIBLE);
        tvApiErrorMessage.setText(failure);
        tvOutputApiObject.setVisibility(View.INVISIBLE);
        rvSearch.setVisibility(View.INVISIBLE);
    }

    /**
     * @author Kathrin Ulmer
     */
    private void makeToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}