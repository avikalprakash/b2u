package com.lueinfo.bshop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.lueinfo.bshop.Adapter.Details;
import com.lueinfo.bshop.Adapter.ItemEntity;
import com.lueinfo.bshop.Adapter.Message;
import com.lueinfo.bshop.Adapter.SessionManagement;
import com.lueinfo.bshop.Database.DatabaseHelper;
import com.lueinfo.bshop.Fragment.AllCarts;
import com.lueinfo.bshop.Fragment.CotegoriesFragment;
import com.lueinfo.bshop.Fragment.HomeMain;
import com.lueinfo.bshop.Fragment.LogOut;
import com.lueinfo.bshop.Fragment.TodaysDeal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.CAMERA;

//import com.jeptag.organization.lueinfo.Activity.NFCActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    AlertDialog alertDialog;
    HomeMain home;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbarheader);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(null);
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
             /*   Intent i=new Intent(MainActivity.this, MySearch.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);*/
            }
        });
        databaseHelper=new DatabaseHelper(MainActivity.this);
        headertext=(TextView)findViewById(R.id.headertext);
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
        /*TextView textView = (TextView)findViewById(R.id.fab2);
        ArrayList<Details> contact = databaseHelper.getContact();
        m=new Message();
        sessionManagement = new SessionManagement(getApplicationContext());
        int size = contact.size();
        Log.d("size", "" + size);
        textView.setText(String.valueOf(size));*/
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

        home = new HomeMain();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, home);
        transaction.commit();
        frame_head = (FrameLayout)findViewById(R.id.frame_head);


        frame_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllCarts allCarts = new AllCarts();
                if (!allCarts.isInLayout()) {
                    FragmentTransaction transaction = MainActivity.this.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, allCarts);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
        //hide header

        checkRunTimePermission();

    }

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
                        ACCESS_COARSE_LOCATION

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

                        Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Permission Denied", Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }

    public boolean checkPermission() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
//        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED ;
//                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED;
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

            home = new HomeMain();
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
        }
        else if(id==R.id.intriduceqr){
            Intent i2 =new Intent(MainActivity.this, QRActivity.class);
            startActivity(i2);

        }
        else if(id==R.id.introduce){
           Intent i=new Intent(MainActivity.this, IntroducerActivity.class);
            startActivity(i);
        }
//
        else if (id == R.id.lang) {

            Intent intent=new Intent(getApplicationContext(), Language.class);
            startActivity(intent);
        }
        else if(id == R.id.Contactus) {
            Intent intent =new Intent(getApplicationContext(), ContactUs.class);
            startActivity(intent);
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
//            UserProfile userProfile = new UserProfile();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.container, userProfile);
////            transaction.addToBackStack(null);
//            transaction.commit();


        }


        else if(id ==R.id.Logout){

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


}
