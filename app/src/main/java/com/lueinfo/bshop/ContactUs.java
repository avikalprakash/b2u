package com.lueinfo.bshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

public class ContactUs extends AppCompatActivity {
    EditText edittextto,edittextsubject,edittextmessage;
    TextView headertext;
    Button contactsend,backbutton;
    ImageView headerrightimg,headerleftimg;
    String To,Subject,Message,lan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        edittextto=(EditText)findViewById(R.id.to);
        edittextmessage=(EditText)findViewById(R.id.message);
        edittextsubject=(EditText)findViewById(R.id.subject);
        contactsend=(Button)findViewById(R.id.contactsend);
        headerleftimg=(ImageView)findViewById(R.id.back_btn);
        headerleftimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ContactUs.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
//        headerrightimg=(ImageView)findViewById(R.id.rightarrow);
        headertext=(TextView)findViewById(R.id.text_header2);
//        headertext.setVisibility(View.VISIBLE);
        lan=getResources().getString(R.string.Contact);
        headertext.setText(lan);
//        headerrightimg.setVisibility(View.INVISIBLE);
//        headerleftimg.setVisibility(View.VISIBLE);
//        headerleftimg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(getApplicationContext(),MainActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//finish();
//            }
//        });


        contactsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
    }

    public void sendData(){

        To=  edittextto.getText().toString();
        Subject=edittextsubject.getText().toString();
        Message=edittextmessage.getText().toString();
        Intent mail =new Intent(Intent.ACTION_SEND);
        mail.putExtra(Intent.EXTRA_EMAIL,new String[]{To});
        mail.putExtra(Intent.EXTRA_SUBJECT,Subject);
        mail.putExtra(Intent.EXTRA_TEXT,Message);
        mail.setType("message/rfc822");
        startActivity(Intent.createChooser(mail,"Send Email Via :"));
    }
}

