package com.tmd.talkies.service.network;

public final class APIEndPoint {


//https://api.themoviedb.org/3/movie/top_rated?api_key={{API_KEY}}&language=en-US&page=1
    public static final String MOVIE_TOP_RATED = "movie/top_rated";
    public static final String MOVIE_POPULAR = "movie/popular";


    private APIEndPoint() {
        // This class is not publicly instantiable
    }
}

