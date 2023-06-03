package com.example.pasmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailPage extends AppCompatActivity {
    Intent i;
    MovieModel movieModel;
    TextView tvMovieName;
    TextView tvMovieDate;
    TextView tvRating;
    TextView tvMovieLanguage;
    TextView tvMovieDesc;
    ImageView ivDetailMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        i = getIntent();
        movieModel = (MovieModel) i.getParcelableExtra("myteam");
        System.out.println("Name Movie : "+movieModel.getTitle());

        tvMovieName = findViewById(R.id.tvMovieName);
        tvMovieName.setText(movieModel.getTitle());

        tvMovieDate = findViewById(R.id.tvMovieDate);
        tvMovieDate.setText(movieModel.getDate());

        tvRating = findViewById(R.id.tvRating);
        tvRating.setText(movieModel.getVote());

        tvMovieLanguage = findViewById(R.id.tvMovieLanguage);
        tvMovieLanguage.setText(movieModel.getLanguage());

        tvMovieDesc = findViewById(R.id.tvMovieDesc);
        tvMovieDesc.setText(movieModel.getDeskripsi());


        ivDetailMovie = findViewById(R.id.ivDetailMovie);
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" + movieModel.getPoster()).into(ivDetailMovie);
    }
}