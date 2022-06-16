package com.tmd.talkies.view.main;

import static com.tmd.talkies.utils.AppConstants.CDN_URL;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.tmd.talkies.databinding.MovieItemBinding;
import com.tmd.talkies.service.model.Movie;

import kotlinx.coroutines.CoroutineDispatcher;

public class MoviesAdapter extends PagingDataAdapter<Movie,MoviesAdapter.MovieViewHolder> {
    private static final String TAG = "MoviesAdapter";
    //Define Loading ViewType
    public static final int LOADING_ITEM=0;
    //Define Movie ViewType
    public static final int MOVIE_ITEM=1;
    RequestManager glide;
    private Context context;
    public MoviesAdapter(@NonNull DiffUtil.ItemCallback<Movie> diffCallback,  RequestManager glide) {
        super(diffCallback);
        this.glide=glide;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new MovieViewHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
     Movie currentMovie=getItem(position);

     if (currentMovie!=null){
         glide.load(CDN_URL+currentMovie.getPosterPath()).into(holder.itemBinding.image);
         holder.itemBinding.title.setText(String.valueOf(currentMovie.getVoteAverage()));
         holder.itemBinding.releaseDate.setText(currentMovie.getReleaseDate());
     }
    }

    @Override
    public int getItemViewType(int position) {
        return position==getItemCount()?MOVIE_ITEM:LOADING_ITEM;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        MovieItemBinding itemBinding;
        public MovieViewHolder(@NonNull  MovieItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding=itemBinding;
        }
    }
}
