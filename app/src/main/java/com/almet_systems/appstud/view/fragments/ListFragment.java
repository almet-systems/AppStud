package com.almet_systems.appstud.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.almet_systems.appstud.R;
import com.almet_systems.appstud.databinding.FragmentListBinding;
import com.almet_systems.appstud.models.Results;
import com.almet_systems.appstud.view.activities.MainActivity;
import com.almet_systems.appstud.view.adapter.PlacesAdapter;
import com.almet_systems.appstud.view.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by razir on 1/16/2017.
 */

public class ListFragment extends BaseFragment {
    FragmentListBinding binding;
    PlacesAdapter adapter;

    public static ListFragment newInstance() {
        Bundle args = new Bundle();
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.menu_list);
        List<Results> data = getArguments().getParcelableArrayList("data");
        adapter = new PlacesAdapter(getContext());
        if (data != null) {
            adapter.setData(data);
        }
        binding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rv.setAdapter(adapter);
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MainActivity activity = (MainActivity) getActivity();
                activity.refreshData();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.swipeLayout.setRefreshing(false);
    }

    @Override
    public void setData(List<Results> data) {
        if (adapter != null) {
            adapter.setData(data);
            binding.swipeLayout.setRefreshing(false);
        } else {
            getArguments().putParcelableArrayList("data", new ArrayList<>(data));
        }
    }
}
