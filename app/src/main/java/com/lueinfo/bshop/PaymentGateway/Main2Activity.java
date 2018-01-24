package com.lueinfo.bshop.PaymentGateway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ipay.Ipay;
import com.ipay.IpayPayment;
import com.ipay.IpayR;
import com.lueinfo.bshop.R;

public class Main2Activity extends AppCompatActivity {

    String mcode="M08793_S0002";
    String mkey="q5eYXjqSSL";
    String amount="1.00";
    String username="Roushan";
    String contact="60123456789";
    String email="johnwoo@yahoo.com";
    String country="MY";
    String language="";
    String currency="MYR";
    String paymentmethodid="";
    String refno="ORD1188";
    String signature="";
    String  procdesc="Ticket for Concert.";
    String URL="http://www.myshop.com/backend_page.php";
    String remarks="";
    Button pay,req;
    IpayPayment payment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        req=(Button)findViewById(R.id.button2);
        pay=(Button)findViewById(R.id.button);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myfun();
            }
        });
        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();
            }
        });
    }
    public void myfun(){
        IpayPayment payment = new IpayPayment();
        payment = new IpayPayment();
        payment.setMerchantKey (mkey);
        payment.setMerchantCode (mcode);
        payment.setPaymentId (paymentmethodid);
        payment.setCurrency (currency);
        payment.setRefNo (refno);
        payment.setAmount (amount);
        payment.setProdDesc (procdesc);
        payment.setUserName (username);
        payment.setUserEmail (email);
        payment.setUserContact (contact);
        payment.setRemark (remarks);
        payment.setLang (language);
        payment.setCountry (country);

        payment.setBackendPostURL (URL);
        Intent checkoutIntent = Ipay.getInstance().checkout(payment, this, new ResultDelegate());
        startActivityForResult(checkoutIntent, 1);
    }
    public void request(){

        String local_MerchantCode = mcode;
        String local_RefID = refno;
        String local_Amount = amount;
        IpayR r = new IpayR();
        r.setMerchantCode(local_MerchantCode);
        r.setRefNo(local_RefID);
        r.setAmount(local_Amount);
        Intent checkoutIntent = Ipay.getInstance().requery(r,this, new ResultDelegate());
        startActivityForResult(checkoutIntent, 1);
    }



}
