package com.tmd.talkies.service.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.tmd.talkies.service.model.Movie;
import com.tmd.talkies.service.network.ApiClient;
import com.tmd.talkies.service.network.ApiServices;
import com.tmd.talkies.service.network.Resource;
import com.tmd.talkies.utils.ErrorUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepositoryImpl implements IMovieRepository {

    private static final String TAG = "MovieRepositoryImpl";

    private static MovieRepositoryImpl instance;
    private static Context mContext;
    private static ApiServices apiServices;
    private MutableLiveData<Resource<Movie>> resourceMutableLiveData;

    private MovieRepositoryImpl() {
    }

    public static MovieRepositoryImpl getInstance(Context context) {
        if (instance == null) {
            mContext = context;
            instance = new MovieRepositoryImpl();
            apiServices = ApiClient.getInstance().getApiServices();
        }
        return instance;
    }

    @Override
    public MutableLiveData<Resource<Movie>> getTopRatedMovie(String language, int pageNumber) {
        if (resourceMutableLiveData == null) {
            resourceMutableLiveData = new MutableLiveData<>();
        }
        resourceMutableLiveData.setValue(null);
        Call<Movie> call = apiServices.getTopRatedMovie(language, pageNumber);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    resourceMutableLiveData.postValue(Resource.success(response.body()));
                } else {
                    String errorMessage = ErrorUtils.getErrorMessage(response);
                    resourceMutableLiveData.postValue(Resource.error(errorMessage, null, response.code()));
                }
            }
            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                resourceMutableLiveData.postValue(Resource.error(t.getMessage(), null, 0));

            }
        });
        return resourceMutableLiveData;
    }
}
