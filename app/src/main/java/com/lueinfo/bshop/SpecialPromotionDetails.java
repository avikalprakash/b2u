package com.lueinfo.bshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SpecialPromotionDetails extends AppCompatActivity {
    String title;
    String created_at;
    String description;
    String image;
//
    ImageView imageView;
    TextView nameText;
    TextView priceText;
    TextView descriptionText;
    TextView headertext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_promotion_details);
        imageView=(ImageView)findViewById(R.id.image);
        nameText=(TextView)findViewById(R.id.ItemName);
        priceText=(TextView)findViewById(R.id.regularprice);
        descriptionText=(TextView)findViewById(R.id.description);
        title = getIntent().getStringExtra("title");
        created_at = getIntent().getStringExtra("careted_at");
        description = getIntent().getStringExtra("desc");
        image = getIntent().getStringExtra("image");
        Picasso.with(getApplicationContext()).load(String.valueOf(image)).into(imageView);
        nameText.setText(title);
       // priceText.setText(price);
        descriptionText.setText(description);
    }
}
