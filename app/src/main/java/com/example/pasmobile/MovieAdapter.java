package com.example.pasmobile;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyMovieAdapter>{

    private Context context;

    private MovieAdapterListener listener;

    private List<MovieModel> movieList;

    public class MyMovieAdapter extends RecyclerView.ViewHolder {

        //
        public TextView tvTitleMovie;
        public TextView tvDate;
        public TextView tvVote;
        public TextView tvLanguage;
        public TextView tvMovieDesc;
        public ImageView ivMovie;

        public MyMovieAdapter(@NonNull View itemView) {
            super(itemView);
            //
            tvTitleMovie = itemView.findViewById(R.id.tvTitleMovie);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvVote = itemView.findViewById(R.id.tvVote);
            tvLanguage = itemView.findViewById(R.id.tvLanguage);
            tvMovieDesc = itemView.findViewById(R.id.tvMovieDesc);
            ivMovie = itemView.findViewById(R.id.ivMovie);


           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   listener.onListMovieSelected(movieList.get(getAdapterPosition()));
               }
           });


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (listener != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            listener.onItemLongClick(pos);
                        }
                    }
                    return true;
                }

            });
        }
    }

    public MovieAdapter(Context context, List<MovieModel> movieList, MovieAdapterListener listener) {
        this.context = context;
        this.movieList = movieList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieAdapter.MyMovieAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new MyMovieAdapter(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MyMovieAdapter holder, int position) {
        final MovieModel listMovie = this.movieList.get(position);
        //
        holder.tvTitleMovie.setText(listMovie.getTitle());
        holder.tvDate.setText(listMovie.getDate());
        holder.tvVote.setText(listMovie.getVote());
        holder.tvLanguage.setText(listMovie.getLanguage());
        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500/"+listMovie.getPoster()).into(holder.ivMovie);
    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }

    public interface MovieAdapterListener{

        void onItemLongClick(int position);

        void onListMovieSelected(MovieModel ListMovie);


    }

}
