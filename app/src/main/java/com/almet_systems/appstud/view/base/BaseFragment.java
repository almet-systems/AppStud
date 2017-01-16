package com.almet_systems.appstud.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.almet_systems.appstud.view_model.base.BaseViewModel;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by razir on 1/16/2017.
 */

public abstract class BaseFragment extends Fragment {
    protected BaseViewModel baseViewModel;
    protected CompositeSubscription subscription = new CompositeSubscription();

    @Nullable
    @Override
    public abstract View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (baseViewModel != null)
            baseViewModel.destroy();

        if (subscription != null)
            subscription.unsubscribe();
    }

    protected void setBaseViewModel(BaseViewModel model) {
        this.baseViewModel = model;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
