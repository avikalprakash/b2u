package com.lueinfo.bshop.Adapter;

import android.app.Dialog;
import android.content.Context;
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
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import com.lueinfo.bshop.Database.DatabaseHelper;
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

import static android.content.Context.MODE_PRIVATE;

public class AddtoCartMobileMan extends Fragment {

    private static final int SHOW_PROCESS_DIALOG = 1;
    private static final int HIDE_PROCESS_DIALOG = 0;
    private final HttpClient Client = new DefaultHttpClient();
    MyAsync myAsync;
//    private String URL_SOAP_LOGIN = "http://54.67.107.248/jeptags/apirest/soap_login";
private String URL_SOAP_LOGIN = "http://bshop2u.com/apirest/soap_login";
    private String Content;
    ProgressBar pb;
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
    public AddtoCartMobileMan() {
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
//                if(button1.getText().equals(getString(R.string.AddtoCart))) {
//
//                    new AddToCart().execute(session_id, cart_id, Product_id, String.valueOf(qty));
//                }else {
//                    AllCarts allCarts = new AllCarts();
//                    if (!allCarts.isInLayout()) {
//                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.container, allCarts);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
//                    }
//                }
Toast.makeText(getActivity(),"CART FACILITY NOT AVAILABLE NOW .COMMING SOON", Toast.LENGTH_LONG).show();

            }
        });
    }

    private class GetShopLogin extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            handler.sendEmptyMessage(SHOW_PROCESS_DIALOG);
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
            handler.sendEmptyMessage(HIDE_PROCESS_DIALOG);
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           // mprogressBar.setIndeterminate(true);
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
                Log.d("HELLO<><><><>",s);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return s;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


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
        handler.sendEmptyMessage(SHOW_PROCESS_DIALOG);
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
                Descriptiontext.setText(Description);
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
        super.onDestroy();
        dialog.dismiss();
        if ( myAsync.getStatus()== AsyncTask.Status.RUNNING)
        {
            myAsync.cancel(true);
        }
    }
}
