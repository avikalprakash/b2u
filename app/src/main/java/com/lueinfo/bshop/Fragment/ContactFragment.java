package com.lueinfo.bshop.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.AbsSavedState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lueinfo.bshop.ContactUs;
import com.lueinfo.bshop.MainActivity;
import com.lueinfo.bshop.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    EditText edittextto,edittextsubject,edittextmessage;
    TextView headertext;
    Button contactsend,backbutton;
    ImageView headerrightimg,headerleftimg;
    String To,Subject,Message,lan;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        edittextto=(EditText)view.findViewById(R.id.to);
        edittextmessage=(EditText)view.findViewById(R.id.message);
        edittextsubject=(EditText)view.findViewById(R.id.subject);
        contactsend=(Button)view.findViewById(R.id.contactsend);
//        headerleftimg=(ImageView)view.findViewById(R.id.back_btn);
//        headerleftimg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(getContext(),MainActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//            }
//        });
//        headerrightimg=(ImageView)findViewById(R.id.rightarrow);
       // headertext=(TextView)view.findViewById(R.id.text_header2);
//        headertext.setVisibility(View.VISIBLE);
        lan=getResources().getString(R.string.Contact);
      //  headertext.setText(lan);
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

        return  view;
    }

    public void sendData(){

        To =  edittextto.getText().toString();
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
