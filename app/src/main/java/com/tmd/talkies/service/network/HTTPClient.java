package com.tmd.talkies.service.network;

import static com.tmd.talkies.utils.AppConstants.BASE_URL;
import static com.tmd.talkies.utils.ClientUtils.getHttpClientBuilder;

import com.tmd.talkies.service.model.MovieResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class HTTPClient {

    static HTTPInterface httpInterface;
    public static HTTPInterface getHttpInterface(){
        if (httpInterface==null){
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
            httpInterface = retrofit.create(HTTPInterface.class);
        }
        return httpInterface;
    }

    public interface HTTPInterface{
        @GET("movie/popular")
        Single<MovieResponse> getMoviesByPage(@Query("page") int page);
    }


}
