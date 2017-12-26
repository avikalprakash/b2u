package com.lueinfo.bshop.Fragment;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
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
import com.lueinfo.bshop.Adapter.CustomListAdapter;
import com.lueinfo.bshop.Adapter.PromotionModel;
import com.lueinfo.bshop.OfferZoneDetails;
import com.lueinfo.bshop.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfferZone extends Fragment {


    TextView mintronametxt, mintrophonetxt;
    ImageView mqrimg,mshareimage,mbackimage;
    Bitmap myBitmap;
    private ProgressDialog pDialog;
    Button msharebtn;
    CustomListAdapter adapter;
    GridView grid;
    TextView title;
    List<PromotionModel> promotionlist = new ArrayList<>();
    public OfferZone() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_offer_zone, container, false);
        grid=(GridView)view.findViewById(R.id.grid);
     //   msharebtn = (Button) view.findViewById(R.id.share_btn);

        populatedata();

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Toast.makeText(SpecialPromotion.this, "You Clicked at "+movie.getItemid(), Toast.LENGTH_SHORT).show();
                //     startActivity(new Intent(getApplicationContext(), SpecialPromotionDetails.class));
                PromotionModel movie =  promotionlist.get(position);
                Intent intent = new Intent(getActivity(),OfferZoneDetails.class);
                intent.putExtra("name",movie.getName());
                intent.putExtra("price", movie.getPrice());
                intent.putExtra("description",movie.getDescription());
                intent.putExtra("image",movie.getImage());
                startActivity(intent);
                //  Toast.makeText(SpecialPromotion.this, "You Clicked at "+movie.getItemid(), Toast.LENGTH_SHORT).show();

            }
        });
        return  view;
    }


    public void populatedata() {

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String userid = preferences.getString("id", "");

        final String url = "http://bshop2u.com/apirest/home_product_promotions";

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

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("")
                                        .setNegativeButton("ok", null)
                                        .create()
                                        .show();

                            }else{

                                JSONArray jsonArray = objone.getJSONArray("message");

                                if(jsonArray.length()>0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject obj = jsonArray.getJSONObject(i);

                                        PromotionModel movie = new PromotionModel();

                                        movie.setImage(obj.getString("image"));
                                        movie.setName(obj.getString("name"));
                                        movie.setDescription(obj.getString("description"));
                                        movie.setId(obj.getString("id"));
                                        movie.setPrice(obj.getString("price"));
                                        promotionlist.add(movie);

                                    }
                                    adapter = new CustomListAdapter(getActivity(), promotionlist);
                                    grid.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

                                }else{

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("No Special Promotion Today")
                                            .setNegativeButton("ok", null)
                                            .create()
                                            .show();

                                }
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
