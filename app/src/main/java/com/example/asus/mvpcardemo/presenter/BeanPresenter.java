package com.example.asus.mvpcardemo.presenter;

import android.content.Context;

import com.example.asus.mvpcardemo.mode.app.MyApp;
import com.example.asus.mvpcardemo.view.IViews.IView;


public class BeanPresenter<T extends IView> {
    protected T view;

    public BeanPresenter(T view) {
        this.view = view;
    }

    Context context() {
        if (view != null && view.context() != null) {

        }
        return MyApp.getContext();
    }
}
