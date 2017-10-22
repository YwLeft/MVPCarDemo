package com.example.asus.mvpcardemo.mode.app;

import android.app.Application;
import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class MyApp extends Application{
    private static Context context;
    private static OkHttpClient okHttpClient;



    @Override
    public void onCreate() {
        super.onCreate();

        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static void setOkHttpClient(OkHttpClient okHttpClient) {
        MyApp.okHttpClient = okHttpClient;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MyApp.context = context;
    }
}
