package com.almet_systems.appstud.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by razir on 1/16/2017.
 */

public class Photo {
    @SerializedName("photo_reference")
    private String photoReference;

    public String getPhotoReference() {
            return photoReference;
        }
}
