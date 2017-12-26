package com.lueinfo.bshop.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.lueinfo.bshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Fujitsu on 22/05/2017.
 */

public class CustomListAdapter extends BaseAdapter {
private Context activity;
private LayoutInflater inflater;
private List<PromotionModel> movieItems;




public CustomListAdapter(Context context, List<PromotionModel> movie) {

        this.activity = context;
        this.movieItems = movie;

        }


@Override
public int getCount() {
        return movieItems.size();
        }

@Override
public Object getItem(int location) {
        return movieItems.get(location);
        }

@Override
public long getItemId(int position) {
        return position;
        }



static class ViewHolder {

    ImageView mgridimage;
    TextView detailtxt;
    TextView timetxt;
   // TextView calrtxt;

}


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.row_layout, null);
            holder = new ViewHolder();

            holder.mgridimage = (ImageView) convertView
                    .findViewById(R.id.grid_image);

            holder.detailtxt = (TextView) convertView.findViewById(R.id.griddetail_text);
            holder.timetxt = (TextView) convertView.findViewById(R.id.gridtime_text);

            convertView.setTag(holder);
        }
        else {

            holder = (ViewHolder) convertView.getTag();
        }


        PromotionModel m = movieItems.get(position);
        Log.d("kbbobo",""+movieItems.size());
        if(!URLUtil.isValidUrl(movieItems.get(position).getImage()))
        {
            holder.mgridimage.setVisibility(View.GONE);
        }
        else
        {
            holder.mgridimage.setVisibility(View.VISIBLE);//add this
            Picasso.with(activity).load(movieItems.get(position).getImage()).into(holder.mgridimage);
            String str = "rjbhai";
            Log.d("rj123",str);
        }

        holder.detailtxt.setText(m.getName());

        holder.timetxt.setText(m.getPrice());
      //  holder.calrtxt.setText(m.getCaltxt());



        return convertView;
    }


}

