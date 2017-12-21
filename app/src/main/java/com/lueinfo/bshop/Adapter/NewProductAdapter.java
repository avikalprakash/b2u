package com.lueinfo.bshop.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.lueinfo.bshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lue on 08-12-2017.
 */

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.NewProductHolder>{

    Context context;
    List<ItemEntity> list;
public NewProductAdapter(Context con, List<ItemEntity> list){
    this.list=list;
    context=con;
}
    @Override
    public NewProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflate= LayoutInflater.from(context);
        View v=inflate.inflate(R.layout.homemain_adptorsecond,null);
       return new NewProductHolder(v);

    }

    @Override
    public void onBindViewHolder(NewProductHolder holder, int position) {
    ItemEntity entity=list.get(position);
        holder.textView.setText(entity.getProductPromotionName());
        holder.textmrp.setText(entity.getMrpPrice());
        holder.textsale.setText(entity.getSalePrice());

        String sale=entity.getSalePrice();
        if(!sale.equals("null")){
            holder.textmrp.setTextColor(Color.BLACK);
            holder.textsale.setVisibility(View.VISIBLE);
            holder. textmrp.setPaintFlags( Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        }
        Picasso.with(context).load(entity.getProductPromotionImage()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class NewProductHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView,textsale,textmrp;
        public NewProductHolder(View itemView) {
            super(itemView);
            imageView=(ImageView) itemView.findViewById(R.id.imgl2);
            textView=(TextView) itemView.findViewById(R.id.item_namel2);
            textsale=(TextView) itemView.findViewById(R.id.pri_list2);
            textmrp=(TextView) itemView.findViewById(R.id.dep_text2) ;
        }
    }
}
