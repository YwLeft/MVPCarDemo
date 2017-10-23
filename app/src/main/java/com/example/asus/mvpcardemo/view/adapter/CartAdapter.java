package com.example.asus.mvpcardemo.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.mvpcardemo.R;
import com.example.asus.mvpcardemo.view.AmountView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private List<HashMap<String,String>> dataList;
    private Map<String,Integer> pitchOnMap;
    private RefreshPriceInterface refreshPriceInterface;

    public CartAdapter(Context context, List<HashMap<String, String>> goodsList) {
        this.context=context;
        this.dataList=goodsList;

        pitchOnMap=new HashMap<>();
        for(int i=0;i<dataList.size();i++){
            pitchOnMap.put(dataList.get(i).get("id"),0);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(pitchOnMap.get(dataList.get(position).get("id"))==0)holder.checkBox.setChecked(false);
        else holder.checkBox.setChecked(true);
        HashMap<String,String> map=dataList.get(position);
        String[] images = map.get("image").split("\\|");
        Glide.with(context).load(images[0]).into(holder.icon);
        holder.name.setText(map.get("name").substring(0,20));
        holder.num.setText(map.get("count"));
        holder.type.setText(map.get("type"));
        holder.price.setText("￥ "+(Double.valueOf(map.get("price")) * Integer.valueOf(map.get("count"))));

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int index=position;
                if(((CheckBox)view).isChecked())pitchOnMap.put(dataList.get(index).get("id"),1);else pitchOnMap.put(dataList.get(index).get("id"),0);
                refreshPriceInterface.refreshPrice(pitchOnMap);
            }
        });

        holder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int index=position;
                dataList.get(index).put("count",(Integer.valueOf(dataList.get(index).get("count"))-1)+"");
                if(Integer.valueOf(dataList.get(index).get("count"))<=0){
                    //可提示是否删除该商品,确定就remove,否则就保留1
                    String deID=dataList.get(index).get("id");
                    dataList.remove(index);
                    pitchOnMap.remove(deID);
                }
                notifyDataSetChanged();
                refreshPriceInterface.refreshPrice(pitchOnMap);
            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int index=position;
                dataList.get(index).put("count",(Integer.valueOf(dataList.get(index).get("count"))+1)+"");
                if(Integer.valueOf(dataList.get(index).get("count"))>15){
                    //15为库存可选择上限
                    Toast.makeText(context,"已达库存上限~",Toast.LENGTH_SHORT).show();
                    return;
                }
                notifyDataSetChanged();
                refreshPriceInterface.refreshPrice(pitchOnMap);
            }
        });
    }
    public Map<String,Integer> getPitchOnMap(){
        return pitchOnMap;
    }
    public void setPitchOnMap(Map<String,Integer> pitchOnMap){
        this.pitchOnMap=new HashMap<>();
        this.pitchOnMap.putAll(pitchOnMap);
    }

    public interface RefreshPriceInterface{
        void refreshPrice(Map<String, Integer> pitchOnMap);
    }
    public void setRefreshPriceInterface(RefreshPriceInterface refreshPriceInterface){
        this.refreshPriceInterface=refreshPriceInterface;
    }



    @Override
    public int getItemCount() {
        if (dataList != null) {
            return dataList.size();
        } else {
            return 0;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        ImageView icon;
        TextView name,price,type;
        AmountView amountView;
        private EditText num;
        private Button reduce;
        private Button add;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox=(CheckBox)itemView.findViewById(R.id.check_box);
            icon=(ImageView)itemView.findViewById(R.id.iv_adapter_list_pic);
            name=(TextView)itemView.findViewById(R.id.tv_goods_name);
            price=(TextView)itemView.findViewById(R.id.tv_goods_price);
            type=(TextView)itemView.findViewById(R.id.tv_type_size);
            amountView = (AmountView) itemView.findViewById(R.id.amount_view);
            num= (EditText) amountView.findViewById(R.id.etAmount);
            reduce= (Button) amountView.findViewById(R.id.btnDecrease);
            add= (Button) amountView.findViewById(R.id.btnIncrease);

        }
    }
}
