package com.almet_systems.appstud.view.utils;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

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

    public static void popBackStack(FragmentActivity activity){
        FragmentManager fm=activity.getSupportFragmentManager();
        for(int i=0;i<fm.getBackStackEntryCount();++i){
            fm.popBackStack();
        }
    }
}
