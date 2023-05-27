package com.example.pasmobile;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterListener {
    RecyclerView rvKontakName;
    ArrayList<MovieModel> listDataEPLTeams;
    private MovieAdapter adapterListKontak;

    public void getMovieModel() {
        String url = "https://api.themoviedb.org/3/movie/popular?language=en-US&page=1&api_key=8f9a90c460a187a0945c8f561d9a3bbe";

        AndroidNetworking.get(url)


                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        // Tangani respons JSON di sini
                        Log.d("success ", "onResponse: "+jsonObject.toString());
                        try {
                            JSONArray jsonArrayEPLTeam = jsonObject.getJSONArray("results");
                            for (int i = 0; i < jsonArrayEPLTeam.length(); i++) {
                                MovieModel myTeam = new MovieModel();
                                JSONObject jsonTeam = jsonArrayEPLTeam.getJSONObject(i);
                                myTeam.setTitle(jsonTeam.getString("title"));
                                myTeam.setPoster(jsonTeam.getString("poster_path"));
                                myTeam.setDate(jsonTeam.getString("release_date"));

                                    listDataEPLTeams.add(myTeam);
                            }
                            rvKontakName = findViewById(R.id.rvkontakname);
//                            progressBar = findViewById(R.id.progressBar);
                            adapterListKontak = new MovieAdapter(getApplicationContext(), listDataEPLTeams,MainActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rvKontakName.setHasFixedSize(true);
                            rvKontakName.setLayoutManager(mLayoutManager);
                            rvKontakName.setAdapter(adapterListKontak);
//                            progressBar.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listDataEPLTeams = new ArrayList<>();

        getMovieModel();
    }

    @Override
    public void onContactSelected(MovieModel myMovie) {

    }
}