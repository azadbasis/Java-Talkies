package com.tmd.talkies.view.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tmd.talkies.R;
import com.tmd.talkies.service.model.MovieDetailsResponse;
import com.tmd.talkies.service.network.APIClient;

import okhttp3.Cache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        int movieId = intent.getIntExtra("movie_id", 0);
        Call<MovieDetailsResponse> call =APIClient.getAPIInterface().getMovieDetailsById(movieId);
        call.enqueue(new Callback<MovieDetailsResponse>() {
            @Override
            public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {
                Log.d(TAG, "onResponse: "+response.body());
            }

            @Override
            public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}