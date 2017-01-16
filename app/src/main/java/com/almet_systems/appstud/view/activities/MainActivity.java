package com.almet_systems.appstud.view.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.almet_systems.appstud.R;
import com.almet_systems.appstud.databinding.ActivityMainBinding;
import com.almet_systems.appstud.view.base.BaseActivity;
import com.almet_systems.appstud.view.fragments.ListFragment;
import com.almet_systems.appstud.view.fragments.MapFragment;
import com.almet_systems.appstud.view.utils.FragmentUtils;

/**
 * Created by razir on 1/16/2017.
 */

public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;
    MapFragment mapFragment;
    ListFragment listFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        mapFragment=MapFragment.newInstance();
        listFragment=ListFragment.newInstance();

        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.actionMap:
                        FragmentUtils.changeFragment(MainActivity.this,R.id.contentFrame,mapFragment,false);
                        return true;
                    case R.id.actionList:
                        FragmentUtils.changeFragment(MainActivity.this,R.id.contentFrame,listFragment,false);
                        return true;
                }
                return false;
            }
        });
    }

}
