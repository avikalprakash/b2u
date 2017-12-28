package com.lueinfo.bshop.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lueinfo.bshop.R;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;



/**
 * Created by lue on 23-06-2017.
 */

public class PromotionListAdaptor extends BaseAdapter {

  //  LayoutInflater mInflater;
   // private Activity context;
   ImageView circleImageView;
  //  ArrayList<User> Contact=new ArrayList<>();
    ArrayList<PromotionDetails> promotionDetailses=new ArrayList<>();
    TextView title;
    Button add;
    String ReciverMobile="";
    private static final int SHOW_PROCESS_DIALOG = 1;
    private static final int HIDE_PROCESS_DIALOG = 0;
    AVLoadingIndicatorView dialog;
    LayoutInflater mInflater;
    private Activity context;//
    public PromotionListAdaptor(FragmentActivity activity, ArrayList<PromotionDetails> storeContacts) {
        this.context=activity;
        this.promotionDetailses=storeContacts;
    }
    @Override
    public int getCount() {
        return promotionDetailses.size();
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
        mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.promotion_custom_layout, null);
        title=(TextView)view.findViewById(R.id.title);
        circleImageView=(ImageView)view.findViewById(R.id.img_list);
        if(promotionDetailses.get(i).getTitle()!=null){
            title.setText(promotionDetailses.get(i).getTitle());
        }else {
            title.setText(" ");
        }
        Picasso.with(context).load(promotionDetailses.get(i).getImage()).into(circleImageView);
       /* if(compareList.get(i).getContactName().equals("true")){
            add.setText("Added");
        }*/
        return view;
    }
}
