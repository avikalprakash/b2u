package com.lueinfo.bshop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lueinfo.bshop.Adapter.CalenderEvensEntity;
import com.lueinfo.bshop.Adapter.PlainClass;
import com.lueinfo.bshop.Adapter.RecycleGalleryAdapter;
import com.lueinfo.bshop.Adapter.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventListActivity extends AppCompatActivity {
    CalenderEvensEntity calenderEvensEntity;
    String from="",s1,s2,eventid = "";
    public static final String LANGUAGE = "log";
    public static final String MyPref = "MyPref";
    SharedPreferences sharedpreferences;
    String language;
    private ProgressDialog pDialog;
    private List<PlainClass> plainClassList = new ArrayList<PlainClass>();

    private RecyclerView recyclerView;

    private RecycleGalleryAdapter adapter;
    JSONObject jsonresponse;


    String schooldtl;

    ImageView mbackimage;

    String fname,lname,enqid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        sharedpreferences=this.getSharedPreferences(MyPref, Context.MODE_PRIVATE);

        language = sharedpreferences.getString(LANGUAGE,"");
        Intent intent=getIntent();
        calenderEvensEntity=(CalenderEvensEntity) intent.getSerializableExtra(  "eventid" );

        if(eventid.equals("")) {

            try {
                eventid = calenderEvensEntity.getEvent_date();
            }catch (Exception e){

                eventid = getIntent().getStringExtra("event_date");
            }
        }else{
            eventid = getIntent().getStringExtra("event_date");
        }


        mbackimage = (ImageView) findViewById(R.id.back_imageverified);
        mbackimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventListActivity.this.finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        adapter = new RecycleGalleryAdapter(EventListActivity.this,plainClassList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(EventListActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(EventListActivity.this, new RecyclerTouchListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        PlainClass mo123 = plainClassList.get(position);

                        Intent newsdetailintnt = new Intent(getApplicationContext(),EventDetail.class);
                        //   newsdetailintnt.putExtra("type",mo123.getYear());
                        newsdetailintnt.putExtra("id",mo123.getId());

                        startActivity(newsdetailintnt);



                        // TODO Handle item click
                    }
                })
        );



//        pDialog = new ProgressDialog(EventListActivity.this);
//        // Showing progress dialog before making http request
//        pDialog.setMessage("Loading...");
//        pDialog.show();

        // changing action bar color
//        getActionBar().setBackgroundDrawable(
//                new ColorDrawable(Color.parseColor("#1b1b1b")));

        // Creating volley request obj

        populatedata();


    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        plainClassList.clear();
//        populatedata();
//    }

    public void populatedata() {

        pDialog = new ProgressDialog(EventListActivity.this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userid = preferences.getString("id", "");

        final String url = "http://bshop2u.com/apiAdmin/api/event/getEventsByDate/"+eventid+"/"+language;

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

                                JSONArray jsonArray = objone.getJSONArray("message");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    PlainClass plainClass = new PlainClass();
                                    plainClass.setId(obj.getString("event_id"));
                                    plainClass.setTitle(obj.getString("title"));
                                    // movie.setTimetxt(obj.getString("created_at"));
                                    plainClass.setThumbnailUrl(obj.getString("image"));
                                    plainClass.setTimestamp(obj.getString("created_at"));
                                    plainClassList.add(plainClass);

                                }
                                adapter.notifyDataSetChanged();
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
                    Toast.makeText(EventListActivity.this, "You Have Some Connectivity Issue....", Toast.LENGTH_LONG).show();
                }
                hidePDialog();

            }
        });
        {
            RequestQueue requestQueue = Volley.newRequestQueue(EventListActivity.this);
            requestQueue.add(movieReq);
        }

    }
    @Override
    public void onDestroy () {
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
