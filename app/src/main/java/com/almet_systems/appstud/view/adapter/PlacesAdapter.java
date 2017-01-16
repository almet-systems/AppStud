package com.almet_systems.appstud.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.almet_systems.appstud.databinding.ItemPlaceBinding;
import com.almet_systems.appstud.models.Results;
import com.almet_systems.appstud.view_model.item.ItemPlaceViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by razir on 1/16/2017.
 */

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.Holder> {
    List<Results> data;
    WeakReference<LayoutInflater> inflater;

    public PlacesAdapter(Context context) {
        this.data = new ArrayList<>();
        this.inflater = new WeakReference<>(LayoutInflater.from(context));
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(ItemPlaceBinding.inflate(inflater.get(), parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind(data.get(position));
    }

    public void setData(List<Results> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ItemPlaceBinding binding;

        public Holder(ItemPlaceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Results results) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ItemPlaceViewModel(inflater.get().getContext(), results));
            } else {
                binding.getViewModel().bind(results);
            }
            binding.executePendingBindings();
        }
    }
}
