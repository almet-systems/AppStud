package com.almet_systems.appstud.view.utils;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by razir on 1/16/2017.
 */

public class FragmentUtils {

    private static final String FRAGMENT_TAG="Content";

    public static void changeFragment(FragmentActivity activity, int contentFrame,
                                      Fragment fr, boolean addToBackStack){
        FragmentTransaction ft=activity.getSupportFragmentManager().beginTransaction();
        ft.replace(contentFrame, fr, FRAGMENT_TAG);
        if(addToBackStack){
            ft.addToBackStack(null);
        }

        ft.commitAllowingStateLoss();
    }

    public static Fragment getCurrentFragment(AppCompatActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        int stackCount = fragmentManager.getBackStackEntryCount();
        if( fragmentManager.getFragments() != null ) return fragmentManager.getFragments().get( stackCount > 0 ? stackCount-1 : stackCount );
        else return null;
    }


}
