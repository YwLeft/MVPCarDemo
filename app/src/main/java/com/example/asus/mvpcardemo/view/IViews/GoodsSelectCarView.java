package com.example.asus.mvpcardemo.view.IViews;

import com.example.asus.mvpcardemo.mode.bean.CarBean;

/**
 * 创建时间  2017/10/20 19:56
 * 创建人    gaozhijie
 * 类描述
 */
public interface GoodsSelectCarView extends IView{
    //查询成功
    void onCartSelectSucceed(CarBean bean);
    //查询失败
    void onCartSelectFail(String string);

}
