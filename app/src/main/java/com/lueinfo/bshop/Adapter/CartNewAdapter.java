package com.lueinfo.bshop.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.lueinfo.bshop.MainActivity;
import com.lueinfo.bshop.MyApplication;
import com.lueinfo.bshop.R;
import com.lueinfo.bshop.RitsActivity.CartFragment;
import com.lueinfo.bshop.RitsActivity.CartProducts;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
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

public class CartNewAdapter extends RecyclerView.Adapter<CartNewAdapter.MyViewHolder> {


    private  List<PromotionDetails> movieItems;
    Context context;
    SessionManagement session;
    String itmid;

    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre,rating;
        NetworkImageView imag;
        Button mremovebtn;

        public MyViewHolder(View view) {
            super(view);
            imag = (NetworkImageView) view.findViewById(R.id.thumbnail);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            mremovebtn = (Button) view.findViewById(R.id.removebtn);

            session = new SessionManagement(context);
        }
    }


    public CartNewAdapter(Context context, List<PromotionDetails> moviesList) {

        this.context = context;
        this.movieItems = moviesList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cattttlelitem, parent, false);
                 return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        PromotionDetails movie = movieItems.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText("MYR "+movie.getPrice());
        imageLoader.get(movie.getImage(), ImageLoader.getImageListener(holder.imag, R.mipmap.ic_launcher, R.mipmap.ic_launcher));

       itmid = movie.getItemid();

        if(!URLUtil.isValidUrl(movieItems.get(position).getImage()))
        {
            holder.imag.setVisibility(View.GONE);
        }
        else
        {
            holder.imag.setVisibility(View.VISIBLE);//add this
            try {
                holder.imag.setImageUrl(movieItems.get(position).getImage(), imageLoader);
            }catch (Exception e){

                Toast.makeText(context,"On Activity "+e.toString(),Toast.LENGTH_LONG).show();
            }
        }

      holder.mremovebtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

          new Remove().execute();

          }
      });

    }


    class Remove extends AsyncTask<String, Void, String> {

        HashMap<String, String> user1 = session.getUserDetails();
        String id = user1.get(session.KEY_ID);

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";


            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://bshop2u.com/apirest/remove_from_cart_custom");
                httpPost.setHeader("Content-type", "application/json");
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("customer_id", id);
                jsonObject.accumulate("item_id", itmid);

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                s = readadsResponse(httpResponse);
                Log.d("tag1", " " + s);
            } catch (Exception exception) {
                exception.printStackTrace();

                Log.d("espone",exception.toString());

            }

            return s;

        }
        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            pDialog.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(json);
                boolean error = jsonObject.getBoolean("error");

                if(error)
                {

                }else
                    {
                        Intent iSave=new Intent(context, CartProducts.class);
                        context.startActivity(iSave);
                        ((Activity)context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        ((Activity)context).finish();

                    }

            }
            catch (JSONException e) {
            }
        }
    }


    private String readadsResponse(HttpResponse httpResponse) {

        InputStream is = null;
        String return_text = "";
        try {
            is = httpResponse.getEntity().getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return_text = sb.toString();
            Log.d("return1230", "" + return_text);
        } catch (Exception e) {

        }
        return return_text;
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

