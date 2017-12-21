package com.lueinfo.bshop.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lueinfo.bshop.Adapter.SessionManagement;
import com.lueinfo.bshop.R;


/**
 * Created by lue on 18-04-2017.
 */
public class LogOut extends Fragment {
SessionManagement sessionManagement;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement=new SessionManagement(getActivity());
       if(sessionManagement.isLoggedIn()) {
           sessionManagement.logoutUser();

       }else {
           Toast.makeText(getActivity(),"Please login first", Toast.LENGTH_LONG).show();
       }


    }
}
