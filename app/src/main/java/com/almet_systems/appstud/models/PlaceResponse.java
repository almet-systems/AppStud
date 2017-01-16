package com.almet_systems.appstud.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by razir on 1/16/2017.
 */

public class PlaceResponse {
    @SerializedName("results")
    private List<Results> results;

    public List<Results> getResults() {
        return results;
    }

}
