package com.almet_systems.appstud.view.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.view.MenuItem;

import com.almet_systems.appstud.R;
import com.almet_systems.appstud.databinding.ActivityMainBinding;
import com.almet_systems.appstud.view.base.BaseActivity;
import com.almet_systems.appstud.view.fragments.ListFragment;
import com.almet_systems.appstud.view.fragments.MapFragment;
import com.almet_systems.appstud.view.utils.FragmentUtils;
import com.almet_systems.appstud.view.utils.SimpleLocationListener;
import com.almet_systems.appstud.view.utils.State;
import com.almet_systems.appstud.view_model.activity.MainActivityViewModel;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by razir on 1/16/2017.
 */

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;
    MapFragment mapFragment;
    ListFragment listFragment;
    MainActivityViewModel viewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        mapFragment = MapFragment.newInstance();
        listFragment = ListFragment.newInstance();
        viewModel = new MainActivityViewModel(this);
        setBaseViewModel(viewModel);
        binding.setViewModel(viewModel);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actionMap:
                        FragmentUtils.changeFragment(MainActivity.this, R.id.contentFrame, mapFragment, false);
                        return true;
                    case R.id.actionList:
                        FragmentUtils.changeFragment(MainActivity.this, R.id.contentFrame, listFragment, false);
                        return true;
                }
                return false;
            }
        });
        FragmentUtils.changeFragment(MainActivity.this, R.id.contentFrame, mapFragment, false);
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
                        }
                    }
                });
    }

}
