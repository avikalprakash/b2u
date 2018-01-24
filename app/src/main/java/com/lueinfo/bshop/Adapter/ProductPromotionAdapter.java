package com.lueinfo.bshop.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lueinfo.bshop.MainActivity;
import com.lueinfo.bshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lue on 08-12-2017.
 */

public class ProductPromotionAdapter extends RecyclerView.Adapter<ProductPromotionAdapter.ProductviewHolerr> {

private List<ItemEntity> list;
private Context context;
    ItemEntity entity;
    MainActivity main;
public ProductPromotionAdapter(Context con, List<ItemEntity> list){
    this.list=list;
    context=con;
}
    @Override
    public ProductviewHolerr onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.homemain_adptorsecond,null);
        return new ProductviewHolerr(v,context,list);
    }

    @Override
    public void onBindViewHolder(ProductviewHolerr holder, int position) {
  entity=list.get(position);
holder.textView.setText(entity.getProductPromotionName());
holder.textmrp.setText(entity.getMrpPrice());
holder.textsale.setText(entity.getSalePrice());

        String sale=entity.getSalePrice();
        try {
            if (!sale.equals("null")) {
                holder.textmrp.setTextColor(Color.BLACK);
                holder.textsale.setVisibility(View.VISIBLE);
                holder.textmrp.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            }
        }catch (Exception e){}
        Picasso.with(context).load(entity.getProductPromotionImage()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class  ProductviewHolerr extends RecyclerView.ViewHolder {

        ImageView imageView;
        CardView card;
        TextView textView,textsale,textmrp;
        List<ItemEntity> list1;
        Context ctx;
       public ProductviewHolerr(View itemView, Context ctx, List<ItemEntity> entity1) {
           super(itemView);

          list1=list;
          ctx=context;


           imageView=(ImageView) itemView.findViewById(R.id.imgl2);
           textView=(TextView) itemView.findViewById(R.id.item_namel2);
           textsale=(TextView) itemView.findViewById(R.id.pri_list2);
           textmrp=(TextView) itemView.findViewById(R.id.price_text2) ;
           card=(CardView)itemView.findViewById(R.id.cardview);
       }



    }
}
