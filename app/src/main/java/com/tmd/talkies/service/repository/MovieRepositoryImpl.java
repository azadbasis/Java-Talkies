package com.tmd.talkies.service.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.tmd.talkies.service.model.MovieResponse;
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
    private MutableLiveData<Resource<MovieResponse>> resourceMutableLiveData;

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
    public MutableLiveData<Resource<MovieResponse>> getTopRatedMovie(String language, int pageNumber) {
        if (resourceMutableLiveData == null) {
            resourceMutableLiveData = new MutableLiveData<>();
        }
        resourceMutableLiveData.setValue(null);
        Call<MovieResponse> call = apiServices.getTopRatedMovie(language, pageNumber);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    resourceMutableLiveData.postValue(Resource.success(response.body()));
                } else {
                    String errorMessage = ErrorUtils.getErrorMessage(response);
                    resourceMutableLiveData.postValue(Resource.error(errorMessage, null, response.code()));
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                resourceMutableLiveData.postValue(Resource.error(t.getMessage(), null, 0));

            }
        });
        return resourceMutableLiveData;
    }
}
