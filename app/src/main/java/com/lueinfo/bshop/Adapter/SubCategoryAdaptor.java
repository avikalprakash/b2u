package com.lueinfo.bshop.Adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.lueinfo.bshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SubCategoryAdaptor extends BaseAdapter {
    LayoutInflater mInflater;
    private Activity context;
    private ArrayList<ItemEntity> items=new ArrayList<ItemEntity>();

    public SubCategoryAdaptor(FragmentActivity activity, ArrayList<ItemEntity> subcotegory_list) {
        this.context = activity;
        this.items=subcotegory_list;
    }

    @Override
    public int getCount() {

      return  items.size();
    }

    @Override
    public Object getItem(int i) {

        return null;
    }

    @Override
    public long getItemId(int i) {

        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.adaptor_subcategory, null);
        TextView txtTitle = (TextView) view
                .findViewById(R.id.subcot_item_text);
        ImageView Subcat_imageView=(ImageView)view.findViewById(R.id.subcat_image) ;
        txtTitle.setText(items.get(i).getName());
        Picasso.with(context).load(items.get(i).getImage()).into(Subcat_imageView);
       /* ImageView imageView = (ImageView) view
                .findViewById(R.id.cot_item_img);*/
        Log.d("cjsj","  "+items.get(i).getName()+items.get(i).getImage());
        if (i<=0){

        }else{
            //imageView.setVisibility(View.GONE);
        }
        return view;
    }
}
