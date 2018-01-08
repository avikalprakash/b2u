package com.lueinfo.bshop.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import com.lueinfo.bshop.Database.DatabaseHelper;
import com.lueinfo.bshop.EventListActivity;
import com.lueinfo.bshop.Fragment.AllCarts;
import com.lueinfo.bshop.ImageShow;

import com.lueinfo.bshop.R;
import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


//Rjjj
public class AddtoCartMobile extends Fragment {

    private static final int SHOW_PROCESS_DIALOG = 1;
    private static final int HIDE_PROCESS_DIALOG = 0;
    private final HttpClient Client = new DefaultHttpClient();
    MyAsync myAsync;
//    private String URL_SOAP_LOGIN = "http://54.67.107.248/jeptags/apirest/soap_login";
private String URL_SOAP_LOGIN = "http://bshop2u.com/apirest/soap_login";
    private String Content;
    ProgressBar pb;
    private ProgressDialog pDialog;
    String sessionid,cartid;
    Button button1,button2;
    String giiglemap_adress;
    String imageurl;
    public static TabLayout tabLayout;
    DotIndicator dotIndicator;
    public static ViewPager viewPager;
    public static DatabaseHelper dbHelper;
    String Entity_Id="";
    public static final String LANGUAGE = "log";
    public static final String MyPref = "MyPref";
    SharedPreferences sharedpreferences;
    String language;
    String Itemname="";
    String Saleprice="";
    String MrpPrice="";
    String Image_url;
    String Product_id;
    String id="";
    int image =0;
    String Description="";
    String RatingCount="";
    String Material="";
    String ItemWeight="";
    String Itemcolor="";
    String ItemStock="";
    TextView titleTV;
    TextView brandshow;
    TextView h,w,l;
    Vibrator mVibrator;
    RatingBar ratingBar ;
    ArrayList<String> images=new ArrayList<>();
    ArrayList<ItemEntity> cotegory_list=new ArrayList<ItemEntity>();
    TextView name,salePricetext,Regularpricetext,Descriptiontext,Ratingtext;
    TextView Weight,color,material,stock,headertext;
    String imageIntent="";
    GoogleMap map;
    TextView adresstext;
   // LocationAddress locationAddress;
    ImagePagerAdapter adapter;
    ImageView share;
    static FrameLayout frame_head;
    Dialog dialog;
    RelativeLayout layoutAddtocart;
    LinearLayout nonet_ll_cot,Buttonlayout;
    Button retry;
    MapView mMapView;
    double latitude=0;
    double longitude=0;
    String Latitude="";
    String Longitude="";
    String brand="";
    private GoogleMap googleMap;
    String mode="";
    String user_id="";
    String session_id="";
    String cart_id="";
    String weight="";
    int qty=1;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    SessionManagement sessionManagement;
    public AddtoCartMobile() {
        // Required empty public constructor
    }
    public byte[] intToBytes( final int i ) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(i);
        return bb.array();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mobile_addto_cart, container, false);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DatabaseHelper(getActivity());
       Buttonlayout=(LinearLayout)view.findViewById(R.id.linearLayout);
        layoutAddtocart=(RelativeLayout)view. findViewById(R.id.layoutAddtocart);
        nonet_ll_cot=(LinearLayout)view.findViewById(R.id.nonet_ll_cot);
        retry=(Button)view.findViewById(R.id.retry);
        share=(ImageView)view.findViewById(R.id.sharebutton);
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fill_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        adresstext=(TextView)view.findViewById(R.id.adresstext);
        headertext=(TextView)getActivity().findViewById(R.id.headertext);
        headertext.setText("Bshop");
        sharedpreferences=this.getActivity().getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        language = sharedpreferences.getString(LANGUAGE,"");

        //shareedpreferences
        pref = this.getActivity().getApplicationContext().getSharedPreferences("MyCart", MODE_PRIVATE);
        editor = pref.edit();
        int MyVersion = Build.VERSION.SDK_INT;

        sessionManagement = new SessionManagement(getActivity());
        if(sessionManagement.isLoggedIn()) {

            HashMap<String, String> user1 = sessionManagement.getUserDetails();

            // name
            if (user1 != null) {
                user_id = user1.get(sessionManagement.KEY_ID);
            }
        }
//        else {
//            Toast.makeText(getActivity(),"Please login first",Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(getActivity(), LoginActivity.class);
//            startActivity(intent);
//
//
//        }


        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                requestForSpecificPermission();
            }
        }
//        mMapView = (MapView) view.findViewById(R.id.map);
//        mMapView.onCreate(savedInstanceState);
//
//        mMapView.onResume();
//        try {
//            MapsInitializer.initialize(getActivity().getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        titleTV = (TextView) getActivity().findViewById(R.id.fab2);
        FrameLayout frameLayout=(FrameLayout)getActivity().findViewById(R.id.frame_head);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllCarts allCarts = new AllCarts();
                if (!allCarts.isInLayout()) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, allCarts);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        ArrayList<Details> contact=dbHelper.getContact();
        new GetShopLogin().execute();
        int size=contact.size();
        name = (TextView) view.findViewById(R.id.ItemName);
        ratingBar=(RatingBar)view.findViewById(R.id.ratingbar);
        salePricetext = (TextView)view. findViewById(R.id.Sale_Price);
//        Ratingtext=(TextView)view.findViewById(R.id.rating_mobile_count);
        Regularpricetext=(TextView)view.findViewById(R.id.regularprice);
        Descriptiontext=(TextView)view.findViewById(R.id.description);
        Weight=(TextView)view.findViewById(R.id.Weight);
        material=(TextView)view.findViewById(R.id.Material);
        color=(TextView)view.findViewById(R.id.Product_Color);
        brandshow=(TextView)view.findViewById(R.id.brand_show);
        stock=(TextView)view.findViewById(R.id.Stock);
        mVibrator= (Vibrator)getActivity(). getSystemService(Context.VIBRATOR_SERVICE);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Entity_Id=bundle.getString("KeyValue",Entity_Id);
        }

        loadData();

        viewPager = (ViewPager)view. findViewById(R.id.viewpager);
        dotIndicator = (DotIndicator) view.findViewById(R.id.indicator);

        button1 = (Button) view.findViewById(R.id.addToCart);
        button2 = (Button) view.findViewById(R.id.byNow);

//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent1=new Intent(getActivity(),PlaceOrder.class);
//                intent1.putExtra("sessionId",session_id);
//                intent1.putExtra("cartID",cart_id);
//                intent1.putExtra("qty", qty);
//                intent1.putExtra("product_id", Product_id);
//                Log.d("@@@@@@",""+Saleprice+"  "+salePricetext.getText().toString());
//                intent1.putExtra("price" ,salePricetext.getText().toString());
//                startActivity(intent1);
//            }
//        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
                share.putExtra(Intent.EXTRA_TEXT, imageurl);

                startActivity(Intent.createChooser(share, "Share link!"));
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Addcart();

            }
        });

        populatedata();
    }

    private class GetShopLogin extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         //   handler.sendEmptyMessage(SHOW_PROCESS_DIALOG);
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_SOAP_LOGIN, ServiceHandler.GET);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                //JSONObject jsonObject = null;
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    if (jsonObject.getString("error").equalsIgnoreCase("false")) {
                        JSONObject object = jsonObject.getJSONObject("message");
                        {
                            if (object != null) {
                                session_id = object.getString("session_id");
                                cart_id = object.getString("cart_id");
                                editor.putString("session_id", session_id);
                                editor.putString("cart_id", cart_id);
                                editor.commit();
                            }
                        }
                    }else if (jsonObject.getString("error").equalsIgnoreCase("true")) {
                        String mg=jsonObject.getString("message");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
          //  handler.sendEmptyMessage(HIDE_PROCESS_DIALOG);
            pDialog.dismiss();
       /*     if (pDialog.isShowing())
                pDialog.dismiss();
            populateSpinner()*/;
        }
    }


public void cart(){
    String image = images.get(0);
    Log.d("imagess", "" + image);
    dbHelper.getWritableDatabase();
    dbHelper.inserRecord(new Details(Product_id, Itemname, Saleprice, "1", image, Saleprice));
    try {
        mVibrator.vibrate(300);
    } catch (Exception e) {
        e.printStackTrace();
    }
    //showCustomAlert("Item added to  The Cart");
    ArrayList<Details> contact = dbHelper.getContact();
    int size = contact.size();
    titleTV.setText(String.valueOf(size));
    button1.setText("Go To Cart");
    button1.setBackgroundResource(R.drawable.edit_text_yellow);
    editor.putString("Product_id", Product_id);
   // editor.putString("qty", String.valueOf(qty));
    editor.commit();

}
    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION }, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                } else {
                    //not granted
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch(message.what){
                case SHOW_PROCESS_DIALOG :
                 //   mprogressBar.setVisibility(View.VISIBLE);
                    dialog.show();
                    dialog.setCancelable(false);
                    break;
                case HIDE_PROCESS_DIALOG :
                  //  mprogressBar.setVisibility(View.GONE);
                    dialog.hide();
                    break;
            }
            return false;
        }
    });

   /* @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        double latitude=-34;
        double longitude=151;
        LatLng sydney = new LatLng(latitude, longitude);
        locationAddress.getAddressFromLocation(latitude, longitude,
                getApplicationContext(), new GeocoderHandler());
        map.addMarker(new MarkerOptions().position(sydney).title(giiglemap_adress).position(sydney));

        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        map.getMaxZoomLevel();

    }*/
   private class AddToCart extends AsyncTask<String, String, String> {
       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           handler.sendEmptyMessage(SHOW_PROCESS_DIALOG);
          // categoriesList=new ArrayList<>();
       }
       @Override
       protected String doInBackground(String... params) {
           String return_text="";
           try{
               HttpClient httpClient=new DefaultHttpClient();
               HttpPost httpPost=new HttpPost("http://bshop2u.com/apirest/add_to_cart");
               JSONObject jsonObject=new JSONObject();
               jsonObject.accumulate("session_id",params[0]);
               jsonObject.accumulate("cart_id",params[1]);
               JSONArray jsonArray=new JSONArray();
               JSONObject jsonObject1=new JSONObject();
               jsonObject1.accumulate("product_id",params[2]);
               jsonObject1.accumulate("qty",params[3]);
                jsonArray.put(jsonObject1);
               jsonObject.accumulate("products",jsonArray);
               StringEntity httpiEntity=new StringEntity(jsonObject.toString());
               httpPost.setEntity(httpiEntity);
               HttpResponse httpResponse=httpClient.execute(httpPost);
               return_text=readResponse(httpResponse);
           }catch(Exception e){
               e.printStackTrace();
           }
           return return_text;
       }
       @Override
       protected void onPostExecute(String result) {
           Log.d("result===", "" + result);
           handler.sendEmptyMessage(HIDE_PROCESS_DIALOG);
           try {
               JSONObject jsonObject = new JSONObject(result);
               if (jsonObject.getString("error").equalsIgnoreCase("false")) {
                   String mg1=jsonObject.getString("message");
                   showCustomAlert(mg1);
                   cart();
               }else if (jsonObject.getString("error").equalsIgnoreCase("true")) {
                   String mg2=jsonObject.getString("message");
                   showCustomAlert(mg2);
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
   }

    class MyAsync extends AsyncTask<String,Void,String> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
           // mprogressBar.setIndeterminate(true);
        }
        @Override
        protected String doInBackground(String... strings) {
          //  handler.sendEmptyMessage(SHOW_PROCESS_DIALOG);
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
                Log.d("HELLO<><><><>",s);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return s;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();

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
            Log.d("return_texta", "" + return_text);
        } catch (Exception e) {

        }
        return return_text;


    }

    public void dataload(String url, final String lan){
       // handler.sendEmptyMessage(SHOW_PROCESS_DIALOG);
        RequestQueue que= Volley.newRequestQueue(getActivity());

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
Log.d("HELLODATA",s);

                JSONObject object=null;
                try {
                     object=new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject jsonObject= null;
                try {
                    jsonObject = object.getJSONObject("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ItemEntity itemEntity=new ItemEntity();
                Log.d("namedetail","   "+name);
//            try {
//                itemEntity.setName(jsonObject.getString("name"));
//                itemEntity.setId(jsonObject.getString("entity_id"));
//                itemEntity.setImage(jsonObject.getString("image_url"));
//                itemEntity.setMrpPrice(jsonObject.getString("regular_price_with_tax"));
//                itemEntity.setSalePrice(jsonObject.getString("final_price_with_tax"));
//                itemEntity.setDescription(jsonObject.getString("description"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            try {
//                itemEntity.setRatingcount(jsonObject.getString("total_reviews_count"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
                try {
                    Itemname=jsonObject.getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    Product_id=jsonObject.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    MrpPrice=jsonObject.getString("price");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    imageurl=jsonObject.getString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                try {
//                    Saleprice=jsonObject.getString("final_price_without_tax");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                try {
                    weight=jsonObject.getString("weight");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                try {
//                    brand=jsonObject.getString("brand");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                try {
                    Description=jsonObject.getString("description");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Description.split("<p>");

//                try {
//                    Material=jsonObject.getString("material");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            try {
//                RatingCount = jsonObject.getString("total_reviews_count");
//                Log.d("HELLO",RatingCount.toString());
//            }catch (Exception e){
//
////            }
//                try {
//                    Itemcolor=jsonObject.getString("color");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                try {
                    ItemStock=jsonObject.getString("stock");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    itemEntity.setName(jsonObject.getString("name"));
                    itemEntity.setId(jsonObject.getString("id"));
//                    itemEntity.setImage(jsonObject.getString("image_url"));
                    itemEntity.setMrpPrice(jsonObject.getString("price"));
//                    itemEntity.setSalePrice(jsonObject.getString("final_price_with_tax"));
                    itemEntity.setDescription(jsonObject.getString("description"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                name.setText(Itemname);
//                salePricetext.setText("MYR"+""+Saleprice);
                Regularpricetext.setText("MYR"+""+MrpPrice);
                String desc = Description.replace("<p>", "").replace("</p>", "");
                Descriptiontext.setText(desc);
//                material.setText(Material);
                stock.setText(ItemStock);
//                color.setText(Itemcolor);
                Weight.setText(weight);
//                brandshow.setText(brand);
//                Ratingtext.setText(RatingCount);
//                try {
//                    Image_url=jsonObject.getString("image_url");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                try {
                    id=jsonObject.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dotIndicator.setNumberOfItems(images.size());
                dotIndicator.setSelectedItem(0, false);
//                ratingBar.setRating(Float.valueOf(RatingCount));
//                // images=jsonObject.get(i).
                try {
                    images.add(jsonObject.getString("image"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    Latitude= (jsonObject.getString("latitude"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    Longitude=(jsonObject.getString("longitude"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if (!Latitude.equals("")) {
                        Log.d("&&&","************");
                        try {
                            latitude = Double.parseDouble(Latitude);
                            longitude = Double.parseDouble(Longitude);
                        }catch (Exception e){}
                    } else {
                        Log.d("&&&","&&&7&&&&");
                        latitude = 0;
                        longitude = 0;
                    }
                }catch(NullPointerException f){
                    f.printStackTrace();
                }
//                try {
//                    mode= jsonObject.getString("mode");
//                    if(mode.equals("")){
//
//                    }else if(mode.equals("Online")){
//                        Buttonlayout.setVisibility(View.VISIBLE);
//                        button1.setVisibility(View.VISIBLE);
//                        button2.setVisibility(View.VISIBLE);
//                    }else {
//                        Buttonlayout.setVisibility(View.GONE);
//                        button1.setVisibility(View.GONE);
//                        button2.setVisibility(View.GONE);
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                cotegory_list.add(itemEntity);
                adapter = new ImagePagerAdapter();
                viewPager.setAdapter(adapter);
//                mMapView.getMapAsync(new OnMapReadyCallback() {
//                    @Override
//                    public void onMapReady(GoogleMap mMap) {
//                        googleMap = mMap;
//
//
//                        // Add a marker in Sydney, Australia, and move the camera.
//
//                        LatLng LatLong = new LatLng(latitude, longitude);
//                        Log.d("valuein","  "+longitude+" "+latitude);
//                        locationAddress.getAddressFromLocation(latitude, longitude,
//                                getActivity(), new AddtoCartMobile.GeocoderHandler());
//
//                        googleMap.addMarker(new MarkerOptions().position(LatLong).title(giiglemap_adress).position(LatLong));
//
//                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLong));
//                        googleMap.animateCamera( CameraUpdateFactory.zoomTo( 8.0f ) );
//
//                        googleMap.getMaxZoomLevel();
//                    }
//                });
                if((cotegory_list.size()>0)){
                    layoutAddtocart.setVisibility(View.VISIBLE);

                    nonet_ll_cot.setVisibility(View.GONE);
                }else {
                    layoutAddtocart.setVisibility(View.GONE);
                    nonet_ll_cot.setVisibility(View.VISIBLE);
                }
                handler.sendEmptyMessage(HIDE_PROCESS_DIALOG);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.d("tag", "Error: " + error.getMessage());
                handler.sendEmptyMessage(HIDE_PROCESS_DIALOG);
            }
        }){

            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<String,String>();
                param.put("product_id", Entity_Id);
                param.put("lang",lan);
                return new JSONObject(param).toString().getBytes();
            }
        };
        que.add(request);
    }
    public void  loadData(){
        myAsync=new MyAsync();
        if (Utils.isNetworkAvailable(getActivity()) && myAsync.getStatus()!= AsyncTask.Status.RUNNING)
        {
            if (language=="") {
                language = "EN";
                dataload("http://bshop2u.com/apirest/get_bbb_product_detail",language);
            }else {
                dataload("http://bshop2u.com/apirest/get_bbb_product_detail",language);
            }
//            String serverURL = "http://bshop2u.com/api/rest/products/"+Entity_Id;

            // Create Object and call AsyncTask execute Method
//            new MyAsync().execute(serverURL);
        }else{
            Toast.makeText(getActivity(),"NO net avalable", Toast.LENGTH_LONG).show();
            layoutAddtocart.setVisibility(View.GONE);
             nonet_ll_cot.setVisibility(View.VISIBLE);
            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadData();
                }
            });
        }
    }

    private class ImagePagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context context = getActivity();
            ImageView imageView = new ImageView(context);
            int padding = context.getResources().getDimensionPixelSize(
                    R.dimen.padding_small);
            imageView.setPadding(padding, padding, padding, padding);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // imageView.setImageResource((images.get(position)));
            // imageView.setImageResource(Integer.parseInt(String.valueOf(images.get(0))));

            Picasso.with(context).load(images.get(position)).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  /*  Intent intentshow=new Intent(getActivity(),ImageShow.class);
                    intentshow.putExtra("ID" ,id);
                    startActivity(intentshow);*/
                }
            });
            // imageView.setImageResource(imageArrayIcon.getResourceId(mImages[position], -1));
            ((ViewPager) container).addView(imageView, 0);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            Log.d("addresdd"," "+locationAddress);
            // adresstext.setText(locationAddress);
            giiglemap_adress=locationAddress;
        }
    }


    //cutom Toast

    public void showCustomAlert(String value)
    {

        Context context = getActivity();
        // Create layout inflator object to inflate toast.xml file
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Call toast.xml file for toast layout
        View toastRoot = inflater.inflate(R.layout.toast, null);
        TextView textView=(TextView)toastRoot.findViewById(R.id.toastText);
        textView.setText(value);
        Toast toast = new Toast(context);


        // Set layout to toast
        toast.setView(toastRoot);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,
                0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();

    }

    @Override
    public void onPause() {
        super.onPause();
        dialog.dismiss();
        if ( myAsync.getStatus()== AsyncTask.Status.RUNNING)
        {
            myAsync.cancel(true);
        }
    }
    @Override
    public void onDestroy() {
        hidePDialog();
        super.onDestroy();
        dialog.dismiss();
        if ( myAsync.getStatus()== AsyncTask.Status.RUNNING)
        {
            myAsync.cancel(true);
        }
    }

    public void populatedata() {

        pDialog = new ProgressDialog(getContext());
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
                    Toast.makeText(getContext(), "You Have Some Connectivity Issue....", Toast.LENGTH_LONG).show();
                }
                hidePDialog();

            }
        });
        {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(movieReq);
        }

    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    public void Addcart(){

        pDialog = new ProgressDialog(getContext());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("session_id", sessionid);
        postParam.put("cart_id", cartid);
        postParam.put("product_id", Entity_Id);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(messg)
                            .setNegativeButton("ok", null)
                            .create()
                            .show();
                }else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Add to cart successfully!")
                            .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   button1.setEnabled(false);
                                    button1.setBackgroundColor(getResources().getColor(R.color.dcolor));

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
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jsonObjReq);

    }

//    class AddtoCart extends AsyncTask<String, Void, String> {
//
//        private ProgressDialog pDialog;
//
////        String username = musernametxt.getText().toString().trim();
////        String password = mpasswordtxt.getText().toString().trim();
////        String token = TokenSave.getInstance(LoginActivity.this).getDeviceToken();
//
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(getContext());
//            pDialog.setMessage("Please Wait ...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
//
//        }
//
//        @Override
//        protected String doInBackground(String... args) {
//            String s = "";
//
//
//            try {
//                HttpClient httpClient = new DefaultHttpClient();
//                HttpPost httpPost = new HttpPost("http://bshop2u.com/apirest/add_to_cart");
//                httpPost.setHeader("Content-type", "application/json");
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.accumulate("session_id", sessionid);
//                jsonObject.accumulate("cart_id", cartid);
//                jsonObject.accumulate("product_id", Entity_Id);
//                jsonObject.accumulate("qty", "1");
//
//                StringEntity stringEntity = new StringEntity(jsonObject.toString());
//                httpPost.setEntity(stringEntity);
//                HttpResponse httpResponse = httpClient.execute(httpPost);
//                HttpEntity httpEntity = httpResponse.getEntity();
//                s = readadsResponse(httpResponse);
//                Log.d("tag1", " " + s);
//            } catch (Exception exception) {
//                exception.printStackTrace();
//
//                Log.d("espone",exception.toString());
//
//            }
//
//            return s;
//
//        }
//        @Override
//        protected void onPostExecute(String json) {
//            super.onPostExecute(json);
//            pDialog.dismiss();
//            try {
//                JSONObject objone = new JSONObject(json);
//                boolean check  = objone.getBoolean("error");
//                if(check) {
//
//                    String messg = objone.getString("message");
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                    builder.setMessage(messg)
//                            .setNegativeButton("ok", null)
//                            .create()
//                            .show();
//                }else{
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                    builder.setMessage("Add to cart successfully!")
//                            .setNegativeButton("ok", null)
//                            .create()
//                            .show();
//
//
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    private String readadsResponse(HttpResponse httpResponse) {
//
//        InputStream is = null;
//        String return_text = "";
//        try {
//            is = httpResponse.getEntity().getContent();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
//            String line = "";
//            StringBuffer sb = new StringBuffer();
//            while ((line = bufferedReader.readLine()) != null) {
//                sb.append(line);
//            }
//            return_text = sb.toString();
//            Log.d("return1230", "" + return_text);
//        } catch (Exception e) {
//
//        }
//        return return_text;
//    }

}
