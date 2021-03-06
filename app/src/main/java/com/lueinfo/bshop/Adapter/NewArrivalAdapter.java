package com.lueinfo.bshop.Adapter;

import android.content.Context;
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

public class NewArrivalAdapter extends RecyclerView.Adapter<NewArrivalAdapter.Categoryholder>  {
Context context;
List<ItemEntity> list;
public NewArrivalAdapter(Context con, List<ItemEntity> list){
    context=con;
    this.list=list;
}
    @Override
    public Categoryholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.homemain_adaptor,null);
        return new Categoryholder(v);
    }

    @Override
    public void onBindViewHolder(Categoryholder holder, int position) {
    ItemEntity entity=list.get(position);
        holder.txtTitle.setText(entity.getName());
        holder.price_text2.setText(entity.getPrice());
        Picasso.with(context).load(entity.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Categoryholder extends RecyclerView.ViewHolder{
        TextView txtTitle;
        TextView price_text2;
        ImageView image;
        public Categoryholder(View itemView) {
            super(itemView);
            txtTitle=(TextView)itemView.findViewById(R.id.item_namel2);
            price_text2=(TextView)itemView.findViewById(R.id.price_text2);
            image=(ImageView)itemView.findViewById(R.id.imgl2);
        }
    }
}
