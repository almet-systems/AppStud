package com.almet_systems.appstud.models;


import com.google.gson.annotations.SerializedName;

/**
 * Created by razir on 1/16/2017.
 */

public class LocationPlace {
    @SerializedName("lat")
    private Double lat;

    @SerializedName("lng")
    private Double lng;
    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }
}
