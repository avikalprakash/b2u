package com.lueinfo.bshop.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lueinfo.bshop.R;

import java.util.ArrayList;


/**
 * Created by Fujitsu on 15/04/2017.
 */

public class Adapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private ArrayList<PojoClass> dummyData;
    Context context;

    public Adapter(Context contex, ArrayList<PojoClass> dummyData) {

        this.dummyData = dummyData;
        mLayoutInflater = LayoutInflater.from(contex);
        this.context = contex;

    }

    @Override
    public int getCount() {
        return dummyData.size();
    }

    @Override
    public Object getItem(int position) {
        return dummyData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PojoClass itr = (PojoClass) getItem(position);


        convertView = mLayoutInflater.inflate(R.layout.griditem,parent,false);

        ImageView mimage1 = (ImageView) convertView.findViewById(R.id.icon);
        TextView textView1 = (TextView) convertView.findViewById(R.id.lable);


      //  mimage1.setImageResource(itr.getImage());

        if(android.os.Build.VERSION.SDK_INT >= 21){

            mimage1.setImageResource(itr.getImage());

//            rBlack = getResources().getDrawable(R.drawable.rblack, getTheme());
        } else {
        //  Drawable  rBlack = context.getResources().getDrawable(itr.getImage(),null);

            Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                    itr.getImage());

           // Drawable  rBlack = ContextCompat.getDrawable(context,itr.getImage());
            mimage1.setImageBitmap(icon);
        }

        textView1.setText(itr.getName());


        return convertView;


    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}

