package com.lueinfo.bshop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lueinfo.bshop.Adapter.CalenderEvensEntity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventDetail extends AppCompatActivity {

    ImageView image_notice;
    TextView tital_notification,by,description,timing3;
    View view1;
    CalenderEvensEntity calenderEvensEntity;
    private ProgressDialog pDialog;
    String from="",s1,s2,eventid = "";

    public static final String LANGUAGE = "log";
    public static final String MyPref = "MyPref";
    SharedPreferences sharedpreferences;
    String language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        view1=findViewById( R.id. view1 );
        tital_notification=(TextView) view1.findViewById( R.id.tital_notification );
        by=(TextView) view1.findViewById( R.id.by );
        description=(TextView) view1.findViewById( R.id.description );
        timing3=(TextView) view1.findViewById( R.id.timing3 );
        image_notice=(ImageView)findViewById( R.id.image_notice );

        sharedpreferences=this.getSharedPreferences(MyPref, Context.MODE_PRIVATE);

        language = sharedpreferences.getString(LANGUAGE,"");

        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );
        Intent intent=getIntent();
        eventid = intent.getStringExtra("id");

        populatedata();
    }

    public void populatedata() {

        pDialog = new ProgressDialog(EventDetail.this);
        pDialog.setMessage("Loading...");
        pDialog.show();


        final String url = "http://bshop2u.com/apiAdmin/api/event/getEventDetails/"+eventid+"/"+language;

        StringRequest movieReq = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("rr000", response.toString());
                        hidePDialog();

                        try {
                            JSONObject objone = new JSONObject(response);
                            boolean check = objone.getBoolean("error");
                            JSONArray jsonArray = objone.getJSONArray("message");
                            if (check) {


                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.d("111sizeeeem", "kk1111 " + jsonArray.length());
                                    JSONObject jobject = jsonArray.getJSONObject(i);
                                    String title  = jobject.getString("title");
                                    String descr = jobject.getString("description");
                                    String image = jobject.getString("image");
                                    String ftime = jobject.getString("from_time");
                                    String ttime = jobject.getString("to_time");
                                    String postedby = jobject.getString("posted_by");
                                    String eventdate = jobject.getString("event_date");

                                    tital_notification.setText(title);
                                    description.setText( Html.fromHtml(descr));
                                    timing3.setText( "(Timing) From:- "+ftime+" To:- "+ttime);
                                    by.setText( ""+ postedby+"\n Date:"+eventdate);
                                    Picasso.with(EventDetail.this)
                                            .load(image)
                                            .placeholder(R.mipmap.ic_launcher)   // optional
                                            .error(R.mipmap.ic_launcher)     // optional
                                            .into(image_notice);
                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }      // notifying list adapter about data changes
                    // so that it renders the list view with updated data

                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(EventDetail.this, "You Have Some Connectivity Issue..", Toast.LENGTH_LONG).show();
                }
                hidePDialog();

            }
        });
        {

            RequestQueue requestQueue = Volley.newRequestQueue(EventDetail.this);
            requestQueue.add(movieReq);
        }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}
