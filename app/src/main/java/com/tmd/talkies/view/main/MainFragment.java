package com.tmd.talkies.view.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.tmd.talkies.R;
import com.tmd.talkies.databinding.FragmentMainBinding;
import com.tmd.talkies.service.model.Movie;
import com.tmd.talkies.utils.MovieComparator;
import com.tmd.talkies.utils.SpaceUtils;
import com.tmd.talkies.view.detail.DetailActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainFragment extends Fragment implements MoviesAdapter.IMovieListener {

    private static final String TAG = "MainFragment";
    private FragmentMainBinding binding;
    private MoviesAdapter moviesAdapter;
    private NavController navController;

    @Inject
    RequestManager requestManager;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        moviesAdapter = new MoviesAdapter(new MovieComparator(), requestManager, this);
        MovieViewModel mainActivityViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        initRecyclerviewAndAdapter();
        mainActivityViewModel.moviePagingDataFlowable.subscribe(moviePagingData -> {
            moviesAdapter.submitData(getLifecycle(), moviePagingData);
        });
        return view;
    }


    private void initRecyclerviewAndAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);//reverse scrolling of recyclerview
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        binding.recyclerView.addItemDecoration(new SpaceUtils(2, 20, true, 0));
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void listenMovieItem(Movie movie) {
        int movieId = movie.getId();
        Bundle bundle = new Bundle();
        bundle.putInt("movie_id", movieId);
        navController.navigate(R.id.action_mainFragment_to_detailFragment, bundle);
    }
}