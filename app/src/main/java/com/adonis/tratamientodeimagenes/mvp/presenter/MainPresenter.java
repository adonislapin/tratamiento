package com.adonis.tratamientodeimagenes.mvp.presenter;

import android.support.annotation.NonNull;

import com.adonis.tratamientodeimagenes.mvp.view.MainView;

public class MainPresenter {

    private MainView view;

    public MainPresenter(@NonNull MainView view){
        this.view = view;
    }
}
