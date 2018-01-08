package com.lueinfo.bshop;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SpecialPromotionDetails extends AppCompatActivity {
    String title;
    String created_at;
    String description;
    String image;
//
    ImageView imageView;
    TextView nameText;
    TextView priceText;
    TextView descriptionText;
    Button addticart;
    TextView headertext;
    private ProgressDialog pDialog;
    String sessionid,cartid,productid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_promotion_details);
        imageView=(ImageView)findViewById(R.id.image);
        nameText=(TextView)findViewById(R.id.ItemName);
        priceText=(TextView)findViewById(R.id.regularprice);
        descriptionText=(TextView)findViewById(R.id.description);
        title = getIntent().getStringExtra("title");
        created_at = getIntent().getStringExtra("careted_at");
        description = getIntent().getStringExtra("desc");
        image = getIntent().getStringExtra("image");
        productid = getIntent().getStringExtra("productid");

        Picasso.with(getApplicationContext()).load(String.valueOf(image)).into(imageView);
        nameText.setText(title);
        addticart = (Button) findViewById(R.id.addToCart);
        addticart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Addcart();
            }
        });
        // priceText.setText(price);
        descriptionText.setText(description);
        populatedata();
    }

    public void populatedata() {

        pDialog = new ProgressDialog(SpecialPromotionDetails.this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


        final String url = "http://bshop2u.com/apirest/soap_login";

        final StringRequest movieReq = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("rr000", response.toString());
                        hidePDialog();
                        try {
                            JSONObject objone = new JSONObject(response);
                            boolean error = objone.getBoolean("error");

                            if(error){

                            }else{

                                JSONObject jsonObject = objone.getJSONObject("message");
                                sessionid = jsonObject.getString("session_id");
                                cartid = jsonObject.getString("cart_id");

//                                JSONArray jsonArray = objone.getJSONArray("message");
//                                for (int i = 0; i < jsonArray.length(); i++) {
//
//                                    JSONObject obj = jsonArray.getJSONObject(i);
//                                    PlainClass plainClass = new PlainClass();
//                                    plainClass.setId(obj.getString("event_id"));
//                                    plainClass.setTitle(obj.getString("title"));
//                                    // movie.setTimetxt(obj.getString("created_at"));
//                                    plainClass.setThumbnailUrl(obj.getString("image"));
//                                    plainClass.setTimestamp(obj.getString("created_at"));
//                                    plainClassList.add(plainClass);
//
//                                }
                            }

                            //  adapter.notifyDataSetChanged();

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    // notifying list adapter about data changes
                    // so that it renders the list view with updated data

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(SpecialPromotionDetails.this, "You Have Some Connectivity Issue....", Toast.LENGTH_LONG).show();
                }
                hidePDialog();

            }
        });
        {
            RequestQueue requestQueue = Volley.newRequestQueue(SpecialPromotionDetails.this);
            requestQueue.add(movieReq);
        }

    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        hidePDialog();
    }

    public void Addcart(){

        pDialog = new ProgressDialog(SpecialPromotionDetails.this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("session_id", sessionid);
        postParam.put("cart_id", cartid);
        postParam.put("product_id", productid);
        postParam.put("qty", "1");




        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                "http://bshop2u.com/apirest/add_to_cart", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject objone) {
                        pDialog.dismiss();
                        Log.d("tag", objone.toString());
                        try {
                            // JSONObject objone = new JSONObject(response);
                            boolean check  = objone.getBoolean("error");
                            if(check) {

                                String messg = objone.getString("message");
                                AlertDialog.Builder builder = new AlertDialog.Builder(SpecialPromotionDetails.this);
                                builder.setMessage(messg)
                                        .setNegativeButton("ok", null)
                                        .create()
                                        .show();
                            }else{

                                AlertDialog.Builder builder = new AlertDialog.Builder(SpecialPromotionDetails.this);
                                builder.setMessage("Add to cart successfully!")
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
        RequestQueue queue = Volley.newRequestQueue(SpecialPromotionDetails.this);
        queue.add(jsonObjReq);

    }

}
