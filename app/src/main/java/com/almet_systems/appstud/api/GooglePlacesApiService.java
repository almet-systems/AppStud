package com.almet_systems.appstud.api;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by razir on 1/16/2017.
 */

public class GooglePlacesApiService {

    static GooglePlacesApiService apiService;
    private static final String MAIN_URL = "https://maps.googleapis.com/maps/api/place/";
    GooglePlacesApiClient api;
    Retrofit retrofit;

    public static GooglePlacesApiService getInstance() {
        if (apiService == null) {
            apiService = new GooglePlacesApiService();
        }
        return apiService;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public GooglePlacesApiService() {
        retrofit = initRetrofit();
        api = retrofit.create(GooglePlacesApiClient.class);
    }

    protected Retrofit initRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .baseUrl(MAIN_URL)
                .build();
        return retrofit;
    }


    public GooglePlacesApiClient getApi() {
        return api;
    }
}
