package com.example.asus.mvpcardemo.mode.bane;

import com.example.asus.mvpcardemo.mode.app.MyApp;
import com.example.asus.mvpcardemo.mode.bean.CarBean;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建时间  2017/10/20 20:00
 * 创建人    gaozhijie
 * 类描述
 */
public class GetGoodsCarBeanMode {

    String path = "http://120.27.23.105/product/getCarts?uid=149";
    OkHttpClient okHttpClient = new OkHttpClient();

    public GetGoodsCarBeanMode() {
        okHttpClient = MyApp.getOkHttpClient();
    }
    public void getCarData(final DataCarBack dataCarBack){
        final Request request = new Request.Builder()
                .url(path)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dataCarBack.onGetDataFail(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json =response.body().string();
                Gson gson = new Gson();
                if(response.isSuccessful()){
                    CarBean carBean = gson.fromJson(json,CarBean.class);
                    dataCarBack.onGetDataSucceed(carBean);

                }

            }
        });

    }
    public interface DataCarBack{
        void onGetDataSucceed(CarBean bean);
        void onGetDataFail(String s);
    }
}
