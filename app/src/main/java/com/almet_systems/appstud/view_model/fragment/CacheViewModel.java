package com.almet_systems.appstud.view_model.fragment;

import android.content.Context;

import com.almet_systems.appstud.models.Results;
import com.almet_systems.appstud.view_model.base.BaseViewModel;

import java.util.List;

/**
 * Created by razir on 1/16/2017.
 */

public class CacheViewModel extends BaseViewModel {

    private List<Results> data;

    public CacheViewModel(Context context) {
        super(context);
    }

    public List<Results> getData() {
        return data;
    }

    public void setData(List<Results> data) {
        this.data = data;
    }
}
