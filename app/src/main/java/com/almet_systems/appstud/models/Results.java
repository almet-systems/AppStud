package com.almet_systems.appstud.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by razir on 1/16/2017.
 */

public class Results {
    @SerializedName("geometry")
    private Geometry geometry;

    @SerializedName("name")
    private String name;

    @SerializedName("vicinity")
    private String vicinity;

    @SerializedName("photos")
    private List<Photo> photos;

    public LatLng getLocation(){
        return new LatLng(geometry.getLocationPlace().getLat(),geometry.getLocationPlace().getLng());
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public String getName() {
        return name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public Results() {
    }
}
