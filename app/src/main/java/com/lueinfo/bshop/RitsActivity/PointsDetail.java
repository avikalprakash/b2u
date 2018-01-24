package com.lueinfo.bshop.RitsActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lueinfo.bshop.MainActivity;
import com.lueinfo.bshop.OfferZoneDetails;
import com.lueinfo.bshop.PaymentGateway.Main2Activity;
import com.lueinfo.bshop.R;

public class PointsDetail extends AppCompatActivity {

    String mony,caspoint;
    TextView mmonettxt,mcashpointtxt;

    Button mbynowbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_detail);

        mmonettxt = (TextView) findViewById(R.id.monettxt);
        mcashpointtxt = (TextView) findViewById(R.id.cashpoint);

        mony = getIntent().getStringExtra("mny");
        caspoint = getIntent().getStringExtra("csp");

        mmonettxt.setText("Cost MYR "+mony);
        mcashpointtxt.setText("You Get "+caspoint+" in Your wallet");

        mbynowbtn = (Button) findViewById(R.id.bynowbtn);
        mbynowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PointsDetail.this,Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
