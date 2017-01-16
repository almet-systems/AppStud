package com.almet_systems.appstud;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.maps.MapsInitializer;


public class App extends Application {
    static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        MapsInitializer.initialize(getContext());
    }

    public static Context getContext() {
        return context;
    }
}
