package com.tmd.talkies.view.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tmd.talkies.service.model.MovieResponse;
import com.tmd.talkies.service.network.Resource;
import com.tmd.talkies.service.repository.IMovieRepository;
import com.tmd.talkies.service.repository.MovieRepositoryImpl;

public class MainViewModel extends AndroidViewModel {

    private IMovieRepository repository;
    private MainNavigator navigator;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = MovieRepositoryImpl.getInstance(application);
    }

    public void setNavigator(MainNavigator navigator) {
        this.navigator = navigator;
    }

    public LiveData<Resource<MovieResponse>> getTopRatedMovie(String language, int pageNumber) {
        navigator.onStarted();
        return repository.getTopRatedMovie(language, pageNumber);
    }
}
