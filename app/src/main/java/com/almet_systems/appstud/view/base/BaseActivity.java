package com.almet_systems.appstud.view.base;

import android.support.v7.app.AppCompatActivity;

import com.almet_systems.appstud.view_model.base.BaseViewModel;

/**
 * Created by razir on 1/16/2017.
 */

public class BaseActivity extends AppCompatActivity {
    protected BaseViewModel baseViewModel;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (baseViewModel != null) {
            baseViewModel.destroy();
        }
    }


    protected void setBaseViewModel(BaseViewModel model) {
        this.baseViewModel = model;
    }


}
