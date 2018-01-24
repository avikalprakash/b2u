package com.lueinfo.bshop.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.lueinfo.bshop.MyApplication;
import com.lueinfo.bshop.R;
import com.lueinfo.bshop.RitsActivity.CartProducts;

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
import java.util.HashMap;
import java.util.List;


/**
 * Created by Fujitsu on 27/05/2017.
 */

public class PrePointAdapter extends RecyclerView.Adapter<PrePointAdapter.MyViewHolder> {


    private  List<PromotionDetails> movieItems;
    Context context;
    SessionManagement session;
    String itmid;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre,rating;
        NetworkImageView imag;
        Button mremovebtn;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.title123);

        }
    }


    public PrePointAdapter(Context context, List<PromotionDetails> moviesList) {

        this.context = context;
        this.movieItems = moviesList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prepointitem, parent, false);
                 return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        PromotionDetails movie = movieItems.get(position);
        holder.title.setText("MYR "+movie.getMoney());
        holder.genre.setText("Get MYR "+movie.getCashpoint()+" Cash Point");


    }



    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getItemCount() {

        return movieItems.size();
    }


}

