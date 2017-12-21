package com.lueinfo.bshop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IntroducerActivity extends AppCompatActivity implements View.OnClickListener {

    Button mconfirmbtn;
    EditText mintroducertxt,mintrophonetxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introducer);

        mconfirmbtn = (Button) findViewById(R.id.confirm_btn);
        mconfirmbtn.setOnClickListener(this);

        mintroducertxt = (EditText) findViewById(R.id.introducer_edtxt);
        mintrophonetxt = (EditText) findViewById(R.id.introducerphone_edtxt);

    }

    @Override
    public void onClick(View v) {

        new MakeIntro().execute();

    }

    class MakeIntro extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;

        String intro = mintroducertxt.getText().toString().trim();
        String intphone = mintrophonetxt.getText().toString().trim();
        String userid = "91";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(IntroducerActivity.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://cloud9.condoassist2u.com/api/confirmIntroducer");
                httpPost.setHeader("Content-type", "application/json");
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("introducer_name", intro);
                jsonObject.accumulate("introducer_phone", intphone);
                jsonObject.accumulate("user_id", userid);

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                s = readadsResponse(httpResponse);
                Log.d("tag1", " " + s);
            } catch (Exception exception) {
                exception.printStackTrace();

                Log.d("espone",exception.toString());

            }

            return s;

        }
        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            pDialog.dismiss();
            try {
                JSONObject objone = new JSONObject(json);
                boolean check  = objone.getBoolean("error");
                if(check) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(IntroducerActivity.this);
                    builder.setMessage("This Introducer is not registerd or Introducer detail is wrong")
                            .setNegativeButton("ok", null)
                            .create()
                            .show();
                }else{


                    AlertDialog.Builder builder = new AlertDialog.Builder(IntroducerActivity.this);
                    builder.setMessage("Show Qr code to entitle for discounts")
                            .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    IntroducerActivity.this.finish();
                                }
                            })
                            .create()
                            .show();

                    SharedPreferences preferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("id",objone.getString("introducer_id"));  //introducer_id
                    editor.commit();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String readadsResponse(HttpResponse httpResponse) {

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
            Log.d("return1230", "" + return_text);
        } catch (Exception e) {

        }
        return return_text;
    }


}
