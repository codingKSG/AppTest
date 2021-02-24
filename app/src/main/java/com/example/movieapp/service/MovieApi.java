package com.example.movieapp.service;

import com.example.movieapp.model.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieApi {

    @GET("api/movie")
    Call<List<Movie>> getMovies();

    @DELETE("api/movie/{id}")
    Call<String> deleteById(@Path("id") Long id);

    Gson gson = new GsonBuilder().setLenient().create();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://112.162.114.11:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
