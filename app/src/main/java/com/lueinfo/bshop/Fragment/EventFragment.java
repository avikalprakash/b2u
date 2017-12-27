package com.lueinfo.bshop.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lueinfo.bshop.Adapter.CalenderEvensEntity;
import com.lueinfo.bshop.EventListActivity;
import com.lueinfo.bshop.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {


    RecyclerView mrecylerview;
    ArrayList<CalenderEvensEntity> calenderEvensEntities=new ArrayList<CalenderEvensEntity>();
    private ProgressDialog pDialog;
    CaldroidFragment caldroidFragment = new CaldroidFragment();

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);


        mrecylerview = (RecyclerView) view.findViewById(R.id.recycler_view);


//        Calendar cal = Calendar.getInstance();
//        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
//        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
//         blue = new ColorDrawable(getResources().getColor(R.color.colorgold));
        //   caldroidFragment.setArguments(args);

//        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
//        t.replace(R.id.calendar1, caldroidFragment);
//        t.commit();
        populatedata();


        // Bundle argstheme = new Bundle();
//        argstheme.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);
        //  caldroidFragment.setArguments(argstheme);

        Calendar mycal = Calendar.getInstance();
        mycal.set(Calendar.MONTH , 2);
        mycal.set(Calendar.DATE, 24);
        mycal.set(Calendar.YEAR, 2017);
        Date greenDate = mycal.getTime();

        ColorDrawable blue = new ColorDrawable( ContextCompat.getColor(getContext(), R.color.colorPrimary));
        ColorDrawable green = new ColorDrawable(Color.GREEN);
        caldroidFragment.setBackgroundDrawableForDate(blue,greenDate );
        // caldroidFragment.setBackgroundDrawableForDate(green,blueDate );

        caldroidFragment.setBackgroundDrawableForDate(green,greenDate);
        caldroidFragment.refreshView();


        return  view;
    }

    String getMonthForInt(int num) {
        String month = "December";
        Log.d( "num",""+num );
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

    public void populatedata() {

        pDialog = new ProgressDialog(getContext());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        final String url = "http://bshop2u.com/apiAdmin/api/event/getAllEvents";

        StringRequest movieReq = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("rr000", response.toString());
                        hidePDialog();

                        try {
                            JSONObject objone = new JSONObject(response);
                            boolean check = objone.getBoolean("error");
                            JSONArray jsonArray = objone.getJSONArray("message");
                            if (check) {

                            } else {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Log.d("111sizeeeem", "kk1111 " + jsonArray.length());
                                    JSONObject jobject = jsonArray.getJSONObject(i);
                                    CalenderEvensEntity calenderEvensEntity = new CalenderEvensEntity();
                                    calenderEvensEntity.setId(jobject.getString("event_id"));
                                    calenderEvensEntity.setEvent_date(jobject.getString("event_date"));
                                    calenderEvensEntities.add(calenderEvensEntity);

                                }

                            }

                            CaldroidFragment caldroidFragment = new CaldroidFragment();
                            Bundle args = new Bundle();
                            Calendar cal = Calendar.getInstance();
                            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
                            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
                            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.ncolor));

                            if (calenderEvensEntities.size()>0) {

                                for (int j = 0; j < calenderEvensEntities.size(); j++) {
                                    CalenderEvensEntity calenderEvensEntity = calenderEvensEntities.get(j);
                                    String getDate = calenderEvensEntity.getEvent_date();
                                    String[] tokens = getDate.split("-");
                                    int myday = Integer.parseInt(tokens[2]);
                                    int mymonth = Integer.parseInt(tokens[1]);
                                    int myyear = Integer.parseInt(tokens[0]);
                                    Calendar myCalendar = new GregorianCalendar(myyear, (mymonth - 1), myday);
                                    Date myDate = myCalendar.getTime();
                                    caldroidFragment.setBackgroundDrawableForDate(blue, myDate);
                                    caldroidFragment.setTextColorForDate(R.color.colorPrimary, myDate);
                                }
                                caldroidFragment.setArguments(args);


                                final CaldroidListener listener = new CaldroidListener() {

                                    @Override
                                    public void onSelectDate(Date date, View view) {

                                        String nowdate="";
                                        int position=0;
                                        try {
                                            SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                                            Date parsedDate = sdf.parse( String.valueOf( date ) );
                                            SimpleDateFormat print = new SimpleDateFormat("yyyy-MM-dd");
                                            nowdate=print.format(parsedDate).toString();
                                            Log.d("nowdate",""+nowdate);
                                            for(int k=0;k<calenderEvensEntities.size();k++){
                                                if (nowdate.equals( calenderEvensEntities.get( k ).getEvent_date() )){
                                                    Log.d("loopdate1",""+calenderEvensEntities.get( k ).getEvent_date());
                                                    position=k;
                                                    break;
                                                }else {
                                                    position=500000;
                                                    Log.d("loopdate2",""+calenderEvensEntities.get( k ).getEvent_date());
                                                }
                                            }
                                            if (position!=500000){
                                                Intent intent=new Intent(getContext(), EventListActivity.class );
                                                intent.putExtra( "eventid",calenderEvensEntities.get( position ));
                                                startActivity( intent );
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onChangeMonth(int month, int year) {

                                        Calendar cal = Calendar.getInstance();
                                        cal.set(year, month, 15);
                                        Calendar myCalendar = new GregorianCalendar(year, month, 15);
                                        Date myDate = myCalendar.getTime();

                                        Log.d("month", "" + getMonthForInt(myDate.getMonth() - 1));
                                        Log.d("year", "" + cal.get(Calendar.YEAR));
                                    }

                                    @Override
                                    public void onLongClickDate(Date date, View view) {

                                    }

                                    @Override
                                    public void onCaldroidViewCreated() {

                                    }

                                };

                                caldroidFragment.setCaldroidListener(listener);

                            }else{

                                Toast.makeText( getContext(),"Low Internate Connection!!!",Toast.LENGTH_LONG ).show();
                            }

                            FragmentTransaction t = getFragmentManager().beginTransaction();
                            t.replace(R.id.calendar1, caldroidFragment);
                            t.commit();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }      // notifying list adapter about data changes
                    // so that it renders the list view with updated data

                },  new Response.ErrorListener() {
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

}
