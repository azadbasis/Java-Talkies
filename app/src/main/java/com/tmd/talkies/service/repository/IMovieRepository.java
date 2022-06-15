package com.tmd.talkies.service.repository;

import androidx.lifecycle.MutableLiveData;

import com.tmd.talkies.service.model.MovieResponse;
import com.tmd.talkies.service.network.Resource;

public interface IMovieRepository {
    MutableLiveData<Resource<MovieResponse>> getTopRatedMovie(String language, int pageNumber);
}
