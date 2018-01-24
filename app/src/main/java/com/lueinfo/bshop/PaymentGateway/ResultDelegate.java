package com.lueinfo.bshop.PaymentGateway;

import android.util.Log;

import com.ipay.IpayResultDelegate;

import java.io.Serializable;

/**
 * Created by lue on 20-01-2018.
 */

public class ResultDelegate implements IpayResultDelegate,Serializable {
    @Override
    public void onPaymentSucceeded(String s, String s1, String s2, String s3, String s4) {
        Log.d("HELLO","Success"+" "+s+" "+" "+s1+" "+s2+" "+s3+" "+s4);
        Log.d("s<><>",s);
        Log.d("s1<><>",s1);
        Log.d("s2<><>",s2);
        Log.d("s3<><>",s3);
        Log.d("s4<><>",s4);
    }

    @Override
    public void onPaymentFailed(String s, String s1, String s2, String s3, String s4) {
        Log.d("HELLO","Failed"+" "+s+" "+" "+s1+" "+s2+" "+s3+" "+s4);
        Log.d("s<><>",s);
        Log.d("s1<><>",s1);
        Log.d("s2<><>",s2);
        Log.d("s3<><>",s3);
        Log.d("s4<><>",s4);
    }

    @Override
    public void onPaymentCanceled(String s, String s1, String s2, String s3, String s4) {
        Log.d("HELLO","cancel"+" "+s+" "+" "+s1+" "+s2+" "+s3+" "+s4);
        Log.d("s<><>",s);
        Log.d("s1<><>",s1);
        Log.d("s2<><>",s2);
        Log.d("s3<><>",s3);
        Log.d("s4<><>",s4);
    }

    @Override
    public void onRequeryResult(String s, String s1, String s2, String s3) {
        Log.d("HELLO","Requery"+" "+s+" "+" "+s1+" "+s2+" "+s3);
        Log.d("s<><>",s);
        Log.d("s1<><>",s1);
        Log.d("s2<><>",s2);
        Log.d("s3<><>",s3);

    }
}
