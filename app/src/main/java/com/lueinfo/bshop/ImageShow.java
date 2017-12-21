package com.lueinfo.bshop;
//http://www.androhub.com/android-image-slider-using-viewpager/

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.lueinfo.bshop.Adapter.ItemEntity;
import com.lueinfo.bshop.Adapter.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ImageShow extends AppCompatActivity {
/*    private static final int SHOW_PROCESS_DIALOG = 1;
    private static final int HIDE_PROCESS_DIALOG = 0;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    ArrayList<ItemEntity> ImageList = new ArrayList<ItemEntity>();
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private static final Integer[] IMAGES = {R.drawable.slide1, R.drawable.slider2, R.drawable.slider3, R.drawable.slider4};

    ProgressBar pb;
    Dialog dialog;
    MyAsync myAsync;
    String id = "";
    ViewPager mViewPager;
    //    private static ViewPager mPager;
    private final HttpClient Client = new DefaultHttpClient();
    private String Content;
    AndroidImageAdapter adapterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);
        dialog = new Dialog(ImageShow.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fill_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        init();
        mViewPager = (ViewPager) findViewById(R.id.viewPageAndroid);

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        Log.d("imageid", "  " + intent.getStringExtra("ID"));
        loadData();
    }
//    private void init() {
//        for(int i=0;i<IMAGES.length;i++)
//            ImagesArray.add(IMAGES[i]);
//
//        mPager = (ViewPager) findViewById(R.id.viewPageAndroid);
//
//
//        mPager.setAdapter(new SlidingImage_Adapter(ImageShow.this,ImagesArray));
//
//
////        CirclePageIndicator indicator = (CirclePageIndicator)
////                findViewById(R.id.indicator);
////
////        indicator.setViewPager(mPager);
//
//        final float density = getResources().getDisplayMetrics().density;
//
////Set circle indicator radius
////        indicator.setRadius(5 * density);
//
//        NUM_PAGES =IMAGES.length;
//
//        // Auto start of viewpager
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == NUM_PAGES) {
//                    currentPage = 0;
//                }
//                mPager.setCurrentItem(currentPage++, true);
//            }
//        };
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 3000, 3000);
//
//        // Pager listener over indicator
////        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
////
////            @Override
////            public void onPageSelected(int position) {
////                currentPage = position;
////
////            }
////
////            @Override
////            public void onPageScrolled(int pos, float arg1, int arg2) {
////
////            }
////
////            @Override
////            public void onPageScrollStateChanged(int pos) {
////
////            }
////        });
//
//    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case SHOW_PROCESS_DIALOG:
                    //   pb.setVisibility(View.VISIBLE);
                    dialog.show();
                    break;
                case HIDE_PROCESS_DIALOG:
                    //   pb.setVisibility(View.GONE);
                    dialog.hide();
                    break;
            }
            return false;
        }
    });

    class MyAsync extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            handler.sendEmptyMessage(SHOW_PROCESS_DIALOG);
            String s = "";
            HttpGet httpget = new HttpGet(strings[0]);
            httpget.setHeader("Accept", "application/json");
            httpget.setHeader("Content-type", "application/json");
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            try {
                Content = Client.execute(httpget, responseHandler);
                HttpResponse httpResponse = Client.execute(httpget);
                HttpEntity httpEntity = httpResponse.getEntity();
                s = readResponse(httpResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d("category1", "" + null);

            Log.d("tag11", " " + s);

            return s;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            handler.sendEmptyMessage(HIDE_PROCESS_DIALOG);
            ImageList.clear();
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ItemEntity itemEntity = new ItemEntity();
                    itemEntity.setImage(jsonObject.getString("url"));
                    Log.d("ImageUrlDetail", "  " + jsonObject.getString("url"));
                    ImageList.add(itemEntity);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (ImageList.size() > 0) {
                Log.d("******", "******");
                adapterView = new AndroidImageAdapter(ImageShow.this, ImageList);
                mViewPager.setAdapter(adapterView);
            } else {

            }

        }

        private String readResponse(HttpResponse httpResponse) {
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
                Log.d("return_text", "" + return_text);
            } catch (Exception e) {

            }
            return return_text;


        }}

        @Override
        public void onPause() {
            super.onPause();
            dialog.dismiss();
            if (myAsync.getStatus() == AsyncTask.Status.RUNNING) {
                myAsync.cancel(true);
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            dialog.dismiss();
            if (myAsync.getStatus() == AsyncTask.Status.RUNNING) {
                myAsync.cancel(true);
            }
        }

        public void loadData() {
            myAsync = new MyAsync();
            if (Utils.isNetworkAvailable(ImageShow.this) && myAsync.getStatus() != AsyncTask.Status.RUNNING) {
                String serverURL = "http://54.67.107.248/jeptags/api/rest/products/" + id + "/images";

                // Create Object and call AsyncTask execute Method
                new MyAsync().execute(serverURL);
            } else {
                Toast.makeText(ImageShow.this, "NO net avalable", Toast.LENGTH_LONG).show();
                // nonet_ll_cot.setVisibility(View.VISIBLE);
            }
        }*/
    }

