package com.lueinfo.bshop.RitsActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lueinfo.bshop.Adapter.SessionManagement;
import com.lueinfo.bshop.OfferZoneDetails;
import com.lueinfo.bshop.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {

    EditText medittext,motptxt,mpasstxt,mconfirmpasstxt;
    Button msbmtbtn,motpbtn,mpassbtn;
    private ProgressDialog pDialog;
    String otp;
    LinearLayout medittxtlaout,motplayout,mpasslayout;
    String email,pass,confirmpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        medittext = (EditText) findViewById(R.id.emailidtxt);
        motptxt = (EditText) findViewById(R.id.otptxt);
        mpasstxt = (EditText) findViewById(R.id.changeppaastxt);
        mconfirmpasstxt = (EditText) findViewById(R.id.againchangeppaastxt);

        msbmtbtn = (Button) findViewById(R.id.sbmtbtn);
        motpbtn = (Button) findViewById(R.id.sbmtbtn1);
        mpassbtn = (Button) findViewById(R.id.sbmtbtn2);

        medittxtlaout = (LinearLayout) findViewById(R.id.emaillayout);
        motplayout =  (LinearLayout) findViewById(R.id.otplayout);
        mpasslayout =  (LinearLayout) findViewById(R.id.changepass);


        motplayout.setVisibility(View.GONE);
        mpasslayout.setVisibility(View.GONE);

        msbmtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = medittext.getText().toString().trim();
                if(email.length() == 0)
                {
                    Toast.makeText(ForgotPassword.this,"Email Not be empty",Toast.LENGTH_LONG).show();
                }
                else
                    {
                        Email();
                    }

            }
        });

        motpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otptxt = motptxt.getText().toString().trim();
                if(otp.equals(otptxt))
                {
                    mpasslayout.setVisibility(View.VISIBLE);
                    motplayout.setVisibility(View.GONE);
                }else
                    {
                        Toast.makeText(ForgotPassword.this,"Wrong Otp",Toast.LENGTH_LONG).show();
                    }

            }
        });

        mpassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password();
            }
        });

    }

    public void Email(){

        pDialog = new ProgressDialog(ForgotPassword.this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

         email = medittext.getText().toString().trim();

        Map<String, String> postParam= new HashMap<String, String>();
        // postParam.put("session_id", sessionid);
        postParam.put("email", email);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                "http://bshop2u.com/apirest/forgot_password_customer", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject objone) {
                        pDialog.dismiss();
                        Log.d("tag", objone.toString());
                        try {
                            // JSONObject objone = new JSONObject(response);
                            boolean check  = objone.getBoolean("error");
                             otp = objone.getString("message");

                            if(check) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);
                                builder.setMessage("Retry")
                                        .setNegativeButton("ok",null)
                                        .create()
                                        .show();

                            }else{

                                AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);
                                builder.setMessage("Pls check your mail")
                                        .setNegativeButton("ok",null)
                                        .create()
                                        .show();

                                motplayout.setVisibility(View.VISIBLE);
                                medittxtlaout.setVisibility(View.GONE);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                VolleyLog.d("tag", "Error: " + error.getMessage());
                //  hideProgressDialog();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }



        };

        jsonObjReq.setTag("tag");
        // Adding request to request queue
        RequestQueue queue = Volley.newRequestQueue(ForgotPassword.this);
        queue.add(jsonObjReq);

    }


    public void password() {

        //   email = medittext.getText().toString().trim();

        pass = mpasstxt.getText().toString().trim();
        confirmpass = mconfirmpasstxt.getText().toString().trim();

        if (pass.equals(confirmpass)) {

            pDialog = new ProgressDialog(ForgotPassword.this);
            // Showing progress dialog before making http request
            pDialog.setMessage("Loading...");
            pDialog.show();

            Map<String, String> postParam = new HashMap<String, String>();
            // postParam.put("session_id", sessionid);
            postParam.put("email", email);
            postParam.put("password", confirmpass);


            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    "http://bshop2u.com/apirest/update_password_customer", new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject objone) {
                            pDialog.dismiss();
                            Log.d("tag", objone.toString());
                            try {
                                // JSONObject objone = new JSONObject(response);
                                boolean check = objone.getBoolean("error");
                                otp = objone.getString("message");

                                if (check) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);
                                    builder.setMessage("Retry")
                                            .setNegativeButton("ok", null)
                                            .create()
                                            .show();

                                } else {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);
                                    builder.setMessage("Your Password changed successfully")
                                            .setNegativeButton("ok", null)
                                            .create()
                                            .show();


                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                    VolleyLog.d("tag", "Error: " + error.getMessage());
                    //  hideProgressDialog();
                }
            }) {

                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }


            };

            jsonObjReq.setTag("tag");
            // Adding request to request queue
            RequestQueue queue = Volley.newRequestQueue(ForgotPassword.this);
            queue.add(jsonObjReq);

        }else
        {
            Toast.makeText(ForgotPassword.this,"Pls keep both fields same",Toast.LENGTH_LONG).show();
        }


    }


}
