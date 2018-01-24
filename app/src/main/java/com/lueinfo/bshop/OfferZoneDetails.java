package com.lueinfo.bshop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lueinfo.bshop.Adapter.AndroidImageAdapternew;
import com.lueinfo.bshop.Adapter.SessionManagement;
import com.lueinfo.bshop.PaymentGateway.Main2Activity;
import com.lueinfo.bshop.RitsActivity.CartProducts;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OfferZoneDetails extends AppCompatActivity {
    String name;
    String price;
    String description;
    String image,offerid;

    ImageView imageView,mshareimg,mbackimage;
    Button addticart,mbybtn;
    TextView nameText;
    TextView priceText;
    TextView descriptionText;
    TextView headertext;
    private ProgressDialog pDialog;
    String sessionid,cartid,url;
    private int delay = 10000;
    SessionManagement session;

    AndroidImageAdapternew adapterView;
    Handler handler1;
    Runnable runnable;

    ArrayList<String> ImageList = new ArrayList<>();

    ViewPager mViewPager;
    private int page = 0;

    CirclePageIndicator indicator;
    float density;

    String shareurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_zone_details);


        mViewPager = (ViewPager) findViewById(R.id.image);
        handler1=new Handler();
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);

        mbybtn = (Button) findViewById(R.id.byNow);
        mbybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(OfferZoneDetails.this, Main2Activity.class));

                if(session.isLoggedIn())
                {
                    startActivity(new Intent(OfferZoneDetails.this, Main2Activity.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please login first",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(OfferZoneDetails.this, LoginActivity.class);
                    startActivity(intent);

                }


            }
        });

        headertext=(TextView)findViewById(R.id.text_header2);
        mbackimage = (ImageView) findViewById(R.id.back_imageverified);
        mbackimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(OfferZoneDetails.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                page = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        headertext.setVisibility(View.VISIBLE);

 //       headertext.setText("Offer Zone");
      //  imageView=(ImageView)findViewById(R.id.image);


        nameText=(TextView)findViewById(R.id.ItemName);
        priceText=(TextView)findViewById(R.id.regularprice);
        descriptionText=(TextView)findViewById(R.id.description);
        name=getIntent().getStringExtra("name");
        price=getIntent().getStringExtra("price");
        description=getIntent().getStringExtra("description");
        image=getIntent().getStringExtra("image");
        offerid=getIntent().getStringExtra("offerid");
      //  url=getIntent().getStringExtra("url");

      //  Picasso.with(getApplicationContext()).load(String.valueOf(image)).into(imageView);
        nameText.setText(name);
        priceText.setText("MYR "+price);
        descriptionText.setText(description);
        ArrayList<String> ImageList = new ArrayList<>();

        mshareimg = (ImageView) findViewById(R.id.sharebutton);
        mshareimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
                share.putExtra(Intent.EXTRA_TEXT, shareurl);

                startActivity(Intent.createChooser(share, "Share link!"));

            }

        });

        session = new SessionManagement(OfferZoneDetails.this);

        // populatedata();

        addticart = (Button) findViewById(R.id.addToCart);
        addticart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(session.isLoggedIn())
                {
                    Addcart();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please login first",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(OfferZoneDetails.this, LoginActivity.class);
                    startActivity(intent);

                }


            }
        });

        ImageSLideShow();

    }


    @Override
    public void onPause() {
        super.onPause();
        handler1.removeCallbacks(runnable);
    }


    @Override
    public void onResume() {
        super.onResume();
        handler1.postDelayed(runnable, delay);
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(OfferZoneDetails.this,MainActivity.class);
        startActivity(intent);
        finish();

        super.onBackPressed();
    }

    public void Addcart(){

        pDialog = new ProgressDialog(OfferZoneDetails.this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        session = new SessionManagement(OfferZoneDetails.this);
        HashMap<String, String> user1 = session.getUserDetails();
        String userid = user1.get(session.KEY_ID);

        Map<String, String> postParam= new HashMap<String, String>();
       // postParam.put("session_id", sessionid);
        postParam.put("customer_id", userid);
        postParam.put("product_id", offerid);
        postParam.put("qty", "1");




        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                "http://bshop2u.com/apirest/add_to_cart_custom", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject objone) {
                        pDialog.dismiss();
                        Log.d("tag", objone.toString());
                        try {
                            // JSONObject objone = new JSONObject(response);
                            boolean check  = objone.getBoolean("error");
                            JSONObject newobj = objone.getJSONObject("message");
                            String itcount = newobj.getString("items_count");
                            String mmmmm = newobj.getString("mymessage");
//                            JSONArray jsonArray = objone.getJSONArray("message");
//                            for(int i = 0 ; i < jsonArray.length(); i++)
//                            {
//
//                                JSONObject obj = jsonArray.getJSONObject(i);
//                                itemcount = obj.getString("items_count");
//
//                            }


                            SharedPreferences sharedPrefotpaccheckst = getSharedPreferences("MyPreffacultyaccheckstudent", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor123accheckst = sharedPrefotpaccheckst.edit();
                            editor123accheckst.putString("count",itcount);
                            editor123accheckst.commit();

                            if(check) {

                                String messg = objone.getString("message");
                                AlertDialog.Builder builder = new AlertDialog.Builder(OfferZoneDetails.this);
                                builder.setMessage(messg)
                                        .setNegativeButton("ok", null)
                                        .create()
                                        .show();
                            }else{

                                AlertDialog.Builder builder = new AlertDialog.Builder(OfferZoneDetails.this);
                                builder.setMessage("Add to cart successfully")
                                        .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                addticart.setEnabled(false);
                                                addticart.setBackgroundColor(getResources().getColor(R.color.dcolor));

                                            }
                                        })
                                        .create()
                                        .show();


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                VolleyLog.d("tag", "Error: " + error.getMessage());
                //  hideProgressDialog();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }



        };

        jsonObjReq.setTag("tag");
        // Adding request to request queue
        RequestQueue queue = Volley.newRequestQueue(OfferZoneDetails.this);
        queue.add(jsonObjReq);

    }

    public void ImageSLideShow(){

        pDialog = new ProgressDialog(OfferZoneDetails.this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        session = new SessionManagement(OfferZoneDetails.this);
        HashMap<String, String> user1 = session.getUserDetails();
        String userid = user1.get(session.KEY_ID);

        Map<String, String> postParam= new HashMap<String, String>();
        // postParam.put("session_id", sessionid);
        postParam.put("product_id", offerid);




        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                "http://bshop2u.com/apirest/get_product_images", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject objone) {
                        pDialog.dismiss();
                        Log.d("tag", objone.toString());
                        try {
                            // JSONObject objone = new JSONObject(response);
                            boolean check  = objone.getBoolean("error");
                            JSONObject newobj = objone.getJSONObject("message");

                            JSONArray jsonArray = newobj.getJSONArray("images");
                             shareurl = newobj.getString("url");

                            for(int i = 0 ;i < jsonArray.length();i++)
                            {

                                String img = (String) jsonArray.get(i);
                                ImageList.add(img);
                            }


                            if (ImageList.size() > 0){
                                adapterView = new AndroidImageAdapternew(OfferZoneDetails.this, ImageList);
                                mViewPager.setAdapter(adapterView);
                                indicator.setViewPager(mViewPager);

                                try {
                                    density = getResources().getDisplayMetrics().density;
                                    indicator.setRadius(5 * density);
                                }catch (Exception e){}

                                runnable = new Runnable() {
                                    public void run() {
                                        if (adapterView.getCount() == page) {
                                            page = 0;
                                        } else {
                                            page++;
                                        }
                                        mViewPager.setCurrentItem(page, true);
                                        handler1.postDelayed(this, delay);
                                    }
                                };
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                VolleyLog.d("tag", "Error: " + error.getMessage());
                //  hideProgressDialog();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }



        };

        jsonObjReq.setTag("tag");
        // Adding request to request queue
        RequestQueue queue = Volley.newRequestQueue(OfferZoneDetails.this);
        queue.add(jsonObjReq);



    }






}
