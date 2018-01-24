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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lueinfo.bshop.Adapter.Adapter;
import com.lueinfo.bshop.Adapter.AddtoCartMobile;
import com.lueinfo.bshop.Adapter.AndroidImageAdapternew;
import com.lueinfo.bshop.Adapter.NewArrivalAdapter;
import com.lueinfo.bshop.Adapter.Details;
import com.lueinfo.bshop.Adapter.ItemEntity;
import com.lueinfo.bshop.Adapter.NewProductAdapter;
import com.lueinfo.bshop.Adapter.PojoClass;
import com.lueinfo.bshop.Adapter.ProductPromotionAdapter;
import com.lueinfo.bshop.Adapter.RecyclerItemClickListener;
import com.lueinfo.bshop.Adapter.SessionManagement;
import com.lueinfo.bshop.Database.DatabaseHelper;
import com.lueinfo.bshop.MainActivity;
import com.lueinfo.bshop.OfferZoneDetails;
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
    float density;
    ProgressBar pb;
    ViewPager mViewPager;
    AVLoadingIndicatorView indicatorView;
    LinearLayout nonet_ll_home;
    CatLoadingView mView;
    LinearLayout nonet_ll_cot;
    Button retry;
    static FrameLayout frame_head;
    ImageView searchheader,mbackimage;
    DatabaseHelper dbHelper;
    private static int currentPage = 0;
    Message m;
    //    ListView listView;
    RecyclerView listViewHotDeals,listViewbestSelling;
    RecyclerView listNewArrival;
    RecyclerView listViewCategory;

    TextView displayUserName,YourAcount,headertext;
    AndroidImageAdapternew adapterView;
    ArrayList<ItemEntity> newArrival_list = new ArrayList<ItemEntity>();
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

    LinearLayout  mwfashiontxt,mmenfashion,mhealthyandbeautytxt,mmobileittxt,mhomelectxt,mhomsportstxt,msportstxt,
            mbabytxt,mgrocerytxt,mvouchertxt,mexcitingoffertxt;



    GridView mgridview;

    private Adapter mNameAdapter;

    private ArrayList<PojoClass> dummydata;
    int[] flags = new int[]{
            R.drawable.agarwoodbracelet,
            R.drawable.agarwoodcollectible,
            R.drawable.agarwoodessenseoil,
            R.drawable.agarwoodhealthy,
            R.drawable.agarwoodincense,
            R.drawable.agarwoodnecklace,
            R.drawable.agarwoodtea,
            R.drawable.agarwoodwooden,
    };


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
        listViewHotDeals=(RecyclerView) view.findViewById(R.id.listViewHotDeals);
        listViewbestSelling=(RecyclerView) view.findViewById(R.id.listbestSelling);
        listNewArrival=(RecyclerView) view.findViewById(R.id.listNewArrival);
       // listViewCategory=(RecyclerView) view.findViewById(R.id.listViewCategory);

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
       new NewArrival().execute();
        new ProductPromotion().execute();
        new BestSelling().execute();

//        mwfashiontxt = (LinearLayout) view.findViewById(R.id.wfashion);
//        mmenfashion = (LinearLayout) view.findViewById(R.id.mfashion);
//        mhealthyandbeautytxt = (LinearLayout) view.findViewById(R.id.healthytxt);
//        mmobileittxt = (LinearLayout) view.findViewById(R.id.mobileittxt);
//        mhomelectxt = (LinearLayout) view.findViewById(R.id.homeclectxt);
//        mhomsportstxt = (LinearLayout) view.findViewById(R.id.homelivingtxt);
//        msportstxt = (LinearLayout) view.findViewById(R.id.sportstxt);
//        mbabytxt = (LinearLayout) view.findViewById(R.id.babytxt);
//        mgrocerytxt = (LinearLayout) view.findViewById(R.id.grocerytxt);
//        mexcitingoffertxt = (LinearLayout) view.findViewById(R.id.excitoffertxt);
//        mvouchertxt = (LinearLayout) view.findViewById(R.id.evouchertxt);
//
//
//       mwfashiontxt.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               SubCategory subCategory=new SubCategory();
//               FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//               Bundle bundle = new Bundle();
//               bundle.putString("Id", "7");
//               bundle.putString("NAME","Women Fashion");
//               subCategory.setArguments(bundle);
//               fragmentTransaction.addToBackStack(null);
//               fragmentTransaction.replace(R.id.container,subCategory);
//               fragmentTransaction.commit();
//           }
//       });
//
//        mmenfashion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SubCategory subCategory=new SubCategory();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                Bundle bundle = new Bundle();
//                bundle.putString("Id", "8");
//                bundle.putString("NAME","Men Fashion");
//                subCategory.setArguments(bundle);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.replace(R.id.container,subCategory);
//                fragmentTransaction.commit();
//            }
//        });
//
//        mhealthyandbeautytxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SubCategory subCategory=new SubCategory();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                Bundle bundle = new Bundle();
//                bundle.putString("Id", "132");
//                bundle.putString("NAME","Health & Beauty");
//                subCategory.setArguments(bundle);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.replace(R.id.container,subCategory);
//                fragmentTransaction.commit();
//            }
//        });
//
//        mmobileittxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SubCategory subCategory=new SubCategory();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                Bundle bundle = new Bundle();
//                bundle.putString("Id", "5");
//                bundle.putString("NAME","Mobile, IT & Camera");
//                subCategory.setArguments(bundle);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.replace(R.id.container,subCategory);
//                fragmentTransaction.commit();
//            }
//        });
//
//        mhomelectxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SubCategory subCategory=new SubCategory();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                Bundle bundle = new Bundle();
//                bundle.putString("Id", "6");
//                bundle.putString("NAME","Home Electronics");
//                subCategory.setArguments(bundle);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.replace(R.id.container,subCategory);
//                fragmentTransaction.commit();
//            }
//        });
//
//        msportstxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SubCategory subCategory=new SubCategory();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                Bundle bundle = new Bundle();
//                bundle.putString("Id", "99");
//                bundle.putString("NAME","Sports & Automotive");
//                subCategory.setArguments(bundle);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.replace(R.id.container,subCategory);
//                fragmentTransaction.commit();
//            }
//        });
//
//        mbabytxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SubCategory subCategory=new SubCategory();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                Bundle bundle = new Bundle();
//                bundle.putString("Id", "120");
//                bundle.putString("NAME","Baby, Kids & Toys");
//                subCategory.setArguments(bundle);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.replace(R.id.container,subCategory);
//                fragmentTransaction.commit();
//            }
//        });
//
//        mgrocerytxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SubCategory subCategory=new SubCategory();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                Bundle bundle = new Bundle();
//                bundle.putString("Id", "128");
//                bundle.putString("NAME","Grocery");
//                subCategory.setArguments(bundle);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.replace(R.id.container,subCategory);
//                fragmentTransaction.commit();
//            }
//        });
//
//        mvouchertxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SubCategory subCategory=new SubCategory();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                Bundle bundle = new Bundle();
//                bundle.putString("Id", "136");
//                bundle.putString("NAME","E-voucher & More");
//                subCategory.setArguments(bundle);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.replace(R.id.container,subCategory);
//                fragmentTransaction.commit();
//            }
//        });
//        mvouchertxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SubCategory subCategory=new SubCategory();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                Bundle bundle = new Bundle();
//                bundle.putString("Id", "136");
//                bundle.putString("NAME","E-voucher & More");
//                subCategory.setArguments(bundle);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.replace(R.id.container,subCategory);
//                fragmentTransaction.commit();
//            }
//        });
//
//        mexcitingoffertxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SubCategory subCategory=new SubCategory();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                Bundle bundle = new Bundle();
//                bundle.putString("Id", "191");
//                bundle.putString("NAME","Exciting Offers");
//                subCategory.setArguments(bundle);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.replace(R.id.container,subCategory);
//                fragmentTransaction.commit();
//            }
//        });
//
//        mhomsportstxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SubCategory subCategory=new SubCategory();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                Bundle bundle = new Bundle();
//                bundle.putString("Id", "4");
//                bundle.putString("NAME","Home & Living");
//                subCategory.setArguments(bundle);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.replace(R.id.container,subCategory);
//                fragmentTransaction.commit();
//            }
//        });


        mgridview = (GridView) view.findViewById(R.id.gv);

        dummydata = new ArrayList<>();

        PojoClass c1 = new PojoClass(flags[0],"Agarwood bracelet");
        PojoClass c2 = new PojoClass(flags[1],"Agarwood collectible");
        PojoClass c3 = new PojoClass(flags[2],"Agarwood essenseoil");
        PojoClass c4 = new PojoClass(flags[3],"Agarwood healthy");
        PojoClass c5 = new PojoClass(flags[4],"Agarwood incense");
        PojoClass c6 = new PojoClass(flags[5],"Agarwood necklace");
        PojoClass c7 = new PojoClass(flags[6],"Agarwood tea");
        PojoClass c8 =  new PojoClass(flags[7],"Agarwood wooden");

        dummydata.add(c1);
        dummydata.add(c2);
        dummydata.add(c3);
        dummydata.add(c4);
        dummydata.add(c5);
        dummydata.add(c6);
        dummydata.add(c7);
        dummydata.add(c8);


        mNameAdapter = new Adapter(getContext(), dummydata);

        mgridview.setAdapter(mNameAdapter);

        mgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    SubCategory subCategory=new SubCategory();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
               Bundle bundle = new Bundle();
               bundle.putString("Id", "7");
               bundle.putString("NAME","Women Fashion");
               subCategory.setArguments(bundle);
               fragmentTransaction.addToBackStack(null);
               fragmentTransaction.replace(R.id.container,subCategory);
               fragmentTransaction.commit();

                }

                if (position == 1) {

                    SubCategory subCategory=new SubCategory();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("Id", "8");
                bundle.putString("NAME","Men Fashion");
                subCategory.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.container,subCategory);
                fragmentTransaction.commit();

                }



            }
        });




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
                try {
                   density = getResources().getDisplayMetrics().density;
                indicator.setRadius(5 * density);
                }catch (Exception e){}
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


    class NewArrival extends AsyncTask<String, Void, String> {

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
            String messagedeptment="";
            try {
                JSONObject jsonObject=new JSONObject(json);
                String department=jsonObject.getString("new_products");
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
                    itemEntity.setCategory_id(jobject.getString("id"));
                    itemEntity.setName(jobject.getString("name"));
                    itemEntity.setDescription(jobject.getString("description"));
                    itemEntity.setImage(jobject.getString("image"));
                    itemEntity.setPrice(jobject.getString("price"));
                   // itemEntity.setProduct_Url(jobject.getString("url"));
                    newArrival_list.add(itemEntity);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            NewArrivalAdapter adapter3=new NewArrivalAdapter(getActivity(),newArrival_list);
            listNewArrival.setAdapter(adapter3);
            listNewArrival.setLayoutManager(manager3);
            listNewArrival.addOnItemTouchListener(
                    new RecyclerItemClickListener(context, listNewArrival ,new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            String id = newArrival_list.get(position).getCategory_id();
                            String nme = newArrival_list.get(position).getName();
                            String price = newArrival_list.get(position).getPrice();
                            String desc = newArrival_list.get(position).getDescription();
                            String img = newArrival_list.get(position).getImage();
                            String url = newArrival_list.get(position).getProduct_Url();
                            Log.d("check4"," "+id);

                            Intent intent = new Intent(getActivity(),OfferZoneDetails.class);
                            intent.putExtra("name",nme);
                            intent.putExtra("price", price);
                            intent.putExtra("description",desc);
                            intent.putExtra("image",img);
                            intent.putExtra("offerid",id);
                           // intent.putExtra("url",url);
                            startActivity(intent);
                            getActivity().finish();

//                            AddtoCartMobile addtoCartMobile = new AddtoCartMobile();
//                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("KeyValue",id);
//                            //  bundle.putString("Url","http://54.67.107.248/jeptags/api/rest/products?filter[1][attribute]=mode&filter[1][in]=238");
//                            addtoCartMobile.setArguments(bundle);
//                            transaction.replace(R.id.container, addtoCartMobile);
//                            transaction.addToBackStack(null);
//                            transaction.commit();
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
              //  HttpPost httpPost = new HttpPost("http://bshop2u.com/apirest/home_product_promotions");
                HttpPost httpPost = new HttpPost("http://bshop2u.com/apirest/hot_deals_custom");
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
            listViewHotDeals.setAdapter(adapter1);
            listViewHotDeals.setLayoutManager(manager1);

            listViewHotDeals.addOnItemTouchListener(
                    new RecyclerItemClickListener(context, listViewHotDeals ,new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            // do whatever
                            String entityId=Productpromotion.get(position).getProductPromotionId();

                            String nme = Productpromotion.get(position).getProductPromotionName();
                            String price = Productpromotion.get(position).getMrpPrice();
                            String desc = Productpromotion.get(position).getDescription();
                            String img = Productpromotion.get(position).getProductPromotionImage();
                            Log.d("check4"," "+entityId);

                            Intent intent = new Intent(getActivity(),OfferZoneDetails.class);
                            intent.putExtra("name",nme);
                            intent.putExtra("price", price);
                            intent.putExtra("description",desc);
                            intent.putExtra("image",img);
                            intent.putExtra("offerid",entityId);
                            // intent.putExtra("url",url);
                            startActivity(intent);
                            getActivity().finish();



//                            Log.d("HELLO<><>", String.valueOf(position));
//                            Log.d("HELLO<><><>",entityId);
//                            String image=Productpromotion.get(position).getProductPromotionImage();
//                            AddtoCartMobile addtoCartMobile = new AddtoCartMobile();
//                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("KeyValue",entityId);
//                            //  bundle.putString("Url","http://54.67.107.248/jeptags/api/rest/products?filter[1][attribute]=mode&filter[1][in]=238");
//                            addtoCartMobile.setArguments(bundle);
//                            transaction.replace(R.id.container, addtoCartMobile);
//                            transaction.addToBackStack(null);
//                            transaction.commit();
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            // do whatever
                        }
                    })
            );
        }
    }


    class BestSelling extends AsyncTask<String, Void, String> {

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
            //    HttpPost httpPost = new HttpPost("http://bshop2u.com/apirest/new_products");
                HttpPost httpPost = new HttpPost("http://bshop2u.com/apirest/best_selling_products_custom");
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
               /* String newproduct=jsonObject.getString("new_products");
                JSONObject newproducts=new JSONObject(newproduct);*/
                messagenewproduct=jsonObject.getString("message");
                Log.d("HELLO<><><><>newprdt",messagenewproduct);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {

                JSONArray newzproductArry=new JSONArray(messagenewproduct);

                for (int k=0;k<newzproductArry.length();k++){
                    JSONObject jobject = newzproductArry.getJSONObject(k);
                    ItemEntity itemEntity1 = new ItemEntity();
                    itemEntity1.setProductPromotionId(jobject.getString("id"));
                   // itemEntity1.setProductPromotionId(jobject.getString("entity_id"));
                    itemEntity1.setProductPromotionName(jobject.getString("name"));
                    itemEntity1.setDescription(jobject.getString("description"));
                    itemEntity1.setProductPromotionImage(jobject.getString("image"));
                    itemEntity1.setMrpPrice(jobject.getString("price"));
                   // itemEntity1.setSalePrice(jobject.getString("special_price"));

                    newProducts.add(itemEntity1);
                    Log.d("HELLO<>newprdtaray",newProducts.toString());

                    String id=newProducts.get(k).getProductPromotionId();
                    Log.d("HELLO<>ID<><>","  "+id);


                }
            }catch (Exception e){
                e.printStackTrace();
            }
            NewProductAdapter adapter2=new NewProductAdapter(getActivity(),newProducts);
            listViewbestSelling.setAdapter(adapter2);
            listViewbestSelling.setLayoutManager(manager2);

            listViewbestSelling.addOnItemTouchListener(
                    new RecyclerItemClickListener(context, listViewHotDeals ,new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            // do whatever
                            String entityId=newProducts.get(position).getProductPromotionId();

                            String nme = newProducts.get(position).getProductPromotionName();
                            String price = newProducts.get(position).getMrpPrice();
                            String desc = newProducts.get(position).getDescription();
                            String img = newProducts.get(position).getProductPromotionImage();
                            String url = newProducts.get(position).getProduct_Url();
                            Log.d("check4"," "+entityId);

                            Intent intent = new Intent(getActivity(),OfferZoneDetails.class);
                            intent.putExtra("name",nme);
                            intent.putExtra("price", price);
                            intent.putExtra("description",desc);
                            intent.putExtra("image",img);
                            intent.putExtra("offerid",entityId);
                            // intent.putExtra("url",url);
                            startActivity(intent);
                            getActivity().finish();



//                            String image=newArrival_list.get(position).getProductPromotionImage();
//                            AddtoCartMobile addtoCartMobile = new AddtoCartMobile();
//                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("KeyValue",entityId);
//                            //  bundle.putString("Url","http://54.67.107.248/jeptags/api/rest/products?filter[1][attribute]=mode&filter[1][in]=238");
//                            addtoCartMobile.setArguments(bundle);
//                            transaction.replace(R.id.container, addtoCartMobile);
//                            transaction.addToBackStack(null);
//                            transaction.commit();
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
