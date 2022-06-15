package com.tmd.talkies.view.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.tmd.talkies.databinding.ActivityMainBinding;
import com.tmd.talkies.service.model.MovieResponse;
import com.tmd.talkies.service.model.Movie;
import com.tmd.talkies.service.network.Resource;
import com.tmd.talkies.utils.SpaceUtils;
import com.tmd.talkies.view.base.BaseActivity;

import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements MainNavigator, MovieAdapter.MovieListener{
    private static final String TAG = "MainActivity";
    private MainViewModel viewModel;
    private MovieAdapter movieAdapter;
    @NonNull
    @Override
    protected ActivityMainBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivityMainBinding.inflate(layoutInflater);    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.setNavigator(this);
        setAdapter();
        observeViewModel();
    }

    private void setAdapter() {
        GridLayoutManager  gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);//reverse scrolling of recyclerview
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        //add space between cards
        binding.recyclerView.addItemDecoration(new SpaceUtils(2, 20, true, 0));
        movieAdapter = new MovieAdapter(this);
        binding.recyclerView.setAdapter(movieAdapter);

    }

    private void observeViewModel() {
        viewModel.getTopRatedMovie("en-US",1).observe(this, new Observer<Resource<MovieResponse>>() {
            @Override
            public void onChanged(Resource<MovieResponse> topMovieResource) {
                binding.progressBar.setVisibility(View.VISIBLE);
                if (topMovieResource!=null){
                    switch (topMovieResource.status){
                        case SUCCESS:
                            MovieResponse movieResponse =topMovieResource.data;
                            List<Movie> topovies = movieResponse.getResults();
                            movieAdapter.setItems(topovies);
                            Log.d(TAG, "topMovie: "+ movieResponse.getResults().size());
                            onSuccess("success");
                            binding.progressBar.setVisibility(View.GONE);
                            break;
                        case ERROR:
                            onFailure("Failed");
                            binding.progressBar.setVisibility(View.GONE);
                            break;
                        case LOADING:
                            onFailure("Failed to load!");
                            binding.progressBar.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onStarted() {
    }

    @Override
    public void onSuccess(String message) {
        Log.d(TAG, "onSuccess: ");
    }

    @Override
    public void onFailure(String message) {
        Log.d(TAG, "onFailure: ");
    }

    @Override
    public void onMovieClicked(Movie movie) {
    }
}