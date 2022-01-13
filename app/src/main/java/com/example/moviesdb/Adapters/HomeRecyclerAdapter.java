package com.example.moviesdb.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesdb.Listeners.OnMovieClickListener;
import com.example.moviesdb.Models.SearchArrayObject;
import com.example.moviesdb.R;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeViewHolder>{
    Context Context;
    List<SearchArrayObject> list;
    OnMovieClickListener listener;

    public HomeRecyclerAdapter(android.content.Context context, List<SearchArrayObject> list, OnMovieClickListener listener) {
        Context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(Context).inflate(R.layout.home_movies_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull  HomeViewHolder holder, int position) {
        holder.textview_movie.setText(list.get(position).getTitle());
        holder.textview_movie.setSelected(true);
        Picasso.get().load(list.get(position).getImage()).into(holder.imageview_poster);

        holder.home_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMovieClicked(list.get(position).getId());
            }
        });

        holder.home_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMovieClicked(list.get(position).getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class HomeViewHolder extends RecyclerView.ViewHolder{
    ImageView imageview_poster;
    TextView textview_movie;
    MaterialCardView home_container;

    public HomeViewHolder(@NonNull  View itemView) {
        super(itemView);

        imageview_poster = itemView.findViewById(R.id.imageview_poster);
        textview_movie = itemView.findViewById(R.id.textview_movie);
        home_container= itemView.findViewById(R.id.home_container);


    }
}
