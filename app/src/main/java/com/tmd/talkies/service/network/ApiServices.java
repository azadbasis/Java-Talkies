package com.tmd.talkies.service.network;

import static com.tmd.talkies.service.network.ApiEndPoint.MOVIE_TOP_RATED;

import com.tmd.talkies.service.model.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {

    @GET(MOVIE_TOP_RATED)
    Call<Movie> getTopRatedMovie(
            @Query("language") String language,
            @Query("page") int pageNumber
    );

}
