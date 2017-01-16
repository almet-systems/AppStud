package com.almet_systems.appstud.view_model.base;

import android.content.Context;

import java.lang.ref.WeakReference;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by razir on 1/16/2017.
 */

public abstract class BaseViewModel {

    protected WeakReference<Context> context;
    protected CompositeSubscription subscription = new CompositeSubscription();

    public BaseViewModel(Context context) {
        this.context = new WeakReference<>(context);
    }

    public void destroy() {
        context = null;
        subscription.unsubscribe();
    }

    protected Context getContext() {
        return context.get();
    }


}
