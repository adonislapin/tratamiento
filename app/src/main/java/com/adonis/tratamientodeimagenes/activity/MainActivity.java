package com.adonis.tratamientodeimagenes.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import com.adonis.tratamientodeimagenes.R;
import com.adonis.tratamientodeimagenes.activity.base.BaseActivity;
import com.adonis.tratamientodeimagenes.mvp.presenter.MainPresenter;
import com.adonis.tratamientodeimagenes.mvp.view.base.MainView;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity  {

    private MainPresenter presenter;
    private MainView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createPresenter();
        view.init();
    }

    @Override
    protected void createPresenter() {
        ButterKnife.bind(this);
        view = new MainView(this);
        presenter = new MainPresenter(view);
    }

}
