package com.lueinfo.bshop.Fragment;


import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lueinfo.bshop.Adapter.AddtoCartMobile;
import com.lueinfo.bshop.Adapter.AndroidImageAdapternew;
import com.lueinfo.bshop.Adapter.CategoryAdapter;
import com.lueinfo.bshop.Adapter.Details;
import com.lueinfo.bshop.Adapter.ItemEntity;
import com.lueinfo.bshop.Adapter.NewProductAdapter;
import com.lueinfo.bshop.Adapter.ProductPromotionAdapter;
import com.lueinfo.bshop.Adapter.RecyclerItemClickListener;
import com.lueinfo.bshop.Adapter.ServiceHandler;
import com.lueinfo.bshop.Adapter.SessionManagement;
import com.lueinfo.bshop.Adapter.Utils;
import com.lueinfo.bshop.Database.DatabaseHelper;
import com.lueinfo.bshop.MainActivity;
import com.lueinfo.bshop.R;
import com.roger.catloadinglibrary.CatLoadingView;
import com.viewpagerindicator.CirclePageIndicator;
import com.wang.avi.AVLoadingIndicatorView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

//
public class HomeFragment extends Fragment {

    private static final int SHOW_PROCESS_DIALOG = 1;
    private static final int HIDE_PROCESS_DIALOG = 0;

    ProgressBar pb;
    ViewPager mViewPager;
    AVLoadingIndicatorView indicatorView;
    LinearLayout nonet_ll_home;
    CatLoadingView mView;
    LinearLayout nonet_ll_cot;
    Button retry;
    static FrameLayout frame_head;
    ImageView searchheader;
    DatabaseHelper dbHelper;
    private static int currentPage = 0;
    Message m;
    //    ListView listView;
    RecyclerView listViewproductpromotion,listViewnewproducts;
    RecyclerView listViewcategory;

    TextView displayUserName,YourAcount,headertext;
    AndroidImageAdapternew adapterView;
    ArrayList<ItemEntity> cotegory_list = new ArrayList<ItemEntity>();
    ArrayList<ItemEntity> Productpromotion = new ArrayList<ItemEntity>();
    ArrayList<ItemEntity> SliderImage = new ArrayList<ItemEntity>();
    ArrayList<ItemEntity> newProducts=new ArrayList<ItemEntity>();
    SessionManagement session;
    LinearLayoutManager manager1,manager2,manager3;
    ArrayList<String> ImageList = new ArrayList<>();
    Context context;
    Timer timer;
    int count = 0;
    private Handler handler2;
    private int delay = 5000;
    private int page = 0;
    Handler handler1;
    Runnable runnable;
    Dialog dialog;
    public static final String LANGUAGE = "log";
    public static final String MyPref = "MyPref";
    SharedPreferences sharedpreferences;
    String language;
    String catLang="EN";
    String lan;
    CirclePageIndicator indicator;
    private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_main, container, false);
        dbHelper = new DatabaseHelper(getActivity());
        handler1=new Handler();
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fill_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        sharedpreferences=this.getActivity().getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        language = sharedpreferences.getString(LANGUAGE,"");
        nonet_ll_cot=(LinearLayout)view.findViewById(R.id.nonet_ll_cot);
        retry=(Button)view.findViewById(R.id.retry);
        manager1=new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        manager2=new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        manager3=new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        //  LinearLayout linearLayout1 = (LinearLayout) getActivity().findViewById(R.id.linearLayout3);
        ScrollView scrollView = (ScrollView) view.findViewById(R.id.scrollView2);
        //  scrollView.getParent().requestChildFocus(scrollView);
        headertext=(TextView)view.findViewById(R.id.headertext);
        indicator = (CirclePageIndicator)
                view.findViewById(R.id.indicator);
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getActivity().getSystemService(getActivity().getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  )
             loadData();
           // Toast.makeText(getActivity(), " Not Connected ", Toast.LENGTH_LONG).show();


        m=new Message();
        context=getActivity();
        indicatorView=(AVLoadingIndicatorView)view.findViewById(R.id.avi) ;
        session = new SessionManagement(getActivity());
        HashMap<String, String> user1 = session.getUserDetails();
        mViewPager = (ViewPager) view.findViewById(R.id.imageView);
        listViewproductpromotion=(RecyclerView) view.findViewById(R.id.listViewproducts);
        listViewnewproducts=(RecyclerView) view.findViewById(R.id.listnewproducts) ;
        listViewcategory=(RecyclerView) view.findViewById(R.id.listcategory);
        View he = view.findViewById(R.id.he);
        nonet_ll_home = (LinearLayout) view.findViewById(R.id.nonet_ll_home);
        pb = (ProgressBar) view.findViewById(R.id.pb_home);
        sharedpreferences=this.getActivity().getSharedPreferences(MyPref, Context.MODE_PRIVATE);

        language = sharedpreferences.getString(LANGUAGE,"");
        TextView textView = (TextView) view.findViewById(R.id.fab2);
        ArrayList<Details> contact = dbHelper.getContact();

        int size = contact.size();
        Log.d("size", "" + size);
        frame_head = (FrameLayout) view.findViewById(R.id.frame_head);

       indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {
               page = position;
           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });
       new SlidingImage().execute();
       new ShopByDep().execute();
        new ProductPromotion().execute();
        new NewProduct().execute();
        return  view;
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case SHOW_PROCESS_DIALOG:
                    dialog.show();
                    break;
                case HIDE_PROCESS_DIALOG:
                    dialog.hide();
                    break;
            }
            return false;
        }
    });


    class SlidingImage extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://bshop2u.com/apirest/homeSlider");
                httpPost.setHeader("Content-type", "application/json");
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("lang", language);


                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                s = readadsResponse(httpResponse);
                Log.d("tag1", " " + s);
            } catch (Exception exception) {
                exception.printStackTrace();

                Log.d("espone",exception.toString());

            }

            return s;

        }
        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            pDialog.dismiss();
            String messageSlide;
            ImageList.clear();
            try {
                JSONObject jsonObject=new JSONObject(json);
                String department=jsonObject.getString("slideshow");
                    JSONObject object =new JSONObject(department);
                    messageSlide=object.getString("message");
                    JSONArray slidShowArry = new JSONArray(messageSlide);
                    for (int i=0; i<slidShowArry.length(); i++) {
                        ItemEntity itemEntity2 = new ItemEntity();
                        JSONObject jobject = slidShowArry.getJSONObject(i);
                        itemEntity2.setSliderImage(jobject.getString("data-src"));
                        SliderImage.add(itemEntity2);
                        ImageList.add(jobject.getString("data-src"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (ImageList.size() > 0) {
                adapterView = new AndroidImageAdapternew(getActivity(), ImageList);
                mViewPager.setAdapter(adapterView);
                indicator.setViewPager(mViewPager);
                final float density = getResources().getDisplayMetrics().density;
                indicator.setRadius(5 * density);
                runnable = new Runnable() {
                    public void run() {
                        if (adapterView.getCount() == page) {
                            page = 0;
                        } else {
                            page++;
                        }
                        mViewPager.setCurrentItem(page, true);
                        handler1.postDelayed(this, delay);
                    }
                };
            }
        }
    }


    private String readadsResponse(HttpResponse httpResponse) {

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
            Log.d("return1230", "" + return_text);
        } catch (Exception e) {

        }
        return return_text;
    }


    class ShopByDep extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");
            pDialog.setIndeterminate(false);
           // pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://bshop2u.com/apirest/shop_by_department");
                httpPost.setHeader("Content-type", "application/json");
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("lang", language);

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                s = readadsResponse(httpResponse);
                Log.d("tag1", " " + s);
            } catch (Exception exception) {
                exception.printStackTrace();

                Log.d("espone",exception.toString());

            }

            return s;

        }
        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            pDialog.dismiss();
            String messagedeptment="";
            try {
                JSONObject jsonObject=new JSONObject(json);
                String department=jsonObject.getString("shop_by_department");
                JSONObject homedepartment=new JSONObject(department);
                messagedeptment=homedepartment.getString("message");
                Log.d("HELLO<><><><>dept",messagedeptment);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONArray jsonArray =new JSONArray(messagedeptment);


                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.d("object", "kk1111xxxxx " + jsonArray.getJSONObject(i).toString());
                    JSONObject jobject = jsonArray.getJSONObject(i);
                    ItemEntity itemEntity = new ItemEntity();
                    itemEntity.setCategory_id(jobject.getString("entity_id"));
                    itemEntity.setName(jobject.getString("name"));
                    itemEntity.setImage(jobject.getString("image_url"));
                    cotegory_list.add(itemEntity);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            CategoryAdapter adapter3=new CategoryAdapter(getActivity(),cotegory_list);
            listViewcategory.setAdapter(adapter3);
            listViewcategory.setLayoutManager(manager3);
            listViewcategory.addOnItemTouchListener(
                    new RecyclerItemClickListener(context, listViewproductpromotion ,new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            String id = cotegory_list.get(position).getCategory_id();
                            Log.d("check4"," "+id);

                            SubCategory subCategory=new SubCategory();
                            FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                            Bundle bundle = new Bundle();
                            bundle.putString("Id", id);
                            subCategory.setArguments(bundle);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.replace(R.id.container,subCategory);
                            fragmentTransaction.commit();
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            // do whatever
                        }
                    })
            );
        }
    }

    class ProductPromotion extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");
            pDialog.setIndeterminate(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://bshop2u.com/apirest/home_product_promotions");
                httpPost.setHeader("Content-type", "application/json");
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("lang", language);

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                s = readadsResponse(httpResponse);
                Log.d("tag1", " " + s);
            } catch (Exception exception) {
                exception.printStackTrace();

                Log.d("espone",exception.toString());

            }

            return s;

        }
        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            pDialog.dismiss();
            try {
                JSONObject objone = new JSONObject(json);
                boolean check  = objone.getBoolean("error");
                if(check) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Please Try Again")
                            .setNegativeButton("ok", null)
                            .create()
                            .show();
                }else{
                    JSONArray jsonArray = objone.getJSONArray("message");
                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject jobject = jsonArray.getJSONObject(i);
                        ItemEntity itemEntity1 = new ItemEntity();
                        itemEntity1.setProductPromotionId(jobject.getString("id"));
                        itemEntity1.setProductPromotionName(jobject.getString("name"));
                        itemEntity1.setProductPromotionImage(jobject.getString("image"));
                        itemEntity1.setMrpPrice(jobject.getString("price"));
                       // itemEntity1.setSalePrice(jobject.getString("special_price"));
                        itemEntity1.setDescription(jobject.getString("description"));

                        Productpromotion.add(itemEntity1);
                        String name=Productpromotion.get(i).getProductPromotionName();
                        Log.d("nmaes","  "+name);



                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            ProductPromotionAdapter adapter1=new ProductPromotionAdapter(getActivity(),Productpromotion);
            listViewproductpromotion.setAdapter(adapter1);
            listViewproductpromotion.setLayoutManager(manager1);

            listViewproductpromotion.addOnItemTouchListener(
                    new RecyclerItemClickListener(context, listViewproductpromotion ,new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            // do whatever
                            String entityId=Productpromotion.get(position).getProductPromotionId();
                            Log.d("HELLO<><>", String.valueOf(position));
                            Log.d("HELLO<><><>",entityId);
                            String image=cotegory_list.get(position).getProductPromotionImage();
                            AddtoCartMobile addtoCartMobile = new AddtoCartMobile();
                            android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            Bundle bundle = new Bundle();
                            bundle.putString("KeyValue",entityId);
                            //  bundle.putString("Url","http://54.67.107.248/jeptags/api/rest/products?filter[1][attribute]=mode&filter[1][in]=238");
                            addtoCartMobile.setArguments(bundle);
                            transaction.replace(R.id.container, addtoCartMobile);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            // do whatever
                        }
                    })
            );
        }
    }


    class NewProduct extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            pDialog.setCancelable(false);

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://bshop2u.com/apirest/new_products");
                httpPost.setHeader("Content-type", "application/json");
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("lang", language);

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                s = readadsResponse(httpResponse);
                Log.d("tag1", " " + s);
            } catch (Exception exception) {
                exception.printStackTrace();

                Log.d("espone",exception.toString());

            }

            return s;

        }
        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            pDialog.dismiss();
            String messagenewproduct="";
            try {
                JSONObject jsonObject=new JSONObject(json);
                String newproduct=jsonObject.getString("new_products");
                JSONObject newproducts=new JSONObject(newproduct);
                messagenewproduct=newproducts.getString("message");
                Log.d("HELLO<><><><>newprdt",messagenewproduct);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {

                JSONArray newzproductArry=new JSONArray(messagenewproduct);

                for (int k=0;k<newzproductArry.length();k++){
                    JSONObject jobject = newzproductArry.getJSONObject(k);
                    ItemEntity itemEntity1 = new ItemEntity();
                    itemEntity1.setProductPromotionId(jobject.getString("entity_id"));
                    itemEntity1.setProductPromotionName(jobject.getString("name"));
                    itemEntity1.setProductPromotionImage(jobject.getString("image"));
                    itemEntity1.setMrpPrice(jobject.getString("price"));
                    itemEntity1.setSalePrice(jobject.getString("special_price"));

                    newProducts.add(itemEntity1);
                    Log.d("HELLO<>newprdtaray",newProducts.toString());

                    String id=newProducts.get(k).getProductPromotionId();
                    Log.d("HELLO<>ID<><>","  "+id);


                }
            }catch (Exception e){
                e.printStackTrace();
            }
            NewProductAdapter adapter2=new NewProductAdapter(getActivity(),newProducts);
            listViewnewproducts.setAdapter(adapter2);
            listViewnewproducts.setLayoutManager(manager2);

            listViewnewproducts.addOnItemTouchListener(
                    new RecyclerItemClickListener(context, listViewproductpromotion ,new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            // do whatever
                            String entityId=newProducts.get(position).getProductPromotionId();
                            String image=cotegory_list.get(position).getProductPromotionImage();
                            AddtoCartMobile addtoCartMobile = new AddtoCartMobile();
                            android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            Bundle bundle = new Bundle();
                            bundle.putString("KeyValue",entityId);
                            //  bundle.putString("Url","http://54.67.107.248/jeptags/api/rest/products?filter[1][attribute]=mode&filter[1][in]=238");
                            addtoCartMobile.setArguments(bundle);
                            transaction.replace(R.id.container, addtoCartMobile);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            // do whatever
                        }
                    })
            );
        }
    }



    public void  loadData(){
            Toast.makeText(getActivity(),"NO net avalable", Toast.LENGTH_LONG).show();

            nonet_ll_cot.setVisibility(View.VISIBLE);
            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("chugdgghci","@@@@@@@@@@@@@@@@@");
                    Intent intent=new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            });
        }

    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getActivity().getSystemService(getActivity().getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet

            Toast.makeText(getActivity(), " Connected ", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Toast.makeText(getActivity(), " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
}
