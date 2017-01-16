package com.almet_systems.appstud.view.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import com.almet_systems.appstud.R;
import com.almet_systems.appstud.databinding.ActivityMainBinding;
import com.almet_systems.appstud.models.Results;
import com.almet_systems.appstud.view.base.BaseActivity;
import com.almet_systems.appstud.view.base.BaseFragment;
import com.almet_systems.appstud.view.fragments.ListFragment;
import com.almet_systems.appstud.view.fragments.MapFragment;
import com.almet_systems.appstud.view.utils.FragmentUtils;
import com.almet_systems.appstud.view.utils.SimpleLocationListener;
import com.almet_systems.appstud.view.utils.State;
import com.almet_systems.appstud.view_model.activity.MainActivityViewModel;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by razir on 1/16/2017.
 */

public class MainActivity extends BaseActivity implements MainActivityViewModel.ActionListener {

    private static final int LOCATION_SETTINGS = 45;

    ActivityMainBinding binding;
    MapFragment mapFragment;
    ListFragment listFragment;
    MainActivityViewModel viewModel;
    int currentTab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mapFragment = MapFragment.newInstance();
        listFragment = ListFragment.newInstance();
        viewModel = new MainActivityViewModel(this, this);
        setBaseViewModel(viewModel);
        binding.setViewModel(viewModel);
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                currentTab = item.getItemId();
                switch (item.getItemId()) {
                    case R.id.actionMap:
                        mapFragment.setData(viewModel.getResults());
                        FragmentUtils.changeFragment(MainActivity.this, R.id.contentFrame, mapFragment, false);
                        return true;
                    case R.id.actionList:
                        listFragment.setData(viewModel.getResults());
                        FragmentUtils.changeFragment(MainActivity.this, R.id.contentFrame, listFragment, false);
                        return true;
                }
                return false;
            }
        });
        binding.btnError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resolveError();
            }
        });
        refreshData();
    }

    public void refreshData() {
        checkPermission();
    }

    private void checkPermission() {
        new RxPermissions(this)
                .request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            viewModel.getLocation();
                        } else {
                            viewModel.setState(State.STATE_ERROR_PERMISSIONS);
                        }
                    }
                });
    }

    private void resolveError() {
        int state = viewModel.getCurrentState();
        switch (state) {
            case State.STATE_ERROR_PERMISSIONS:
                checkPermission();
                break;
            case State.STATE_ERROR_NO_PROVIDER:
                openDeviceLocationSettings();
                break;
            case State.STATE_ERROR_NETWORK:
                viewModel.loadLastLocation();
                break;
            case State.STATE_ERROR_NO_LOCATION:
                viewModel.getLocation();
                break;
        }
    }

    private void openDeviceLocationSettings() {
        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), LOCATION_SETTINGS);
    }

    public Location getLastLocation() {
        return viewModel.getLastLocation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        viewModel.getLocation();
    }

    @Override
    public void onDataRefreshed(List<Results> results) {
        if (currentTab != 0) {
            if (currentTab == R.id.actionList) {
                listFragment.setData(results);
            } else {
                mapFragment.setData(results);
            }
        } else {
            mapFragment.setData(results);
            FragmentUtils.changeFragment(this, R.id.contentFrame, mapFragment, false);
        }
    }
}
