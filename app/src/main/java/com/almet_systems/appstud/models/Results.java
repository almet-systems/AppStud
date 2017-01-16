package com.almet_systems.appstud.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razir on 1/16/2017.
 */

public class Results implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.geometry, flags);
        dest.writeString(this.name);
        dest.writeString(this.vicinity);
        dest.writeList(this.photos);
    }

    protected Results(Parcel in) {
        this.geometry = in.readParcelable(Geometry.class.getClassLoader());
        this.name = in.readString();
        this.vicinity = in.readString();
        this.photos = new ArrayList<Photo>();
        in.readList(this.photos, Photo.class.getClassLoader());
    }

    public static final Parcelable.Creator<Results> CREATOR = new Parcelable.Creator<Results>() {
        @Override
        public Results createFromParcel(Parcel source) {
            return new Results(source);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[size];
        }
    };
}
