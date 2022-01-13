package com.example.moviesdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesdb.Adapters.CastRecyclerAdapter;
import com.example.moviesdb.Listeners.OnDetailsApisListener;
import com.example.moviesdb.Models.DetailApiResponse;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    TextView textView_movieName,textView_movieReleased,textView_movieRuntime,textView_movieRating,textView_movieVotes,textView_MoviePlot,textview_actor,textview_character;
    ImageView imageview_moviePoster;
    RecyclerView recycler_movieCast;
    CastRecyclerAdapter adapter;
    RequestManger manager;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        textView_movieName =findViewById(R.id.textView_movieName);
        textView_movieReleased =findViewById(R.id.textView_movieReleased);
        textView_movieRuntime =findViewById(R.id.textView_movieRuntime);
        textView_movieRating =findViewById(R.id.textView_movieRating);
        textView_movieVotes =findViewById(R.id.textView_movieVotes);
        textview_actor =findViewById(R.id.textview_actor);
        textview_character =findViewById(R.id.textview_character);
        imageview_moviePoster =findViewById(R.id.imageview_moviePoster);
        recycler_movieCast =findViewById(R.id.recycler_movieCast);
        textView_MoviePlot =findViewById(R.id.textView_MoviePlot);

        manager = new RequestManger(this);

        String movie_id = getIntent().getStringExtra("data");
        dialog = new ProgressDialog(this);
        dialog.setTitle("Please Wait...");
        dialog.show();
        manager.searchMovieDetails(listener,movie_id);

    }
    private OnDetailsApisListener listener = new OnDetailsApisListener() {
        @Override
        public void onResponse(DetailApiResponse response) {
            dialog.dismiss();
            if (response.equals(null)){
                Toast.makeText(DetailsActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                return;
            }
            showResults(response);
        }

        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(DetailsActivity.this, "Error!!", Toast.LENGTH_SHORT).show();

        }
    };

    private void showResults(DetailApiResponse response) {
        textView_movieName.setText(response.getTitle());
        textView_movieReleased.setText("Year Released: " + response.getYear());
        textView_movieRuntime.setText("Length"+response.getLength());
        textView_movieRating.setText("Rating: "+ response.getRating());
        textView_movieVotes.setText(response.getRating_votes() + "Votes");
        textView_MoviePlot.setText(response.getPlot());

        try {
            Picasso.get().load(response.getPoster()).into(imageview_moviePoster);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        recycler_movieCast.setHasFixedSize(true);
        recycler_movieCast.setLayoutManager(new GridLayoutManager(this,1));
        adapter = new CastRecyclerAdapter(this,response.getCast());
        recycler_movieCast.setAdapter(adapter);
    }
}