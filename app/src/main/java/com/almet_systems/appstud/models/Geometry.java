package com.almet_systems.appstud.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by razir on 1/16/2017.
 */

public class Geometry implements Parcelable {
    @SerializedName("location")
    private LocationPlace locationPlace;

    public LocationPlace getLocationPlace() {
        return locationPlace;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.locationPlace, flags);
    }

    public Geometry() {
    }

    protected Geometry(Parcel in) {
        this.locationPlace = in.readParcelable(LocationPlace.class.getClassLoader());
    }

    public static final Parcelable.Creator<Geometry> CREATOR = new Parcelable.Creator<Geometry>() {
        @Override
        public Geometry createFromParcel(Parcel source) {
            return new Geometry(source);
        }

        @Override
        public Geometry[] newArray(int size) {
            return new Geometry[size];
        }
    };
}
