package com.lueinfo.bshop.RitsActivity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lueinfo.bshop.Adapter.CartNewAdapter;
import com.lueinfo.bshop.Adapter.PrePointAdapter;
import com.lueinfo.bshop.Adapter.PromotionDetails;
import com.lueinfo.bshop.Adapter.RecyclerTouchListener;
import com.lueinfo.bshop.Adapter.SessionManagement;
import com.lueinfo.bshop.MainActivity;
import com.lueinfo.bshop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PointsFrag extends Fragment {

    List<PromotionDetails> promotionDetailses=new ArrayList<PromotionDetails>();
    ListView listPromotion;
    View view;
    public static final String LANGUAGE = "log";
    public static final String MyPref = "MyPref";
    SharedPreferences sharedpreferences;
    String language;
    ProgressDialog pDialog;
    PrePointAdapter adapter;
    SessionManagement session;
    ImageView mbackimage;
    private RecyclerView recyclerView;

    public PointsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_points, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        adapter = new PrePointAdapter(getActivity(),promotionDetailses);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(getActivity(), new RecyclerTouchListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        PromotionDetails mo123 = promotionDetailses.get(position);

                        Intent newsdetailintnt = new Intent(getActivity(),PointsDetail.class);
                        //   newsdetailintnt.putExtra("type",mo123.getYear());
                        newsdetailintnt.putExtra("mny",mo123.getMoney());
                        newsdetailintnt.putExtra("csp",mo123.getCashpoint());

                        startActivity(newsdetailintnt);



                        // TODO Handle item click
                    }
                })
        );



        sharedpreferences= getActivity().getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        language = sharedpreferences.getString(LANGUAGE,"");

        session = new SessionManagement(getActivity());

        populatedata();

         return  view;
    }

    public void populatedata() {

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


        final String url = "http://bshop2u.com/apiAdmin/api/getcashpoint";

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
                                    PromotionDetails movie = new PromotionDetails();
                                    movie.setMoney(obj.getString("money"));
                                    movie.setItemid(obj.getString("id"));
                                    movie.setCashpoint(obj.getString("cashpoint"));
                                    movie.setCashpointdate(obj.getString("cashpoint_date"));
                                    promotionDetailses.add(movie);

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
                    Toast.makeText(getActivity(), "You Have Some Connectivity Issue....", Toast.LENGTH_LONG).show();
                }
                hidePDialog();

            }
        });
        {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
