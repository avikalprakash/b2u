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
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.lueinfo.bshop.QRActivity;
import com.lueinfo.bshop.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class QrFragment extends Fragment implements View.OnClickListener {


    TextView mintronametxt, mintrophonetxt;
    ImageView mqrimg,mshareimage,mbackimage;
    Bitmap myBitmap;
    private ProgressDialog pDialog;
    Button msharebtn;
    public QrFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_qr, container, false);

        msharebtn = (Button) view.findViewById(R.id.share_btn);
        msharebtn.setOnClickListener(this);
        mqrimg = (ImageView) view.findViewById(R.id.imageQR);
//        mbackimage = (ImageView) view.findViewById(R.id.back_image);
//        mbackimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().finish();
//            }
//        });
        mintronametxt = (TextView) view.findViewById(R.id.introducerName);

        mintrophonetxt = (TextView) view.findViewById(R.id.introducerContact);
        populatedata();
        return  view;
    }

    public void populatedata() {

        pDialog = new ProgressDialog(getContext());
        // Showing progress dialog before making http request
        pDialog.setMessage("loading...");
        pDialog.show();

        SharedPreferences preferences = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String userid = preferences.getString("id", "");

        final String url = "http://cloud9.condoassist2u.com/api/getQrCode/"+userid;

        StringRequest movieReq = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("rr000", response.toString());
                        hidePDialog();

                        try {
                            JSONObject objone = new JSONObject(response);
                            boolean check  = objone.getBoolean("error");
                            if(check) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("Please Try Again")
                                        .setNegativeButton("ok", null)
                                        .create()
                                        .show();
                            }else{

                                JSONObject jobj  = objone.getJSONObject("message");

                                SharedPreferences preferences = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("lock",true);
                                editor.apply();

                                mintronametxt.setText(jobj.getString("introducer_name"));
                                mintrophonetxt.setText(jobj.getString("introducer_phone"));
                                Picasso.with(getContext()).load(jobj.getString("qr_image")).into(mqrimg);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    // notifying list adapter about data changes
                    // so that it renders the list view with updated data

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getContext(), "You Have Some Connectivity Issue..", Toast.LENGTH_LONG).show();
                }
                hidePDialog();

            }
        });
        {

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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

    @Override
    public void onClick(View v) {
        View v1 = getActivity().getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        myBitmap = v1.getDrawingCache();
        saveBitmap(myBitmap);
    }

    public void saveBitmap(Bitmap bitmap) {
        String filePath = Environment.getExternalStorageDirectory()
                + File.separator + "Pictures/screenshot.png";
        File imagePath = new File(filePath);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            sendMail(imagePath);
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    public void sendMail(File file) {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "For Get Discount \n Share Qr Image \n http://bshop2u.com/qrpage/qr-page.html");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "No App Available", Toast.LENGTH_SHORT).show();
        }
    }



}
