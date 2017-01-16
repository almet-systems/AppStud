package com.almet_systems.appstud.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.almet_systems.appstud.App;
import com.almet_systems.appstud.R;
import com.google.gson.annotations.SerializedName;

/**
 * Created by razir on 1/16/2017.
 */

public class Photo implements Parcelable {
    @SerializedName("photo_reference")
    private String photoReference;

    public String getPhotoReference() {
        int width = 800;
        return String.format("https://maps.googleapis.com/maps/api/place/photo?maxwidth=" +
                        "%d&photoreference=%s&key=%s",
                width, photoReference, App.getContext().getResources().getString(R.string.google_places_api_key));
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.photoReference);
    }

    public Photo() {
    }

    protected Photo(Parcel in) {
        this.photoReference = in.readString();
    }

    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
