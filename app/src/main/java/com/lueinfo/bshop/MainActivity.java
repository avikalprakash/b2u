package com.lueinfo.bshop;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lueinfo.bshop.Adapter.AndroidImageAdapternew;
import com.lueinfo.bshop.Adapter.BottomNavigationViewHelper;
import com.lueinfo.bshop.Adapter.ItemEntity;
import com.lueinfo.bshop.Adapter.LanguageDatabase;
import com.lueinfo.bshop.Adapter.Message;
import com.lueinfo.bshop.Adapter.ReadLanguage;
import com.lueinfo.bshop.Adapter.SessionManagement;
import com.lueinfo.bshop.Database.DatabaseHelper;
import com.lueinfo.bshop.Fragment.ContactFragment;
import com.lueinfo.bshop.Fragment.CotegoriesFragment;
import com.lueinfo.bshop.Fragment.EventFragment;
import com.lueinfo.bshop.Fragment.HomeFragment;
import com.lueinfo.bshop.Fragment.IntroFragment;
import com.lueinfo.bshop.Fragment.LogOut;
import com.lueinfo.bshop.Fragment.NotificationFragment;
import com.lueinfo.bshop.Fragment.OfferZone;
import com.lueinfo.bshop.Fragment.ProfileFragment;
import com.lueinfo.bshop.Fragment.PromotionFragment;
import com.lueinfo.bshop.Fragment.QrFragment;
import com.lueinfo.bshop.Fragment.TodaysDeal;
import com.lueinfo.bshop.RitsActivity.CartFragment;
import com.lueinfo.bshop.RitsActivity.CartProducts;
import com.lueinfo.bshop.RitsActivity.PointsFrag;
import com.lueinfo.bshop.RitsActivity.ScanQrActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
//

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    AlertDialog alertDialog;
    HomeFragment home;
    ImageView headerimage;
    ViewPager viewpager1;
    DatabaseHelper databaseHelper;
    DrawerLayout drawer;
    SessionManagement session;
    TextView headertext, username1,username2;
    Message m;
    View hView;
    NavigationView navigationView;
    static FrameLayout frame_head;
    String user_id="";
    public static final int REQUEST_PERMISSION_CODE=1;
    int i=1;
    private List<ItemEntity> order_des_List = new ArrayList<>();
    SessionManagement sessionManagement;
    String name;
    TextView textView;
    String log="";
    String count;
    RelativeLayout searchLayout;

    SharedPreferences sharedPrefotpaccheckst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbarheader);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(null);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //  viewpager1=(ViewPager)findViewById(R.id.viewpagertab);
        //  setUpViewPager(viewpager1);

/*        TabLayout tablayout=(TabLayout)findViewById(R.id.tablayout) ;
        tablayout.setupWithViewPager(viewpager1);
tablayout.addTab(tablayout.newTab());
        tablayout.addTab(tablayout.newTab());
        tablayout.addTab(tablayout.newTab());
        tablayout.addTab(tablayout.newTab());
        tablayout.addTab(tablayout.newTab());
        tablayout.addTab(tablayout.newTab());
        tablayout.addTab(tablayout.newTab());
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);*/


        headerimage=(ImageView)findViewById(R.id.search_header);
        headerimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(i);
            }
        });
        databaseHelper=new DatabaseHelper(MainActivity.this);
        headertext=(TextView)findViewById(R.id.headertext);
       // searchLayout=(RelativeLayout) findViewById(R.id.search_header);
        headertext.setText("Bshop");
        username1=(TextView)findViewById(R.id.username1);
        username2=(TextView)findViewById(R.id.username2);

//        session = new SessionManagement(this);
//        if(sessionManagement.isLoggedIn()) {
//            HashMap<String, String> user1 = session.getUserDetails();
//
//
//            // names
//            if (user1 != null) {
//               // String name = user1.get(session.KEY_NAME);
//                String name = "abc";
//               // m.log("HELLO<><><>" + name);
//                username2.setText(name);
//                username2.setVisibility(View.VISIBLE);
//                // email
////                String email = user1.get(session.KEY_EMAIL);
////                m.log("EMAIL<><><><>"+email);
////            navtext.setText(email);
//            }
//        }
//        navtext=(TextView)findViewById(R.id.username);
//       Log.d("HELLO<><><><>",String.valueOf(navtext));



//        TextView fab = (TextView) findViewById(R.id.fab);
           textView = (TextView)findViewById(R.id.fab2);


//        ArrayList<Details> contact = databaseHelper.getContact();
//        m=new Message();
//        sessionManagement = new SessionManagement(getApplicationContext());
//        int size = contact.size();
//        Log.d("size", "" + size);
//        textView.setText(String.valueOf(size));
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        sessionManagement = new SessionManagement(getApplicationContext());
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawer.setDrawerListener(toggle);
        //
        toolbar1.setNavigationIcon(R.drawable.ic_dehaze);
        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        //  toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        hView = navigationView.getHeaderView(0);
        navigationView.setItemIconTintList(null);
        navigationView.getMenu().findItem(R.id.home).setVisible(true);
        if(sessionManagement.isLoggedIn()) {
            navigationView.getMenu().findItem(R.id.Logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.myaccount).setVisible(true);
            navigationView.getMenu().findItem(R.id.myorders).setVisible(true);
            navigationView.getMenu().findItem(R.id.mywishlist).setVisible(true);
            navigationView.getMenu().findItem(R.id.mycart).setVisible(true);
            navigationView.getMenu().findItem(R.id.login).setVisible(false);
        }else {
            navigationView.getMenu().findItem(R.id.Logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.login).setVisible(true);
            navigationView.getMenu().findItem(R.id.myaccount).setVisible(false);
            navigationView.getMenu().findItem(R.id.myorders).setVisible(false);
            navigationView.getMenu().findItem(R.id.mywishlist).setVisible(false);
            navigationView.getMenu().findItem(R.id.mycart).setVisible(false);

        }
        View header = navigationView.getHeaderView(0);
/*       TextView texthead = (TextView) header.findViewById(R.id.texthead_signup);
        texthead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SignupActivity.class);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });*/

        home = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, home);
        transaction.commit();
        frame_head = (FrameLayout)findViewById(R.id.frame_head);


        frame_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                CartFragment cartFragment = new CartFragment();
//                FragmentTransaction carttransaction123 = getSupportFragmentManager().beginTransaction();
//                carttransaction123.replace(R.id.container, cartFragment);
//                carttransaction123.addToBackStack(null);
//                carttransaction123.commit();
//                headertext.setText("Cart");

                if(sessionManagement.isLoggedIn())
                {
                    Intent intent = new Intent(MainActivity.this,CartProducts.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
                else
                    {
                        Toast.makeText(getApplicationContext(),"Please login first",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

            }
        });
        //hide header

        checkRunTimePermission();


        if(sessionManagement.isLoggedIn())
        {
            ImageSLideShow();

//            sharedPrefotpaccheckst = getSharedPreferences("MyPreffacultyaccheckstudent", Context.MODE_PRIVATE);
//            count = sharedPrefotpaccheckst.getString("count",null);
//            Log.d("kjhbibsivdb",""+count);
//            textView.setText(count);


        }else
            {
                textView.setText("0");
            }

    }

    @Override
    protected void onRestart() {

        super.onRestart();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
//                case R.id.navigation_homeqwert:
//
//                    addHomeFragment();
//
//                    return true;
                case R.id.navigation_setting:

                    home = new HomeFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, home);
                    transaction.commit();

                    return true;

                case R.id.navigation_com:

                    CotegoriesFragment cotegoryFragment = new CotegoriesFragment();
                    FragmentTransaction transaction123 = getSupportFragmentManager().beginTransaction();
                    transaction123.replace(R.id.container, cotegoryFragment);
                    transaction123.addToBackStack(null);
                    transaction123.commit();
                    headertext.setText("CATEGORY");


                    return true;
                case R.id.navigation_calender:

                    PromotionFragment promotionFragment = new PromotionFragment();
                    FragmentTransaction transactionpromo = getSupportFragmentManager().beginTransaction();
                    transactionpromo.replace(R.id.container, promotionFragment);
                    transactionpromo.addToBackStack(null);
                    transactionpromo.commit() ;
                    headertext.setText("Event");

                    return true;

                case R.id.navigation_c:

                    sessionManagement = new SessionManagement(MainActivity.this);
                    if(sessionManagement.isLoggedIn()) {
                        HashMap<String, String> user1 = sessionManagement.getUserDetails();

                        if (user1 != null) {
                            try {
                                ProfileFragment profileFragment = new ProfileFragment();
                                FragmentTransaction transactionprofile = getSupportFragmentManager().beginTransaction();
                                transactionprofile.replace(R.id.container, profileFragment);
                                transactionprofile.addToBackStack(null);
                                transactionprofile.commit() ;
                                headertext.setText("Profile");
                            }  catch (Exception e){}
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"Please login first",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);

                    }

                    return true;

                case R.id.navigation_comment:

                NotificationFragment notificationFragment = new NotificationFragment();
                FragmentTransaction transactionNotify = getSupportFragmentManager().beginTransaction();
                    transactionNotify.replace(R.id.container, notificationFragment);
                    transactionNotify.addToBackStack(null);
                    transactionNotify.commit();

                    return true;

            }
            return false;
        }

    };



    /*    protected void setUpViewPager(ViewPager viewpager){
            Pager viewpageradapter=new Pager(getSupportFragmentManager());
            viewpageradapter.addFragment(new HomeMain(),"Highlights");
            viewpageradapter.addFragment((new Fashion()),"Fashion");
            viewpageradapter.addFragment(new MobileItCamera(),"Mobile&It");
            viewpageradapter.addFragment(new Electronics(),"Electronics");
            viewpageradapter.addFragment(new Health(),"Health & Beauty");
            viewpageradapter.addFragment(new Sports(),"Sports");
            viewpageradapter.addFragment(new BabyAndKids(),"Baby Kids & Toys");
    viewpager.setAdapter(viewpageradapter);
        }*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(home.isVisible() && MainActivity.this!= null && !MainActivity.this.isFinishing()){
            showDilog();
        }
        else {
            super.onBackPressed();
        }
    }

// Permission giving at runtime

    private void checkRunTimePermission() {

        if(checkPermission()){

            Toast.makeText(MainActivity.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();

        }
        else {

            requestPermission();
        }
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {
                        CAMERA,
                        ACCESS_COARSE_LOCATION,
                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE

                },  REQUEST_PERMISSION_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case  REQUEST_PERMISSION_CODE:

                if (grantResults.length > 0) {

                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadContactsPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//                    boolean ReadPhoneStatePermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (CameraPermission && ReadContactsPermission ) {

                     //   Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    }
                    else {
                     //   Toast.makeText(MainActivity.this,"Permission Denied", Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }

    public boolean checkPermission() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int FourthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                FourthPermissionResult == PackageManager.PERMISSION_GRANTED ;
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        /*if(sessionManagement.isLoggedIn()) {
           item.setVisible(false);
        }*/


        if (id == R.id.home) {
            // Handle the camera action

            home = new HomeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, home);
            transaction.commit();



        } else if (id == R.id.categories) {

            CotegoriesFragment cotegoryFragment = new CotegoriesFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, cotegoryFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            headertext.setText("CATEGORY");

        }else if (id == R.id.hotdeal_nav){

            TodaysDeal todaysDeal = new TodaysDeal();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, todaysDeal);
            transaction.addToBackStack(null);
            transaction.commit() ;
            headertext.setText("DEALS");

        }
        else if(id==R.id.noti_draw){
            NotificationFragment notificationFragment = new NotificationFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, notificationFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            //           m.toast(this,"Notification comming soon .....");
//            headertext.setText("HOME");
        }
        else if(id==R.id.scanqr){
           startActivity(new Intent(MainActivity.this, ScanQrActivity.class));
        }

        else if(id==R.id.prepoint){

            PointsFrag notificationFragment = new PointsFrag();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, notificationFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            //           m.toast(this,"Notification comming soon .....");
//            headertext.setText("HOME");
        }

        else if(id==R.id.mycart){
          /*  m.toast(this,"Myccart commong soon .....");
            drawer.closeDrawers();*/
        }
        else if(id==R.id.mywishlist){
   /*         m.toast(this,"Wish list Comming soon...");
            drawer.closeDrawers();*/

        }
        else if(id==R.id.offer_draw){
       /*     m.toast(this,"Offer Zone Comming soon...");
            drawer.closeDrawers();*/
            OfferZone offerZone = new OfferZone();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, offerZone);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(id==R.id.intriduceqr){

            QrFragment todaysDeal = new QrFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, todaysDeal);
            transaction.addToBackStack(null);
            transaction.commit() ;
            headertext.setText("Qr Activity");

        }
        else if(id==R.id.introduce){
            IntroFragment todaysDeal = new IntroFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, todaysDeal);
            transaction.addToBackStack(null);
            transaction.commit() ;
            headertext.setText("Introducer");

        }
//
        else if (id == R.id.lang) {

          /*  Intent intent=new Intent(getApplicationContext(), Language.class);
            startActivity(intent);*/
            onchooselang();
        }
        else if(id == R.id.Contactus) {

          /*  Intent intent =new Intent(getApplicationContext(), ContactUs.class);
            startActivity(intent);*/
            ContactFragment contactFragment = new ContactFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, contactFragment);
            transaction.addToBackStack(null);
            transaction.commit() ;



//            Intent intent =new Intent(getApplicationContext(), ContactUs.class);
//            startActivity(intent);

            ContactFragment todaysDeal = new ContactFragment();
            FragmentTransaction transaction123 = getSupportFragmentManager().beginTransaction();
            transaction123.replace(R.id.container, todaysDeal);
            transaction123.addToBackStack(null);
            transaction123.commit() ;
            headertext.setText("ContactUs");




        }else if(id == R.id.myorders) {
//            sessionManagement = new SessionManagement(this);
//            if(sessionManagement.isLoggedIn()) {
//                HashMap<String, String> user1 = sessionManagement.getUserDetails();
//
//                // name
//
//                if (user1 != null) {
//                    try {
//                        YourOrder yourOrder = new YourOrder();
//                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.container, yourOrder);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
//                        headertext.setText("ORDERS");
//                    }catch (Exception e){}
//                }
//            }else {
//                Toast.makeText(getApplicationContext(),"Please login first",Toast.LENGTH_LONG).show();
//                Intent intent=new Intent(this, LoginActivity.class);
//                startActivity(intent);
//
//            }
        }else if(id == R.id.myaccount) {
            ProfileFragment profileFragment = new ProfileFragment();
            FragmentTransaction transactionprofile = getSupportFragmentManager().beginTransaction();
            transactionprofile.replace(R.id.container, profileFragment);
            transactionprofile.addToBackStack(null);
            transactionprofile.commit() ;
            headertext.setText("Profile");
        }


        else if(id ==R.id.Logout){

//            SharedPreferences.Editor editor = sharedPrefotpaccheckst.edit();
//            editor.remove("count");
//            editor.apply();

            LogOut logOut=new LogOut();
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container,logOut);
            transaction.addToBackStack(null);
            transaction.commit();

        }

        else if (id ==R.id.login){
            Intent intent=new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else if (id ==R.id.event){

            EventFragment todaysDeal = new EventFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, todaysDeal);
            transaction.addToBackStack(null);
            transaction.commit() ;
            headertext.setText("Event");
        }
        else if (id ==R.id.specialPromotion){
            PromotionFragment promotionFragment = new PromotionFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, promotionFragment);
            transaction.addToBackStack(null);
            transaction.commit() ;
            headertext.setText("Event");
        }

        else if (id ==R.id.mycart){

            Intent intent = new Intent(MainActivity.this,CartProducts.class);
            startActivity(intent);
            MainActivity.this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    void showDilog(){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to exit ?");
        alertDialogBuilder.setCancelable( false );
        alertDialogBuilder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                                           @Override
                                           public void onShow(DialogInterface arg0) {
                                               alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor( getResources().getColor( R.color.colorPrimary ));
                                               alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor( getResources().getColor( R.color.colorPrimary ));
                                           }
                                       }
        );
        alertDialog.show();
    }

    @Override
    protected void onResume() {


        super.onResume();
        headertext.setText("Bshop");
        username2=(TextView)hView.findViewById(R.id.username2);
        session = new SessionManagement(this);
        if(sessionManagement.isLoggedIn()) {
            HashMap<String, String> user1 = session.getUserDetails();


            // names
            if (user1 != null) {
                try {
                    name = user1.get(session.KEY_NAME);
                    m.log("HELLO<><><>" + name);
                }catch (Exception e){}
                try {
                    if (!name.equals("null")) {
                        username2.setText(name);
                        username2.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){}

            }
        }
    }

    private String getResString(int resId){
        return getResources().getString(resId);
    }

    public void onchooselang()
    {

        try {
            final String s[] = {getResString(R.string.en), getResString(R.string.zh)};
            AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
            adb.setTitle(R.string.txt_setting_changeyourlocale);
            final String lan = ReadLanguage.read(MainActivity.this);
            adb.setAdapter(new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, s), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    switch (which) {
                        case -1:

                            break;
                        case 0:
                            log = "EN";
                            editor.putString("log", log);
                            editor.commit();
                            break;
                        case 1:
//                           log = "VI";
                            log = "ZH";
                            editor.putString("log", log);
                            editor.commit();
                            break;

                        default:

                            break;
                    }

                    if (!log.equals(lan)) {
                        showRestartConfirmDlg(log);
                    }
                }

            });
            adb.show();
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Exception "+e, Toast.LENGTH_LONG).show();
        }
    }

    public void showRestartConfirmDlg(final String log)
    {
        AlertDialog.Builder adb=new AlertDialog.Builder(MainActivity.this);
        adb.setTitle(getResources().getString(R.string.msg_change_locale));
        adb.setMessage(getResources().getString(R.string.languageischanging));
        adb.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeLocale(log);
            }
        });
        adb.setNegativeButton("Cancel",null);
        adb.show();
    }

    private void changeLocale(String language_code){
        Resources res = getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(language_code.toLowerCase());
        res.updateConfiguration(conf, dm);

        LanguageDatabase languageDatabase=new LanguageDatabase(MainActivity.this);
        languageDatabase.open();
        languageDatabase.update(language_code);
        languageDatabase.close();
        restartApp();
    }

    private void restartApp(){
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void ImageSLideShow(){

        session = new SessionManagement(MainActivity.this);
        HashMap<String, String> user1 = session.getUserDetails();
        String userid = user1.get(session.KEY_ID);

        Map<String, String> postParam= new HashMap<String, String>();
        // postParam.put("session_id", sessionid);
        postParam.put("customer_id", userid);




        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                "http://bshop2u.com/apirest/get_cart_products_count_custom", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject objone) {
                        Log.d("tag", objone.toString());
                        try {
                            // JSONObject objone = new JSONObject(response);
                            boolean check  = objone.getBoolean("error");
                            String itemcount = objone.getString("message");

                            textView.setText(itemcount);

//                            SharedPreferences sharedPrefotpaccheckst = getSharedPreferences("MyPreffacultyaccheckstudent", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor123accheckst = sharedPrefotpaccheckst.edit();
//                            editor123accheckst.putString("count",itemcount);
//                            editor123accheckst.commit();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
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
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(jsonObjReq);



    }



}
