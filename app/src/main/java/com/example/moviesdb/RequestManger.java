package com.example.moviesdb;

import android.content.Context;
import android.widget.Toast;

import com.example.moviesdb.Listeners.OnDetailsApisListener;
import com.example.moviesdb.Listeners.OnSearchApiListener;
import com.example.moviesdb.Models.DetailApiResponse;
import com.example.moviesdb.Models.SearchApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public class RequestManger {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://imdb-internet-movie-database-unofficial.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManger(Context context) {
        this.context = context;
    }

    public void searchMovies(OnSearchApiListener listener,String movie_name){
        getMovies getMovies = retrofit.create(getMovies.class);
        Call<SearchApiResponse>call = getMovies.callMovies(movie_name);

        call.enqueue(new Callback<SearchApiResponse>() {
            @Override
            public void onResponse(Call<SearchApiResponse> call, Response<SearchApiResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(context,"Couldn't fetch Data!!",Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<SearchApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());


            }
        });
    }
    public void searchMovieDetails(OnDetailsApisListener listener, String movie_id){
        getMoviesDetails getMoviesDetails = retrofit.create(getMoviesDetails.class);
        Call<DetailApiResponse>call = getMoviesDetails.callMovieDetails(movie_id);

        call.enqueue(new Callback<DetailApiResponse>() {
            @Override
            public void onResponse(Call<DetailApiResponse> call, Response<DetailApiResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(context,"Couldn't fetch Data!!",Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<DetailApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());


            }
        });
    }

    public interface getMovies{
        @Headers({
                "Accept: application/json",
                "x-rapidapi-host: imdb-internet-movie-database-unofficial.p.rapidapi.com",
                "x-rapidapi-key: a8e966b9a5msh47e4758bc1ada16p1baa6bjsn5f7ca439e998"
        })
        @GET("search/{movie_name}")
        Call<SearchApiResponse> callMovies(
                @Path("movie_name") String movie_name
        );
    }
    public interface getMoviesDetails{
        @Headers({
                "Accept: application/json",
                "x-rapidapi-host: imdb-internet-movie-database-unofficial.p.rapidapi.com",
                "x-rapidapi-key: a8e966b9a5msh47e4758bc1ada16p1baa6bjsn5f7ca439e998"
        })
        @GET("film/{movie_id}")
        Call<DetailApiResponse> callMovieDetails(
                @Path("movie_id") String movie_id
        );
    }

}
