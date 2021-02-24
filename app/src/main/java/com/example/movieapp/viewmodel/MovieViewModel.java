package com.example.movieapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> mtMovies = new MutableLiveData<>();


    public MutableLiveData<List<Movie>> 구독() {
        return mtMovies;
    }

    public void 전체가져오기(List<Movie> movies) {

        mtMovies.setValue(movies);
    }

    public void 데이터초기화() {
        List<Movie> movies = new ArrayList<>();
        mtMovies.setValue(movies);
    }

    public void 데이터삭제(int id) {
        List<Movie> movies = mtMovies.getValue();
        movies.remove(id);
        mtMovies.setValue(movies);
    }

}
