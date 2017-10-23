package com.example.asus.mvpcardemo.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.mvpcardemo.R;
import com.example.asus.mvpcardemo.mode.bean.CarBean;
import com.example.asus.mvpcardemo.presenter.GetGoodsCartPresenter;
import com.example.asus.mvpcardemo.view.IViews.GoodsSelectCarView;
import com.example.asus.mvpcardemo.view.adapter.CartAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class
MainActivity extends AppCompatActivity implements CartAdapter.RefreshPriceInterface, View.OnClickListener, GoodsSelectCarView {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.all_chekbox)
    CheckBox cb_check_all;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;
    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.tv_go_to_pay)
    TextView tv_go_to_pay;

    private CartAdapter adapter;

    private double totalPrice = 0.00;
    private int totalCount = 0;
    private List<HashMap<String, String>> goodsList = new ArrayList<>();
    private List<CarBean.DataBean.ListBean> mlist = new ArrayList<>();
    private List<CarBean.DataBean> list = new ArrayList<>();

    GetGoodsCartPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new GetGoodsCartPresenter(this);
        presenter.getCarData();

    }


    @Override
    public Context context() {
        return this;
    }

    @Override
    public void onCartSelectSucceed(CarBean bean) {
        list.addAll(bean.getData());
        for (CarBean.DataBean dataBean : list) {
            mlist.addAll(dataBean.getList());
        }
        for (int i = 0; i < mlist.size(); i++) {
            CarBean.DataBean.ListBean listBean = mlist.get(i);
            HashMap<String, String> map = new HashMap<>();
            map.put("id", listBean.getPid() + "");
            map.put("name", listBean.getTitle());
            map.put("type", (i + 20) + "码");
            map.put("price", listBean.getPrice() + "");
            map.put("count", 1+"");
            map.put("image", listBean.getImages());
            goodsList.add(map);

        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initView();
            }
        });

    }

    @Override
    public void onCartSelectFail(String string) {

    }

    private void initView() {
        tv_go_to_pay.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        cb_check_all.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter(this, goodsList);
        adapter.setRefreshPriceInterface(this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    //控制价格展示
    private void priceControl(Map<String, Integer> pitchOnMap) {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < goodsList.size(); i++) {
            if (pitchOnMap.get(goodsList.get(i).get("id")) == 1) {
                totalCount = totalCount + Integer.valueOf(goodsList.get(i).get("count"));
                double goodsPrice = Integer.valueOf(goodsList.get(i).get("count")) * Double.valueOf(goodsList.get(i).get("price"));
                totalPrice = totalPrice + goodsPrice;
            }
        }
        tv_total_price.setText("￥ " + totalPrice);
        tv_go_to_pay.setText("付款(" + totalCount + ")");
    }


    public void refreshPrice(Map<String, Integer> pitchOnMap) {
        priceControl(pitchOnMap);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.all_chekbox:
                AllTheSelected();
                break;
            case R.id.tv_go_to_pay:
                if (totalCount <= 0) {
                    Toast.makeText(this, "请选择要付款的商品~", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "钱就是另一回事了~", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_delete:
                if (totalCount <= 0) {
                    Toast.makeText(this, "请选择要删除的商品~", Toast.LENGTH_SHORT).show();
                    return;
                }
                checkDelete(adapter.getPitchOnMap());
                break;
        }
    }

    //删除操作
    private void checkDelete(Map<String, Integer> map) {
        List<HashMap<String, String>> waitDeleteList = new ArrayList<>();
        Map<String, Integer> waitDeleteMap = new HashMap<>();
        for (int i = 0; i < goodsList.size(); i++) {
            if (map.get(goodsList.get(i).get("id")) == 1) {
                waitDeleteList.add(goodsList.get(i));
                waitDeleteMap.put(goodsList.get(i).get("id"), map.get(goodsList.get(i).get("id")));
            }
        }
        goodsList.removeAll(waitDeleteList);
        map.remove(waitDeleteMap);
        priceControl(map);
        adapter.notifyDataSetChanged();
    }

    //全选或反选
    private void AllTheSelected() {
        Map<String, Integer> map = adapter.getPitchOnMap();
        boolean isCheck = false;
        boolean isUnCheck = false;
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            if (Integer.valueOf(entry.getValue().toString()) == 1) isCheck = true;
            else isUnCheck = true;
        }
        if (isCheck == true && isUnCheck == false) {//已经全选,做反选
            for (int i = 0; i < goodsList.size(); i++) {
                map.put(goodsList.get(i).get("id"), 0);
            }
            cb_check_all.setChecked(false);
        } else if (isCheck == true && isUnCheck == true) {//部分选择,做全选
            for (int i = 0; i < goodsList.size(); i++) {
                map.put(goodsList.get(i).get("id"), 1);
            }
            cb_check_all.setChecked(true);
        } else if (isCheck == false && isUnCheck == true) {//一个没选,做全选
            for (int i = 0; i < goodsList.size(); i++) {
                map.put(goodsList.get(i).get("id"), 1);
            }
            cb_check_all.setChecked(true);
        }
        priceControl(map);
        adapter.setPitchOnMap(map);
        adapter.notifyDataSetChanged();
    }
}
