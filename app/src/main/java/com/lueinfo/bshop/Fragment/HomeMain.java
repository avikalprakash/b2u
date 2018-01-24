package com.lueinfo.bshop.Fragment;


import android.support.v4.app.Fragment;

/**
 * Created by lue on 16-03-2017.
 */
public class HomeMain extends Fragment {
        //implements AdapterView.OnItemClickListener {
/*    private static final int SHOW_PROCESS_DIALOG = 1;
    private static final int HIDE_PROCESS_DIALOG = 0;

    ProgressBar pb;
    MyAsync myAsync;
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
//RecyclerView listViewcategory;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_main, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DatabaseHelper(getActivity());
        handler1=new Handler();
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fill_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        sharedpreferences=this.getActivity().getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        language = sharedpreferences.getString(LANGUAGE,"");
        nonet_ll_cot=(LinearLayout)getActivity().findViewById(R.id.nonet_ll_cot);
        retry=(Button)getActivity().findViewById(R.id.retry);
        manager1=new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        manager2=new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        manager3=new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
      //  LinearLayout linearLayout1 = (LinearLayout) getActivity().findViewById(R.id.linearLayout3);
        ScrollView scrollView = (ScrollView) getActivity().findViewById(R.id.scrollView2);
      //  scrollView.getParent().requestChildFocus(scrollView);
        headertext=(TextView)getActivity().findViewById(R.id.headertext);
         indicator = (CirclePageIndicator)
                getActivity().findViewById(R.id.indicator);

        m=new Message();
        context=getActivity();
//lan=getActivity().getResources().getString(R.string.Home_Store_name);
//        headertext.setText("Home");
//        displayUserName = (TextView) getActivity().findViewById(R.id.displayUserName);
       // mView = new CatLoadingView();
      *//*  mAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        if (mAdapter == null) {
            Toast.makeText(getActivity(),"Your device do not support nfc",Toast.LENGTH_LONG).show();
            //finish();
            return;
        } else {
            showCustomAlert("Scaning Item Tag .....");
            mPendingIntent = PendingIntent.getActivity(getActivity(), 0, new Intent(getActivity(), getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            Intent i = new Intent(getActivity(), NFCActivity.class);
            startActivity(i);
        }*//*
//        YourAcount=(TextView)getActivity().findViewById(R.id.YourAcount);
//        YourAcount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                UserProfile userProfile=new UserProfile();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.container, userProfile);
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        });
        indicatorView=(AVLoadingIndicatorView)getActivity().findViewById(R.id.avi) ;
        session = new SessionManagement(getActivity());
        HashMap<String, String> user1 = session.getUserDetails();


        // namew
//        if (user1 != null) {
//            String name = user1.get(session.KEY_NAME);
//
//            // email
//            String email = user1.get(session.KEY_EMAIL);
//            displayUserName.setText(email);
//        }

        mViewPager = (ViewPager) getActivity().findViewById(R.id.imageView);


    *//*   mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               page = position;
           }

           @Override
           public void onPageSelected(int position) {

           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });*//*

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                page = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });





//    listView = (ListView) getActivity().findViewById(R.id.list);
//        setListViewHeightBasedOnChildren(listView);
        listViewproductpromotion=(RecyclerView) getActivity().findViewById(R.id.listViewHotDeals);
        listViewnewproducts=(RecyclerView) getActivity().findViewById(R.id.listnewproducts) ;
     //   listViewcategory=(RecyclerView) getActivity().findViewById(R.id.listcategory);

        View he = getActivity().findViewById(R.id.he);
//        LinearLayout linearLayout = (LinearLayout) he.findViewById(R.id.ll2);
        nonet_ll_home = (LinearLayout) getActivity().findViewById(R.id.nonet_ll_home);
        pb = (ProgressBar) getActivity().findViewById(R.id.pb_home);
        sharedpreferences=this.getActivity().getSharedPreferences(MyPref, Context.MODE_PRIVATE);

        language = sharedpreferences.getString(LANGUAGE,"");
//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MySearch search = new MySearch();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.container, search);
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        });
//        LinearLayout linearLayout2 = (LinearLayout) he.findViewById(R.id.ll);
//        linearLayout2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                View he = getActivity().findViewById(R.id.he);
//                LinearLayout linearLayout = (LinearLayout) he.findViewById(R.id.ll2);
//                LinearLayout linearLayout2 = (LinearLayout) he.findViewById(R.id.ll);
//
//                TextView fab = (TextView) getActivity().findViewById(R.id.fab2);
//                RelativeLayout re = (RelativeLayout) getActivity().findViewById(R.id.re);
//                //Utils.Animationviewtoview(getActivity(),textn,fab);
//                Utils.cartAnimation(fab);
//                CotegoriesFragment cotegoryFragment = new CotegoriesFragment();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                // Replace whatever is in the fragment_container view with this fragment,
//                // and add the transaction to the back stack so the user can navigate back
//                transaction.replace(R.id.container, cotegoryFragment);
//                transaction.addToBackStack(null);
//                // Commit the transaction
//                transaction.commit();
//            }
//        });

        TextView textView = (TextView) getActivity().findViewById(R.id.fab2);
        ArrayList<Details> contact = dbHelper.getContact();

        int size = contact.size();
        Log.d("size", "" + size);
//        textView.setText(String.valueOf(size));
        frame_head = (FrameLayout) getActivity().findViewById(R.id.frame_head);
//searchheader=(ImageView)getActivity().findViewById(R.id.search_header);
//        searchheader.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MySearch search = new MySearch();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.container, search);
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        });

     *//*   frame_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllCarts allCarts = new AllCarts();
                if (!allCarts.isInLayout()) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, allCarts);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
//                Toast.makeText(getActivity(),"CART FACILITY COMMONG SOON...",Toast.LENGTH_LONG).show();
            }
        });*//*

        loadData();
    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case SHOW_PROCESS_DIALOG:
                  //  indicatorView.show();
                  //  mView.show(getChildFragmentManager(), "");
                    dialog.show();
                    break;
                case HIDE_PROCESS_DIALOG:
                    //indicatorView.hide();
                  //  mView.dismiss();
                    dialog.hide();
                    break;
            }
            return false;
        }
    });
//
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


    //// for net
    class MyAsync extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            handler.sendEmptyMessage(SHOW_PROCESS_DIALOG);
            //pb.setIndeterminate(true);
        }

        @Override
        protected String doInBackground(String... params) {

            String return_text="";
            try{
                HttpClient httpClient=new DefaultHttpClient();
//                HttpPost httpPost=new HttpPost("http://54.67.107.248/jeptags/apirest/home");
                HttpPost httpPost=new HttpPost("http://bshop2u.com/apirest/home");
                JSONObject jsonObject=new JSONObject();
                jsonObject.accumulate("lang",params[0]);
                StringEntity httpiEntity=new StringEntity(jsonObject.toString());
                httpPost.setEntity(httpiEntity);
                HttpResponse httpResponse=httpClient.execute(httpPost);
                return_text=readResponse(httpResponse);
            }catch(Exception e){
                e.printStackTrace();
            }
            return return_text;
        }
        public String readResponse(HttpResponse res) {
            InputStream is=null;
            String return_text="";
            try {
                is=res.getEntity().getContent();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                String line="";
                StringBuffer sb=new StringBuffer();
                while ((line=bufferedReader.readLine())!=null)
                {
                    sb.append(line);
                }
                return_text=sb.toString();
            } catch (Exception e)
            {

            }
            return return_text;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            handler.sendEmptyMessage(HIDE_PROCESS_DIALOG);
//            Log.d("onPostExcute", "" + s);
            Log.d("HELLO<><><><>Response<>",s);
            cotegory_list.clear();
            Productpromotion.clear();
            newProducts.clear();
            ImageList.clear();
            String messageProductPromotion="";
            String messageSlide="";
            String messagedeptment="";
            String messagenewproduct="";
            try {
                JSONObject jsonObject=new JSONObject(s);
                String department=jsonObject.getString("shop_by_department");
               // String slideShow=jsonObject.getString("slideshow");
                String productPromotion=jsonObject.getString("product_promotions");
                String newproduct=jsonObject.getString("new_products");
                JSONObject homedepartment=new JSONObject(department);
             //   JSONObject slideshow=new JSONObject(slideShow);
                JSONObject productPromotions=new JSONObject(productPromotion);
                JSONObject newproducts=new JSONObject(newproduct);
                 messageProductPromotion=productPromotions.getString("message");
               //  messageSlide=slideshow.getString("message");
                 messagedeptment=homedepartment.getString("message");
                Log.d("HELLO<><><><>dept",messagedeptment);
                messagenewproduct=newproducts.getString("message");
                Log.d("HELLO<><><><>newprdt",messagenewproduct);


//                Log.d("cotegory_list", "size: " + cotegory_list.size());

            } catch (Exception e) {
                e.printStackTrace();
            }
// for new product with message (messagenewproduct)


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



            // for product PRomotion with message (messageProductPromotion)
            try {

                JSONArray productPromotionArry=new JSONArray(messageProductPromotion);

                for (int k=0;k<productPromotionArry.length();k++){
                    JSONObject jobject = productPromotionArry.getJSONObject(k);
                    ItemEntity itemEntity1 = new ItemEntity();
                    itemEntity1.setProductPromotionId(jobject.getString("entity_id"));
                    itemEntity1.setProductPromotionName(jobject.getString("name"));
                    itemEntity1.setProductPromotionImage(jobject.getString("image"));
                    itemEntity1.setMrpPrice(jobject.getString("price"));
                    itemEntity1.setSalePrice(jobject.getString("special_price"));

                    Productpromotion.add(itemEntity1);
                    String name=Productpromotion.get(k).getProductPromotionName();
                    Log.d("nmaes","  "+name);


                }
            }catch (Exception e){
                e.printStackTrace();
            }


            // for department with message (messagedeptment)
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


            //for slide shop with message (messageSlide)
            try {

                JSONArray slidShowArry = new JSONArray(messageSlide);
                for (int j = 0; j < slidShowArry.length(); j++) {
                    ItemEntity itemEntity2 = new ItemEntity();
                    JSONObject jobject = slidShowArry.getJSONObject(j);
                    itemEntity2.setSliderImage(jobject.getString("data-src"));
                    SliderImage.add(itemEntity2);
                    ImageList.add(jobject.getString("data-src"));

                }

            }catch (Exception e){
                e.printStackTrace();
            }



            if (Productpromotion.size() > 0) {
//                listView.setVisibility(View.VISIBLE);
                nonet_ll_cot.setVisibility(View.GONE);
                Log.d("cotegory_list", "size: " + Productpromotion.size());
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
                ProductPromotionAdapter adapter1=new ProductPromotionAdapter(getActivity(),Productpromotion);

                NewProductAdapter adapter2=new NewProductAdapter(getActivity(),newProducts);
                NewArrivalAdapter adapter3=new NewArrivalAdapter(getActivity(),cotegory_list);
                Log.d("HELLO<><><><>n", newProducts.toString());
                String s1=newProducts.get(1).getProductPromotionName();
                Log.d("HELLO<>",s1);
                Log.d("HELLO<><><><>p", Productpromotion.toString());
                String s2=Productpromotion.get(1).getProductPromotionName();
                Log.d("HELLO<>",s2);
                Log.d("HELLO<><><><>c", cotegory_list.toString());
                String s3=cotegory_list.get(1).getName();
                Log.d("HELLO<>",s3);

listViewproductpromotion.setLayoutManager(manager1);
                listViewproductpromotion.setAdapter(adapter1);


                listViewnewproducts.setAdapter(adapter2);
                listViewnewproducts.setLayoutManager(manager2);


                listViewcategory.setAdapter(adapter3);
                listViewcategory.setLayoutManager(manager3);

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

//                listViewproductpromotion.addOnItemTouchListener(new RecycyclerItem);
//listViewproductpromotion.setOnTouchListener(new View.OnTouchListener() {
//
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        if(event.getAction() == MotionEvent.ACTION_MOVE){
//            return true;
//        }
//        return false;
//    }
//});
//                listViewnewproducts.setOnTouchListener(new View.OnTouchListener() {
//
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        if(event.getAction() == MotionEvent.ACTION_MOVE){
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//
//                listViewcategory.setOnTouchListener(new View.OnTouchListener() {
//
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        if(event.getAction() == MotionEvent.ACTION_MOVE){
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//                listViewproductpromotion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                        String entityId=cotegory_list.get(i).getProductPromotionId();
//                        String image=cotegory_list.get(i).getProductPromotionImage();
//                        AddtoCartMobile addtoCartMobile = new AddtoCartMobile();
//                        android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("KeyValue",entityId);
//                        //  bundle.putString("Url","http://54.67.107.248/jeptags/api/rest/products?filter[1][attribute]=mode&filter[1][in]=238");
//                        addtoCartMobile.setArguments(bundle);
//                        transaction.replace(R.id.container, addtoCartMobile);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
//
//                    }
//                });
//
//                listViewnewproducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                        String entityId=cotegory_list.get(i).getProductPromotionId();
//                        String image=cotegory_list.get(i).getProductPromotionImage();
//                        AddtoCartMobile addtoCartMobile = new AddtoCartMobile();
//                        android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("KeyValue",entityId);
//                        //  bundle.putString("Url","http://54.67.107.248/jeptags/api/rest/products?filter[1][attribute]=mode&filter[1][in]=238");
//                        addtoCartMobile.setArguments(bundle);
//                        transaction.replace(R.id.container, addtoCartMobile);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
//
//                    }
//                });
//                listViewcategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        String id = Productpromotion.get(i).getCategory_id();
//                        Log.d("check4"," "+id);
//
//                        SubCategory subCategory=new SubCategory();
//                        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("Id", id);
//                        subCategory.setArguments(bundle);
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.replace(R.id.container,subCategory);
//                        fragmentTransaction.commit();
//
//                    }
//                });
            } else {
//                listView.setVisibility(View.GONE);
                nonet_ll_cot.setVisibility(View.VISIBLE);

            }
        }


    }

  // Async task ended





    public void  loadData(){
        myAsync=new MyAsync();
        if (Utils.isNetworkAvailable(getActivity()) && myAsync.getStatus()!= AsyncTask.Status.RUNNING)
        {
            if (language=="") {
                language = "EN";
                myAsync.execute(language);
            }else {
                myAsync.execute(language);
            }

        }else{

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
    }
    @Override
    public void onPause() {
        super.onPause();
        dialog.dismiss();
        if ( myAsync.getStatus()== AsyncTask.Status.RUNNING)
        {

            myAsync.cancel(true);
        }
        handler1.removeCallbacks(runnable);
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
//    public static void setListViewHeightBasedOnChildren(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            return;
//        }
//        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
//        int totalHeight = 0;
//        View view = null;
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            view = listAdapter.getView(i, view, listView);
//            if (i == 0) {
//                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LinearLayout.LayoutParams.WRAP_CONTENT));
//            }
//            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
//            totalHeight += view.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//        listView.requestLayout();
//    }
    @Override
    public void onResume() {
        super.onResume();
        handler1.postDelayed(runnable, delay);
    }
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

    }*/
}
