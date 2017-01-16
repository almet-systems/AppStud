package com.almet_systems.appstud.view.fragments;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.almet_systems.appstud.databinding.FragmentMapBinding;
import com.almet_systems.appstud.models.Results;
import com.almet_systems.appstud.view.base.BaseFragment;
import com.almet_systems.appstud.view_model.fragment.FragmentMapViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by razir on 1/16/2017.
 */

public class MapFragment extends BaseFragment {
    FragmentMapBinding binding;
    FragmentMapViewModel viewModel;
    GoogleMap googleMap;

    Map<Results, Marker> markers = new HashMap<>();

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
        viewModel = new FragmentMapViewModel(getContext());
        setBaseViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                MapFragment.this.googleMap = googleMap;
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                if (viewModel.getData() != null) {
                    setData(viewModel.getData());
                }
            }
        });
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


    public void setMarker(Results results) {
        Marker marker = googleMap.addMarker(new MarkerOptions().position(results.getLocation()).draggable(false));
        markers.put(results, marker);
    }

    @Override
    public void setData(List<Results> data) {
        if (googleMap != null) {
            googleMap.clear();
            markers.clear();
            for (Results results : data) {
                setMarker(results);
            }
        } else {
            viewModel.setData(data);
        }
    }

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
