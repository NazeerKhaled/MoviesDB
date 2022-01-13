package com.example.moviesdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;

import com.example.moviesdb.Adapters.HomeRecyclerAdapter;
import com.example.moviesdb.Listeners.OnMovieClickListener;
import com.example.moviesdb.Listeners.OnSearchApiListener;
import com.example.moviesdb.Models.SearchApiResponse;

public class MainActivity extends AppCompatActivity implements OnMovieClickListener {
    SearchView searchView;
    RecyclerView recyclerview_home;
    HomeRecyclerAdapter adapter;
    RequestManger manger;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        searchView= findViewById(R.id.search_view);
        recyclerview_home = findViewById(R.id.recyclerview_home);
        dialog =new ProgressDialog(this);
        manger = new RequestManger(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Please Wait...");
                dialog.show();
                manger.searchMovies(listener, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
    private final OnSearchApiListener listener = new OnSearchApiListener() {

        @Override
        public void onResponse(SearchApiResponse response) {
            dialog.dismiss();
            if (response==null){
                Toast.makeText(MainActivity.this,"No data available",Toast.LENGTH_SHORT).show();
                return;
            }
            showResult(response);
        }

        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(MainActivity.this,"An Error Occurred!!",Toast.LENGTH_SHORT).show();

        }
    };

    private void showResult(SearchApiResponse response) {
        recyclerview_home.setHasFixedSize(true);
        recyclerview_home.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
        adapter= new HomeRecyclerAdapter(this,response.getTitles(),this);
        recyclerview_home.setAdapter(adapter);

    }

    @Override
    public void onMovieClicked(String id) {
        startActivity(new Intent(MainActivity.this,DetailsActivity.class)
        .putExtra("data",id));
    }
}