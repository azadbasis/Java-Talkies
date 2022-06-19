package com.tmd.talkies.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import com.bumptech.glide.RequestManager;
import com.tmd.talkies.databinding.ActivityMainBinding;
import com.tmd.talkies.service.model.Movie;
import com.tmd.talkies.utils.MovieComparator;
import com.tmd.talkies.utils.SpaceUtils;
import com.tmd.talkies.view.base.BaseActivity;
import com.tmd.talkies.view.detail.DetailActivity;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity<ActivityMainBinding>{

    private static final String TAG = "MainActivity";

    @NonNull
    @Override
    protected ActivityMainBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivityMainBinding.inflate(layoutInflater);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

}