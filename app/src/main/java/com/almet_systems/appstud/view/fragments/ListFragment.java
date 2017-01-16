package com.almet_systems.appstud.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.almet_systems.appstud.models.Results;
import com.almet_systems.appstud.view.base.BaseFragment;

import java.util.List;

/**
 * Created by razir on 1/16/2017.
 */

public class ListFragment extends BaseFragment {

    public static ListFragment newInstance() {
        Bundle args = new Bundle();
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setData(List<Results> data) {

    }
}
