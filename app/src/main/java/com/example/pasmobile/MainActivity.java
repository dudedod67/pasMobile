package com.example.pasmobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
                                myTeam.setVote(jsonTeam.getString("vote_average"));
                                myTeam.setLanguage(jsonTeam.getString("original_language"));
                                myTeam.setDeskripsi(jsonTeam.getString("overview"));

                                    listDataEPLTeams.add(myTeam);
                            }
                            rvKontakName = findViewById(R.id.rvkontakname);
                            adapterListKontak = new MovieAdapter(getApplicationContext(), listDataEPLTeams,MainActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rvKontakName.setHasFixedSize(true);
                            rvKontakName.setLayoutManager(mLayoutManager);
                            rvKontakName.setAdapter(adapterListKontak);


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
    public void onItemLongClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Perhatian!")
                .setMessage("Apakah kamu yakin ingin menghapus item ini?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Tindakan yang dilakukan ketika tombol OK diklik
                        listDataEPLTeams.remove(position);
                        adapterListKontak.notifyItemRemoved(position);
                        Toast.makeText(MainActivity.this.getApplicationContext(), "Dihapus", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Tindakan yang dilakukan ketika tombol Batal diklik
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onListMovieSelected(MovieModel ListMovie) {
        Intent intent = new Intent(MainActivity.this, DetailPage.class);
        intent.putExtra("myteam", ListMovie);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listDataEPLTeams = new ArrayList<>();

        getMovieModel();
    }


}