package com.lueinfo.bshop.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.lueinfo.bshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TodaysDealAdapter extends RecyclerView.Adapter<TodaysDealAdapter.ViewHolder>{
    private Context context;
    String[] values;
    Activity context1;
    int count=1;
    View view1;
    String[] items;
    LayoutInflater inflater;
    int image[];
    String name[];
    String MrpPrice[];
    String SalePrice[];
    public List<ItemEntity> productList;
    Context activity1;
    public FragmentManager f_manager;
    FragmentManager manager;



    public TodaysDealAdapter(Context context, List<ItemEntity> productsList, Object o, FragmentManager manager) {
        this.productList=productsList;
        this.manager = manager;
        activity1=context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textView1,textView2,textView3;
        ImageView imageView;

        public LinearLayout button;
        public LinearLayout button_grid;
        public LinearLayout linearLayout,ll_backer;

        public ViewHolder(View v){

            super(v);
            textView1=(TextView)v.findViewById(R.id.imr_text);
            imageView=(ImageView)v.findViewById(R.id.subcat_image);
            textView2=(TextView)v.findViewById(R.id.imr_text2);
            textView3=(TextView)v.findViewById(R.id.imr_text3);
            button=(LinearLayout)v.findViewById(R.id.addItem);

        }

        @Override
        public void onClick(View view) {
           /* String entityId=productList.get(i).getId();
            String image=productList.get(i).getImage();
            Intent intent=new Intent(context,AddtoCartMobile.class);
            intent.putExtra("KeyValue",entityId);
            intent.putExtra("image",image);
            context. startActivity(intent);*/
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more_related_todaysdeal, parent, false);



        return new TodaysDealAdapter.ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder (final ViewHolder holder, final int i){
        holder.textView1.setText(productList.get(i).getName());
        Picasso.with(context).load(productList.get(i).getImage()).into(holder.imageView);
        holder.textView2.setText(productList.get(i).getSalePrice());
        holder.textView3.setText(productList.get(i).getMrpPrice());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* String entityId=productList.get(i).getId();
                String image=productList.get(i).getImage();
                Intent intent=new Intent(context,AddtoCartMobile.class);
                intent.putExtra("KeyValue",entityId);
                intent.putExtra("image",image);
               context. startActivity(intent);*/
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entityId=productList.get(i).getId();
                AddtoCartMobile addtoCartMobile = new AddtoCartMobile();
                Bundle bundle = new Bundle();
                bundle.putString("KeyValue",entityId);
                addtoCartMobile.setArguments(bundle);
                manager.beginTransaction().replace(R.id.container, addtoCartMobile)
                        .commit();


            }
        });

    }

    @Override
    public int getItemCount(){

        return productList.size();
    }

}