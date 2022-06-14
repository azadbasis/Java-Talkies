package com.tmd.talkies.service.network;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String TAG = "ApiClient";
    public static final String TMDB_API_KEY = "eb8aa6f914f794f711fb1841fb141f12";
    public static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";
    private static ApiClient instance;
    private final ApiServices apiServices;

    private ApiClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(TMDB_BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(getHttpClientBuilder()).build();
        apiServices = retrofit.create(ApiServices.class);

    }

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    public static OkHttpClient getHttpClientBuilder() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor clientInterceptor = chain -> {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder().addQueryParameter("api_key", TMDB_API_KEY).build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
        };
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(clientInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    public ApiServices getApiServices() {
        return apiServices;
    }


}
