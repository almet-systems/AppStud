package com.almet_systems.appstud.api;

import com.almet_systems.appstud.models.PlaceResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by razir on 1/16/2017.
 */

public interface GooglePlacesApiClient {

    @GET("nearbysearch/json")
    Observable<PlaceResponse> getPlace(@Query("location") String location, @Query("radius") int radius, @Query("key") String key,
                                       @Query("keyword") String keyword, @Query("rankBy")String rankBy);
}
