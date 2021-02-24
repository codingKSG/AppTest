package com.example.movieapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.adapter.MovieAdapter;
import com.example.movieapp.model.Movie;
import com.example.movieapp.service.MovieApi;
import com.example.movieapp.viewmodel.MovieViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity2";
    private MovieViewModel movieViewModel;
    private RecyclerView rvContainer;
    private MovieAdapter movieAdapter;
    private MainActivity mainActivity;
    private MovieApi movieApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieApi = MovieApi.retrofit.create(MovieApi.class);
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        movieViewModel.데이터초기화();

        // 1. 레트로핏에 findAll요청
        findAll();

        subscribe();
    }

    public void subscribe() {
        movieViewModel.구독().observe(this, movies -> {

            // 어뎁터에 던지기
            GridLayoutManager manager = new GridLayoutManager(this, 3);
            rvContainer = findViewById(R.id.rv_container);
            rvContainer.setLayoutManager(manager);

            movieAdapter = new MovieAdapter(movies, mainActivity);

            rvContainer.setAdapter(movieAdapter);
        });
    }

    public void findAll(){
        Call<List<Movie>> call = movieApi.getMovies();

        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                List<Movie> movies = response.body();
                movieViewModel.전체가져오기(movies);
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.d(TAG, "onFailure: 실패");
                t.printStackTrace();
            }
        });
    }

    public void deleteById(int position, long id){
        Log.d(TAG, "onResponse: position: "+position+"  id: "+ id);
        Call<String> call = movieApi.deleteById(id);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "onResponse: 성공");
                movieViewModel.데이터삭제(position);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure: 데이터삭제 실패");
                t.printStackTrace();
            }
        });
    }
}