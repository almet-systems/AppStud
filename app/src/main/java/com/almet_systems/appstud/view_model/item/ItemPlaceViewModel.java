package com.almet_systems.appstud.view_model.item;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableShort;

import com.almet_systems.appstud.models.Results;
import com.almet_systems.appstud.view_model.base.BaseViewModel;

/**
 * Created by razir on 1/16/2017.
 */

public class ItemPlaceViewModel extends BaseViewModel {

    public ObservableField<String> image = new ObservableField<>();
    public ObservableField<String> title = new ObservableField<>();

    public ItemPlaceViewModel(Context context,Results results) {
        super(context);
        bind(results);
    }

    public void bind(Results results) {
        title.set(results.getName());
        if (results.getPhotos() != null&&!results.getPhotos().isEmpty()) {
            image.set(results.getPhotos().get(0).getPhotoReference());
        }
    }
}
