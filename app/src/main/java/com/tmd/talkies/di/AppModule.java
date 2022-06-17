package com.tmd.talkies.di;

import static com.tmd.talkies.utils.AppConstants.API_KEY;

import android.app.DownloadManager;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.tmd.talkies.R;
import com.tmd.talkies.utils.ClientUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Provides
    @Singleton
    public RequestManager getGlide(@ApplicationContext Context context) {
        return Glide.with(context).applyDefaultRequestOptions(
                new RequestOptions()
                        .error(R.drawable.ic_launcher_background)
                        .placeholder(R.drawable.ic_launcher_background)
        );
    }

}
