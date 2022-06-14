package com.tmd.talkies.service.repository;

import androidx.lifecycle.MutableLiveData;

import com.tmd.talkies.service.model.Movie;
import com.tmd.talkies.service.network.Resource;

public interface IMovieRepository {
    MutableLiveData<Resource<Movie>> getTopRatedMovie(String language, int pageNumber);
}
