package com.example.asus.mvpcardemo.presenter;

import com.example.asus.mvpcardemo.mode.bane.GetGoodsCarBeanMode;
import com.example.asus.mvpcardemo.mode.bean.CarBean;
import com.example.asus.mvpcardemo.view.IViews.GoodsSelectCarView;


public class GetGoodsCartPresenter extends BeanPresenter<GoodsSelectCarView>{

    private GetGoodsCarBeanMode getGoodsCarBeanMode;
    public GetGoodsCartPresenter(GoodsSelectCarView view) {
        super(view);
        getGoodsCarBeanMode = new  GetGoodsCarBeanMode();
    }

    public void getCarData(){
        getGoodsCarBeanMode.getCarData(new GetGoodsCarBeanMode.DataCarBack() {
            @Override
            public void onGetDataSucceed(CarBean bean) {
                view.onCartSelectSucceed(bean);
            }

            @Override
            public void onGetDataFail(String s) {
                view.onCartSelectFail(s);
            }
        });
    }
}
