package com.tmd.talkies.utils;

import static com.tmd.talkies.utils.AppConstants.API_KEY;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class ClientUtils {
    public static OkHttpClient getHttpClientBuilder() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor clientInterceptor = chain -> {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder()
                    // Add API Key as query string parameter
                    .addQueryParameter("api_key", API_KEY)
                    .build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
        };
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(clientInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();
    }
}
