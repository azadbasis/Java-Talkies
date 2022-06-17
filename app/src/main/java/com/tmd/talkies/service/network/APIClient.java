package com.tmd.talkies.service.network;

import static com.tmd.talkies.service.network.APIEndPoint.MOVIE_POPULAR;
import static com.tmd.talkies.utils.AppConstants.BASE_URL;
import static com.tmd.talkies.utils.ClientUtils.getHttpClientBuilder;

import com.tmd.talkies.service.model.MovieResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class APIClient {

    static APIInterface APIInterface;
    public static APIInterface getAPIInterface(){
        if (APIInterface ==null){
            // Create retrofit instance
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getHttpClientBuilder())
                    // Add Gson converter
                    .addConverterFactory(GsonConverterFactory.create())
                    // Add RxJava support for Retrofit
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
            // Init APIInterface
            APIInterface = retrofit.create(APIInterface.class);
        }
        return APIInterface;
    }

    public interface APIInterface {
        @GET(MOVIE_POPULAR)
        Single<MovieResponse> getMoviesByPage(@Query("page") int page);
        //<base_url>movie/<movie_id>
    }


}
