package com.adonis.tratamientodeimagenes.mvp.view.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.view.inputmethod.InputMethodManager;

import com.adonis.tratamientodeimagenes.activity.base.BaseActivity;
import com.adonis.tratamientodeimagenes.utils.NetworkUtils;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import timber.log.Timber;

public class ActivityView<T extends BaseActivity, HV, HA>   {
    public static final String EMPTY_STRING = "";
    public static final int ZERO_INT = 0;

    protected Observer<HV> viewObserver;
    protected Observer<HA> adapterObserver;

    private WeakReference<T> activityRef;

    public ActivityView(T activity) {
        activityRef = new WeakReference<>(activity);
    }

    @Nullable
    public T getActivity() {
        return activityRef.get();
    }

    @Nullable
    public Context getContext() {
        return getActivity();
    }

    @Nullable
    public FragmentManager getFragmentManager() {
        T activity = getActivity();
        return (activity != null) ? activity.getSupportFragmentManager() : null;
    }

    public void showMessage(@StringRes int message) {
        final T activity = getActivity();
        if (activity == null) {
            return;
        }
        showMessage(activity.getString(message));
    }

    public void showMessage(String message) {
        Timber.d("Toast: %s", message);
    }

    public Observer<HA> getAdapterObserver() {
        return this.adapterObserver;
    }

    public void setAdapterObserver(Observer<HA> observer) {
        this.adapterObserver = observer;
    }

    public Observer<HV> getViewObserver() {
        return this.viewObserver;
    }

    public void setViewObserver(Observer<HV> observer) {
        this.viewObserver = observer;
    }

    public void hideKeyboard() {
        final T activity = getActivity();
        if (activity == null || activity.getCurrentFocus() == null)
            return;
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void showKeyboard() {
        final T activity = getActivity();
        if (activity == null || activity.getCurrentFocus() == null)
            return;
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, ZERO_INT);
        }
    }

    @NonNull
    public String getStringFromResource(final int resourceId) {
        final Context context = getContext();
        if (context == null) {
            return EMPTY_STRING;
        }
        return context.getString(resourceId);
    }

    @NonNull
    public String getStringFromResource(final int resourceId, @NonNull final Object... params) {
        final Context context = getContext();
        if (context == null) {
            return EMPTY_STRING;
        }
        return context.getString(resourceId, params);
    }

    public boolean isConnectedToNetwork() {
        final Context context = getContext();
        return context != null && NetworkUtils.isConnected(context);
    }

}
