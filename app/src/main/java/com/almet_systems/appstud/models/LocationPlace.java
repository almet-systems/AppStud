package com.almet_systems.appstud.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by razir on 1/16/2017.
 */

public class LocationPlace implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.lat);
        dest.writeValue(this.lng);
    }

    public LocationPlace() {
    }

    protected LocationPlace(Parcel in) {
        this.lat = (Double) in.readValue(Double.class.getClassLoader());
        this.lng = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<LocationPlace> CREATOR = new Parcelable.Creator<LocationPlace>() {
        @Override
        public LocationPlace createFromParcel(Parcel source) {
            return new LocationPlace(source);
        }

        @Override
        public LocationPlace[] newArray(int size) {
            return new LocationPlace[size];
        }
    };
}
