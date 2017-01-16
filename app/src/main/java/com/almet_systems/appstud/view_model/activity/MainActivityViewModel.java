package com.almet_systems.appstud.view_model.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.ObservableInt;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import com.almet_systems.appstud.R;
import com.almet_systems.appstud.api.GooglePlacesApiService;
import com.almet_systems.appstud.api.NetworkUtils;
import com.almet_systems.appstud.models.PlaceResponse;
import com.almet_systems.appstud.models.Results;
import com.almet_systems.appstud.view.utils.SimpleLocationListener;
import com.almet_systems.appstud.view.utils.State;
import com.almet_systems.appstud.view_model.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by razir on 1/16/2017.
 */

public class MainActivityViewModel extends BaseViewModel {
    static final int SEARCH_RADIUS_METERS=50000;
    private static final String RANK_BY = "distance";
    private static final int LOCATION_TIMEOUT = 15000;
    Timer timer;
    List<Results> results = null;

    public ObservableInt state = new ObservableInt();

    Location lastLocation;

    public MainActivityViewModel(Context context) {
        super(context);
    }

    public void loadData(Location location) {
        lastLocation = location;
        Subscriber<PlaceResponse> subscriber = new Subscriber<PlaceResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                state.set(State.STATE_ERROR_NETWORK);
                e.printStackTrace();
            }

            @Override
            public void onNext(PlaceResponse response) {
                state.set(State.STATE_DATA_LOADED);
                results=response.getResults();
            }
        };

        subscription.add(subscriber);
        Observable<PlaceResponse> observable = GooglePlacesApiService.getInstance().getApi()
                .getPlace((location.getLatitude() + "," +location.getLongitude()),SEARCH_RADIUS_METERS,
                        getContext().getString(R.string.google_places_api_key),"Bar",RANK_BY);
        if (NetworkUtils.isNetworkAvailable(getContext())) {
            if (results==null) {
                state.set(State.STATE_LOADING);
            }
            observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);

        } else {
           setState(State.STATE_ERROR_NETWORK);
        }


    }

    public void setState(int state) {
        this.state.set(state);
    }

    public void getLocation() {
        LocationManager lm = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            setupTimeout();
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, locationListener);
        } else if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            setupTimeout();
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 0, locationListener);
        } else {
            setState(State.STATE_ERROR_NO_PROVIDER);
        }
    }

    public List<Results> getResults() {
        return results;
    }

    @Override
    public void destroy() {
        super.destroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    private void setupTimeout() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                timer = null;
                LocationManager lm = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
                lm.removeUpdates(locationListener);
                setState(State.STATE_ERROR_NO_LOCATION);
            }
        }, LOCATION_TIMEOUT);
    }

    SimpleLocationListener locationListener = new SimpleLocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            super.onLocationChanged(location);
            LocationManager lm = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
            lm.removeUpdates(locationListener);
            loadData(location);
        }
    };

}
