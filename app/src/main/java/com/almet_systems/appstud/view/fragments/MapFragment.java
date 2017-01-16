package com.almet_systems.appstud.view.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.almet_systems.appstud.R;
import com.almet_systems.appstud.databinding.FragmentMapBinding;
import com.almet_systems.appstud.models.Results;
import com.almet_systems.appstud.view.activities.MainActivity;
import com.almet_systems.appstud.view.base.BaseFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by razir on 1/16/2017.
 */

public class MapFragment extends BaseFragment {
    FragmentMapBinding binding;
    GoogleMap googleMap;

    List<Target> targets = new ArrayList<>();

    public static MapFragment newInstance() {
        Bundle args = new Bundle();
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.menu_map);
        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                MapFragment.this.googleMap = googleMap;
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                List<Results> data = getArguments().getParcelableArrayList("data");
                if (data != null) {
                    addMarkers(data);
                    MainActivity activity = (MainActivity) getActivity();
                    zoomToLocation(activity.getLastLocation());
                }
            }
        });
    }

    private void zoomToLocation(Location location) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 12));
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
    }


    public void setMarker(Results results, Bitmap bitmap) {
        googleMap.addMarker(new MarkerOptions().position(results.getLocation()).icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
    }

    @Override
    public void setData(List<Results> data) {
        getArguments().putParcelableArrayList("data", new ArrayList<>(data));
    }

    private void addMarkers(List<Results> data) {
        googleMap.clear();
        loadAllMarkersImages(data);

    }

    private void updateMarker(Results results, Bitmap bitmap) {
        setMarker(results, bitmap);
    }

    private void loadAllMarkersImages(List<Results> data) {
        for (Results results : data) {
            if (results.getPhotos() != null && !results.getPhotos().isEmpty()) {
                CustomTarget target = new CustomTarget(results);
                targets.add(target);
                Picasso.with(getContext()).load(results.getPhotos().get(0).getPhotoReferenceSmall())
                        .centerCrop()
                        .resizeDimen(R.dimen.marker_size, R.dimen.marker_size)
                        .transform(circle).into(target);
            }
        }
    }

    class CustomTarget implements Target {
        Results results;

        public CustomTarget(Results results) {
            this.results = results;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            targets.remove(this);
            updateMarker(results, bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            targets.remove(this);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    }

    Transformation circle = new RoundedTransformationBuilder()
            .borderColor(Color.BLACK)
            .borderWidthDp(2)
            .oval(true)
            .build();

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory();
    }
}
