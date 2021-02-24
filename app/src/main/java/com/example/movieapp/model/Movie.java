package com.example.movieapp.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    private long id;
    private String url;
    private String title;
    private long year;
    private double rating;
    private long runtime;
    private String summary;
    @SerializedName("medium_cover_image")
    private String medium_cover_image;

    @BindingAdapter({"medium_cover_image"})
    public static void loadImage(ImageView view, String medium_cover_image){
        Glide.with(view.getContext())
                .load(medium_cover_image)
                .into(view);
    }
}
