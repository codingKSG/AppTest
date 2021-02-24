package com.example.movieapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.MainActivity;
import com.example.movieapp.R;
import com.example.movieapp.model.Movie;
import com.example.movieapp.viewmodel.MovieViewModel;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private static final String TAG = "MovieAdapter2";
    private final List<Movie> movies ;
    private final MainActivity mainActivity;
    private MovieViewModel movieViewModel;
    private int adapterPosition = 0;

    public MovieAdapter(List<Movie> movies, MainActivity mainActivity) {
        this.movies = movies;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.movie_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setItem(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivMovieImg;
        private TextView tvMovieTitle;
        private TextView tvMovieRating;
        private Button btnMovieDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivMovieImg = itemView.findViewById(R.id.iv_movie_img);
            tvMovieTitle = itemView.findViewById(R.id.tv_movie_title);
            tvMovieRating = itemView.findViewById(R.id.tv_movie_rating);
            btnMovieDelete = itemView.findViewById(R.id.btn_movie_delete);

            btnMovieDelete.setOnClickListener(v -> {
                adapterPosition = getAdapterPosition();
                mainActivity.deleteById(adapterPosition, movies.get(adapterPosition).getId());
            });

            ivMovieImg.setOnClickListener(v -> {

                View dialog = v.inflate(v.getContext(), R.layout.detail_movie, null);

                ImageView ivDetailImg = dialog.findViewById(R.id.iv_detail_img);
                TextView tvDetailTitle = dialog.findViewById(R.id.tv_detail_title);
                TextView tvDetailSummary = dialog.findViewById(R.id.tv_detail_summary);
                TextView tvDetailRating = dialog.findViewById(R.id.tv_detail_rating);
                TextView tvDetailRuntime = dialog.findViewById(R.id.tv_detail_runtime);
                Button btnDetailUrl = dialog.findViewById(R.id.btn_detail_url);

                adapterPosition = getAdapterPosition();

                Glide.with(dialog).load(movies.get(adapterPosition).getMedium_cover_image()).into(ivDetailImg);
                tvDetailTitle.setText(movies.get(adapterPosition).getTitle());
                tvDetailSummary.setText(movies.get(adapterPosition).getSummary());
                tvDetailRating.setText(movies.get(adapterPosition).getRating()+"");
                tvDetailRuntime.setText(movies.get(adapterPosition).getRuntime()+"");

                AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());

                SharedPreferences pref = v.getContext().getSharedPreferences("pref", MainActivity.MODE_PRIVATE);

                btnDetailUrl.setOnClickListener(v1 -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movies.get(adapterPosition).getUrl()));
                    v1.getContext().startActivity(intent);
                });

                dlg.setTitle("영화 정보");
                dlg.setView(dialog);
                dlg.setNegativeButton("닫기", null);
                dlg.show();
            });

        }

        public void setItem(Movie movie) {
            Glide.with(itemView).load(movie.getMedium_cover_image()).into(ivMovieImg);
            tvMovieTitle.setText(movie.getTitle());
            tvMovieRating.setText(movie.getRating()+"");
        }

    }
}
