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

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.lueinfo.bshop.Adapter.Helper;
import com.lueinfo.bshop.Adapter.ItemEntity;
import com.lueinfo.bshop.Adapter.SQLiteHandler;
import com.lueinfo.bshop.Adapter.SessionManagement;
import com.lueinfo.bshop.Adapter.Utils;
import com.lueinfo.bshop.RitsActivity.ForgotPassword;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    TextView create_ac2,headertext;
    EditText editText1, editText2;
    Helper helper;
    private ProgressDialog pDialog;
    private SessionManagement session;
    private SQLiteHandler db;
    Dialog dialog;
    public static final String USER_NAME = "USER_NAME";
    public static final String PASSWORD = "PASSWORD";
    private static final String LOGIN_URL = "http://192.xxx.x.xx/VolleyUpload/login.php";
    ProgressBar pb;
    private static final int SHOW_PROCESS_DIALOG = 1;
    private static final int HIDE_PROCESS_DIALOG = 0;
    ArrayList<ItemEntity> Loginlist=new ArrayList<ItemEntity>();
    MyAsync myAsync;
    String email="";
    String password ="";
    CheckBox hidePass;
    private CallbackManager callbackManager;
    private TextView textView,fgpasstxt;

    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

//    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
//        @Override
//        public void onSuccess(LoginResult loginResult) {
//            AccessToken accessToken = loginResult.getAccessToken();
//            Profile profile = Profile.getCurrentProfile();
//            displayMessage(profile);
//        }
//
//        @Override
//        public void onCancel() {
//
//        }
//
//        @Override
//        public void onError(FacebookException e) {
//
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fill_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

       // pb = (ProgressBar) findViewById(R.id.pb_subcot);
        editText1 = (EditText) findViewById(R.id.email);
        editText2 = (EditText) findViewById(R.id.password);
        headertext=(TextView)findViewById(R.id.header3text);
        hidePass = (CheckBox)findViewById(R.id.hidePass);
        create_ac2 = (TextView) findViewById(R.id.create_ac2);
      //  LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
//        textView = (TextView) findViewById(R.id.textView);

        fgpasstxt = (TextView) findViewById(R.id.fgpasstxt);
        fgpasstxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotPassword.class));
            }
        });

        headertext=(TextView)findViewById(R.id.header3text);
        headertext.setText("LOGIN");
       // loginButton.setReadPermissions("user_friends");
       // loginButton.setFragment(this);
      //  loginButton.registerCallback(callbackManager, callback);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        //Facebook login

        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                displayMessage(newProfile);
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();
        hidePass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                {
                    editText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else
                {
                    editText2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });
        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManagement(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        create_ac2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        TextView textView = (TextView) findViewById(R.id.login);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 email = editText1.getText().toString().trim();
                 password = editText2.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    loadData();

                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    class MyAsync extends AsyncTask<String,Void,String> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            //pb.setIndeterminate(true);
        }

        @Override
        protected String doInBackground(String... Strings) {

           // handler.sendEmptyMessage(SHOW_PROCESS_DIALOG);
            Log.d("werftghy", "iiiiiiiiiiii");
            String s = "";


            try {
                HttpClient httpClient = new DefaultHttpClient();
//                HttpPost httpPost = new HttpPost("http://54.67.107.248/jeptags/apirest/login");
                HttpPost httpPost = new HttpPost("http://bshop2u.com/apirest/login");
                httpPost.setHeader("Content-type", "application/json");
                Log.d("jdnj", "  " + Strings[0]+"   "+Strings[1]);
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("email", Strings[0]);
                jsonObject.accumulate("password",Strings[1]);
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
          //  handler.sendEmptyMessage(HIDE_PROCESS_DIALOG);
           Loginlist.clear();
            try {
                JSONObject jsonObject=new JSONObject(s);
                String Response= jsonObject.getString("error");
                String Messgae=jsonObject.getString("message");
                if(Response.equals("true")){
                    Toast.makeText(LoginActivity.this,Messgae, Toast.LENGTH_LONG).show();
                }else {

                    String user = jsonObject.getString("message");
                    Log.d("hbh", "  " + user);
                    JSONObject userDetail = new JSONObject(user);
                    ItemEntity itemEntity = new ItemEntity();
                    itemEntity.setUserLoginId(userDetail.getString("email"));
                    itemEntity.setUserLoginPass(userDetail.getString("password_hash"));
                    itemEntity.setUserFirstName(userDetail.getString("firstname"));
                    itemEntity.setUserId(userDetail.getString("entity_id"));
                    String username = userDetail.getString("firstname");
                    String useremail = userDetail.getString("email");
                    String userid = userDetail.getString("entity_id");
                    session.createLoginSession(username, useremail, userid);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Log.d("hbh"," "+userDetail.getString("email")+ " "+userDetail.getString("password_hash"));

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        }

        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case SHOW_PROCESS_DIALOG:
                        dialog.show();
                     //   pb.setVisibility(View.VISIBLE);
                        break;
                    case HIDE_PROCESS_DIALOG:
                        dialog.hide();
                      //  pb.setVisibility(View.GONE);
                        break;
                }
                return false;
            }
        });

        public void loadData() {

            myAsync = new MyAsync();
            if (Utils.isNetworkAvailable(LoginActivity.this) && myAsync.getStatus() != AsyncTask.Status.RUNNING) {
                new MyAsync().execute(email,password);
            } else {
                Toast.makeText(LoginActivity.this, "NO net avalable", Toast.LENGTH_LONG).show();

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
            if (myAsync.getStatus() == AsyncTask.Status.RUNNING) {
                myAsync.cancel(true);
            }
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
       // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private void displayMessage(Profile profile){
        if(profile != null){
            textView.setText(profile.getName());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        displayMessage(profile);
    }
}
