package com.almet_systems.appstud.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by razir on 1/16/2017.
 */

public class Geometry {
    @SerializedName("location")
    private LocationPlace locationPlace;

    public LocationPlace getLocationPlace() {
        return locationPlace;
    }
}
