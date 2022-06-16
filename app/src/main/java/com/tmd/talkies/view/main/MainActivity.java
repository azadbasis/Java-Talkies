package com.tmd.talkies.view.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.RequestManager;
import com.tmd.talkies.databinding.ActivityMainBinding;
import com.tmd.talkies.service.model.MovieResponse;
import com.tmd.talkies.service.model.Movie;
import com.tmd.talkies.service.network.Resource;
import com.tmd.talkies.utils.MovieComparator;
import com.tmd.talkies.utils.SpaceUtils;
import com.tmd.talkies.view.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity<ActivityMainBinding>{

    private static final String TAG = "MainActivity";
    private MovieViewModel mainActivityViewModel;
    private MoviesAdapter moviesAdapter;

    @Inject
    RequestManager requestManager;


    @NonNull
    @Override
    protected ActivityMainBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivityMainBinding.inflate(layoutInflater);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "requestManager: "+requestManager);
        moviesAdapter = new MoviesAdapter(new MovieComparator(),requestManager);
        mainActivityViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        initRecyclerviewAndAdapter();
        mainActivityViewModel.moviePagingDataFlowable.subscribe(moviePagingData -> {
            moviesAdapter.submitData(getLifecycle(), moviePagingData);
        });
    }

    private void initRecyclerviewAndAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);//reverse scrolling of recyclerview
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        //add space between cards
        binding.recyclerView.addItemDecoration(new SpaceUtils(2, 20, true, 0));
//        movieAdapter = new MovieAdapter(this);
        // set adapter
        binding.recyclerView.setAdapter(
                // This will show end user a progress bar while pages are being requested from server
                moviesAdapter.withLoadStateFooter(
                        // When we will scroll down and next page request will be sent
                        // while we get response form server Progress bar will show to end user
                        new MoviesLoadStateAdapter(v -> {
                            moviesAdapter.retry();
                        }))
        );
        //moviesAdapter.addLoadStateListener();
        // set Grid span to set progress at center
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                // If progress will be shown then span size will be 1 otherwise it will be 2
                return moviesAdapter.getItemViewType(position) == MoviesAdapter.LOADING_ITEM ? 1 : 2;
            }
        });
    }

}