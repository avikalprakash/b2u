package com.lueinfo.bshop.Fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lueinfo.bshop.Adapter.PromotionDetails;
import com.lueinfo.bshop.Adapter.PromotionListAdaptor;
import com.lueinfo.bshop.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyKartFragment extends Fragment {


    ArrayList<PromotionDetails> promotionDetailses=new ArrayList<PromotionDetails>();
    ListView listPromotion;
    View view;
    public static final String LANGUAGE = "log";
    public static final String MyPref = "MyPref";
    SharedPreferences sharedpreferences;
    String language;
    PromotionListAdaptor promotionListAdaptor;


    public MyKartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_kart, container, false);

        return view;
    }

}
