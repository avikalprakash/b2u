package com.lueinfo.bshop.Adapter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by lue on 23-11-2017.
 */

public class Message {

    private int STATUS=1;
    public void toast(Context context, String message){

        Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
    }
    public void log(String message){

        Log.d("Hello<><><><>"+ String.valueOf(STATUS),message);
        STATUS=STATUS+1;
    }
}
