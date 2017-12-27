package com.lueinfo.bshop;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.lueinfo.bshop.Adapter.SQLiteHandler;
import com.lueinfo.bshop.Adapter.SessionManagement;
import com.lueinfo.bshop.Adapter.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {
TextView already_user1,singup,headertext;
    EditText ed1,ed2,ed3,ed4,ed5,ed6;
     SessionManagement session;
    SQLiteHandler db;
   ProgressBar pb;
    String firstname="";
    String lastname="";
    String email="";
    String password="";
    Dialog dialog;
    private static final int SHOW_PROCESS_DIALOG = 1;
    private static final int HIDE_PROCESS_DIALOG = 0;
    MyAsync myAsync;
    CheckBox hidePass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fill_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

       // pb = (ProgressBar) findViewById(R.id.pb_subcot);
        ed1=(EditText)findViewById(R.id.firstname);
        ed2=(EditText)findViewById(R.id.lastName);
        ed3=(EditText)findViewById(R.id.email);
        ed4=(EditText)findViewById(R.id.password);
        hidePass = (CheckBox)findViewById(R.id.hidePass);
        headertext=(TextView)findViewById(R.id.header3text);
        headertext.setText("SIGNUP");
      //  ed5=(EditText)findViewById(R.id.confirmPassword);
        singup=(TextView)findViewById(R.id.signup);
      //  ed6=(EditText)findViewById(R.id.address);
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManagement(getApplicationContext());
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Toast.makeText(SignupActivity.this,"You are already Log in", Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        }
        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 firstname=ed1.getText().toString();
                 lastname=ed2.getText().toString();
                 email=ed3.getText().toString();
                 password=ed4.getText().toString();

                if (!firstname.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                  //  SignupActivity(name, email, password);
                    loadData();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        hidePass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                {
                    ed4.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else
                {
                    ed4.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });
        already_user1=(TextView)findViewById(R.id.already_user1);
        already_user1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    public class MyAsync extends AsyncTask<String,Void,String> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignupActivity.this);
            pDialog.setMessage("loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
          //  pb.setIndeterminate(true);
        }

        @Override
        protected String doInBackground(String... Strings) {
          //  handler.sendEmptyMessage(SHOW_PROCESS_DIALOG);

            String s = "";
            ArrayList<NameValuePair> postParameters;
            String Consumer_Key="d545e3629c42e6781f083695010ff736";
            String Consumer_Secret="1f4adedc685fe6260f48c5d77e6d7786";
            String Token="eb0f6b5066d2e4ad5dc9105536190e1a";
            String Token_Secret="5b582cb51d36cc53e228e9ac89a7905d";

            try {

                HttpClient httpClient = new DefaultHttpClient();

//                String url="http://54.67.107.248/jeptags/apirest/create_customer";
                String url="http://bshop2u.com/apirest/create_customer";
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("content-type", "application/xml");
                httpPost.addHeader("Accept","application/xml");
                Log.d("jdnj", "  " + Strings[0] + "   " + Strings[1]);
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("firstname", Strings[0]);
                jsonObject.accumulate("lastname", Strings[1]);
                jsonObject.accumulate("email",Strings[2]);
                jsonObject.accumulate("password",Strings[3]);
                Log.d("jdnj", "  " + Strings[0]);

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                s = readResponse(httpResponse);
                Log.d("tag11", " " + s);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            return s;
        }
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
             //   handler.sendEmptyMessage(HIDE_PROCESS_DIALOG);
                pDialog.dismiss();
                Log.d("sssss",""+s);
                try {
                    JSONObject jsonObject=new JSONObject(s);
                  String Response= jsonObject.getString("error");
                    String Messgae=jsonObject.getString("message");
                    Log.d("Response"," "+Response);
                    if(Response.equals("false")){
                        Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(SignupActivity.this,Messgae, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
    }
   /* public OAuthConsumer getOauthConsumer() {
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(COUNSUMER_KEY, COUNSUMER_SECRET);
        consumer.setTokenWithSecret(OAUTH_TOKEN, OAUTH_SECRET);
        return consumer;
    }*/
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case SHOW_PROCESS_DIALOG:
                    dialog.show();
                  //  pb.setVisibility(View.VISIBLE);
                    break;
                case HIDE_PROCESS_DIALOG:
                    dialog.hide();
                   // pb.setVisibility(View.GONE);
                    break;
            }
            return false;
        }
    });

    public void loadData() {

        myAsync = new MyAsync();
        if (Utils.isNetworkAvailable(SignupActivity.this) && myAsync.getStatus() != AsyncTask.Status.RUNNING) {
            new MyAsync().execute(firstname,lastname,email,password);
        } else {
            Toast.makeText(SignupActivity.this, "NO net avalable", Toast.LENGTH_LONG).show();

        }
    }


    private String readResponse(HttpResponse httpResponse) {
        InputStream is = null;
        String return_text = "";
        try {
            is = httpResponse.getEntity().getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return_text = sb.toString();
            Log.d("return_text", "" + return_text);
        } catch (Exception e) {

        }
        return return_text;


    }

    @Override
    public void onPause() {
        super.onPause();
        dialog.dismiss();
        myAsync=new MyAsync();
        if (myAsync.getStatus() == AsyncTask.Status.RUNNING) {
            myAsync.cancel(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
        myAsync=new MyAsync();
        if (myAsync.getStatus() == AsyncTask.Status.RUNNING) {
            myAsync.cancel(true);
        }
    }

}
