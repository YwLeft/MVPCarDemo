package com.example.asus.mvpcardemo.mode.app;

import android.app.Application;
import android.content.Context;

import org.xutils.BuildConfig;
import org.xutils.x;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 创建时间  2017/10/20 20:00
 * 创建人    gaozhijie
 * 类描述
 */
public class MyApp extends Application{
    private static Context context;
    private static OkHttpClient okHttpClient;



    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

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
