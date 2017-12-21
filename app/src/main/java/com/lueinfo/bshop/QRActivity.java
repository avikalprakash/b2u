package com.lueinfo.bshop;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class QRActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mintronametxt, mintrophonetxt;
    ImageView mqrimg,mshareimage,mbackimage;
    Bitmap myBitmap;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        mshareimage = (ImageView) findViewById(R.id.share_image);
        mshareimage.setOnClickListener(this);
        mqrimg = (ImageView) findViewById(R.id.imageQR);
        mbackimage = (ImageView) findViewById(R.id.back_image);
        mbackimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRActivity.this.finish();
            }
        });
        mintronametxt = (TextView) findViewById(R.id.introducerName);

        mintrophonetxt = (TextView) findViewById(R.id.introducerContact);

//        String intr = getIntent().getStringExtra("name");
//        String intrphone = getIntent().getStringExtra("phone");
//        String qrimg = getIntent().getStringExtra("qrcode");
//
//
//        mintronametxt.setText(intr);
//        mintrophonetxt.setText(intrphone);

        populatedata();

    }

    public void populatedata() {

        pDialog = new ProgressDialog(QRActivity.this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        SharedPreferences preferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
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

                                AlertDialog.Builder builder = new AlertDialog.Builder(QRActivity.this);
                                builder.setMessage("Please Try Again")
                                        .setNegativeButton("ok", null)
                                        .create()
                                        .show();
                            }else{

                                JSONObject jobj  = objone.getJSONObject("message");

                                SharedPreferences preferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("lock",true);
                                editor.apply();

                                mintronametxt.setText(jobj.getString("introducer_name"));
                                mintrophonetxt.setText(jobj.getString("introducer_phone"));
                                Picasso.with(getApplicationContext()).load(jobj.getString("qr_image")).into(mqrimg);
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
                    Toast.makeText(QRActivity.this, "You Have Some Connectivity Issue..", Toast.LENGTH_LONG).show();
                }
                hidePDialog();

            }
        });
        {

            RequestQueue requestQueue = Volley.newRequestQueue(QRActivity.this);
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
    public void onBackPressed() {

//        startActivity(new Intent(QRActivity.this,MainActivity.class));
//        finish();

        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        View v1 = getWindow().getDecorView().getRootView();
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
            Toast.makeText(getApplicationContext(), "No App Available", Toast.LENGTH_SHORT).show();
        }
    }


}
