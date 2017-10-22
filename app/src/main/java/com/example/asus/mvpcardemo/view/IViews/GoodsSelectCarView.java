package com.example.asus.mvpcardemo.view.IViews;

import com.example.asus.mvpcardemo.mode.bean.CarBean;


public interface GoodsSelectCarView extends IView{
    //查询成功
    void onCartSelectSucceed(CarBean bean);
    //查询失败
    void onCartSelectFail(String string);

}
