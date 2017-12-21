package com.lueinfo.bshop.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.lueinfo.bshop.R;

import java.util.ArrayList;

public class MainCotegotieAdapter extends BaseAdapter {
    String json;
    LayoutInflater mInflater;
    private Activity context;
    private ArrayList<ItemEntity> items=new ArrayList<ItemEntity>();
    public MainCotegotieAdapter(Activity context, ArrayList<ItemEntity> items) {
        this.context = context;
        this.items=items;
    }
    @Override
    public int getCount() {
        return items.size();
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
        view = mInflater.inflate(R.layout.cotegry_item, null);
        TextView txtTitle = (TextView) view
                .findViewById(R.id.cot_item_text);
        ImageView imageView = (ImageView) view
                .findViewById(R.id.cot_item_img);
        txtTitle.setText(items.get(i).getName());

        return view;
    }

}
