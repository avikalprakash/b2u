package com.lueinfo.bshop.RitsActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.lueinfo.bshop.Adapter.CartNewAdapter;
import com.lueinfo.bshop.Adapter.PromotionDetails;
import com.lueinfo.bshop.Adapter.PromotionListAdaptor;
import com.lueinfo.bshop.Adapter.ServiceHandler;
import com.lueinfo.bshop.Adapter.SessionManagement;
import com.lueinfo.bshop.EventListActivity;
import com.lueinfo.bshop.Fragment.PromotionFragment;
import com.lueinfo.bshop.MainActivity;
import com.lueinfo.bshop.OfferZoneDetails;
import com.lueinfo.bshop.R;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartProducts extends AppCompatActivity {

    List<PromotionDetails> promotionDetailses=new ArrayList<PromotionDetails>();
    ListView listPromotion;
    View view;
    public static final String LANGUAGE = "log";
    public static final String MyPref = "MyPref";
    SharedPreferences sharedpreferences;
    String language;
    CartNewAdapter adapter;
    SessionManagement session;
    ImageView mbackimage;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_products);

        mbackimage = (ImageView) findViewById(R.id.back_imageverified);
        mbackimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartProducts.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        adapter = new CartNewAdapter(CartProducts.this,promotionDetailses);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CartProducts.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        recyclerView.addOnItemTouchListener(
//                new RecyclerTouchListener(getActivity(), new RecyclerTouchListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view, int position) {
//
//                        PlainClass mo123 = plainClassList.get(position);
//
//                        Intent newsdetailintnt = new Intent(getApplicationContext(),EventDetail.class);
//                        //   newsdetailintnt.putExtra("type",mo123.getYear());
//                        newsdetailintnt.putExtra("id",mo123.getId());
//
//                        startActivity(newsdetailintnt);
//
//
//
//                        // TODO Handle item click
//                    }
//                })
//        );
        sharedpreferences= getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        language = sharedpreferences.getString(LANGUAGE,"");

        session = new SessionManagement(CartProducts.this);

        new Cart().execute();
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(CartProducts.this,MainActivity.class);
        startActivity(intent);
        finish();

        super.onBackPressed();
    }

    class Cart extends AsyncTask<String, Void, String> {

        HashMap<String, String> user1 = session.getUserDetails();
        String id = user1.get(session.KEY_ID);

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CartProducts.this);
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
                HttpPost httpPost = new HttpPost("http://bshop2u.com/apirest/get_cart_products_custom");
                httpPost.setHeader("Content-type", "application/json");
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("customer_id", id);
                jsonObject.accumulate("lang", language);

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

                if (!error) {

                    JSONArray jsonArray = jsonObject.getJSONArray("message");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject jobject = jsonArray.getJSONObject(i);
                        String itemcount = jobject.getString("items_count");

                        SharedPreferences sharedPrefotpaccheckst = getSharedPreferences("MyPreffacultyaccheckstudent", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor123accheckst = sharedPrefotpaccheckst.edit();
                        editor123accheckst.putString("count",itemcount);
                        editor123accheckst.commit();

                        JSONArray newarray = jobject.getJSONArray("list");
                        for (int j = 0; j < newarray.length(); j++)
                        {
                            JSONObject nrejsonobj = newarray.getJSONObject(j);
                            PromotionDetails promotionDetails = new PromotionDetails();
                            promotionDetails.setTitle(nrejsonobj.getString("name"));
                            promotionDetails.setDescription(nrejsonobj.getString("product_description"));
                            promotionDetails.setImage(nrejsonobj.getString("image_url"));
                            promotionDetails.setProductid(nrejsonobj.getString("product_id"));
                            promotionDetails.setPrice(nrejsonobj.getString("price"));
                            promotionDetails.setActualprice(nrejsonobj.getString("actual_price"));
                            promotionDetails.setItemid(nrejsonobj.getString("item_id"));
                            promotionDetailses.add(promotionDetails);

                        }

                    }
                    adapter.notifyDataSetChanged();


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




}
