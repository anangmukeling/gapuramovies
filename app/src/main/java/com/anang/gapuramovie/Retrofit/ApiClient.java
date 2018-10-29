package com.anang.gapuramovie.Retrofit;

import com.anang.gapuramovie.API.ApiMovieInterface;
import com.anang.gapuramovie.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Anang Muklhisin on 31/07/2018.
 */

public class ApiClient {

    // Base URL The Movie DB
    private static final String BASE_URL = BuildConfig.BASE_URL;

    private ApiMovieInterface api;

    private static ApiClient instance = null;

    private ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ApiMovieInterface.class);
    }

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    public ApiMovieInterface getApi() {
        return api;
    }

}
