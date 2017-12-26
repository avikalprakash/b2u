package com.lueinfo.bshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class OfferZoneDetails extends AppCompatActivity {
    String name;
    String price;
    String description;
    String image;

    ImageView imageView;
    TextView nameText;
    TextView priceText;
    TextView descriptionText;
    TextView headertext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_zone_details);
        headertext=(TextView)findViewById(R.id.text_header2);
//        headertext.setVisibility(View.VISIBLE);

 //       headertext.setText("Offer Zone");
        imageView=(ImageView)findViewById(R.id.image);
        nameText=(TextView)findViewById(R.id.ItemName);
        priceText=(TextView)findViewById(R.id.regularprice);
        descriptionText=(TextView)findViewById(R.id.description);
        name=getIntent().getStringExtra("name");
        price=getIntent().getStringExtra("price");
        description=getIntent().getStringExtra("description");
        image=getIntent().getStringExtra("image");
        Picasso.with(getApplicationContext()).load(String.valueOf(image)).into(imageView);
        nameText.setText(name);
        priceText.setText(price);
        descriptionText.setText(description);
    }
}
