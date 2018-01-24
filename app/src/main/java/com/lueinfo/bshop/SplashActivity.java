package com.lueinfo.bshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private Timer timer;
    private ProgressBar progressBar;
    private int i=0;
    TextView textView;
    SharedPreferences sharedpreferences;
    String language;
    public static final String LANGUAGE = "log";
    public static final String MyPref = "MyPref";
    ImageView mc9img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mc9img = (ImageView) findViewById(R.id.img_second);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.myanimation);
        mc9img.startAnimation(hyperspaceJumpAnimation);




//        progressBar=(ProgressBar)findViewById(R.id.progressBar);
//        progressBar.setProgress(0);
//        progressBar.getProgressDrawable().setColorFilter(
//                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
//        textView=(TextView)findViewById(R.id.textView);
//        textView.setText("");
        sharedpreferences=  this.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        language = sharedpreferences.getString(LANGUAGE,"");
        Resources res = getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(language.toLowerCase());
        res.updateConfiguration(conf, dm);
        final long period = 40;

        Thread splashthread = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                    });
                    finish();
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        splashthread.start();




//        timer=new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                //this repeats every 100 ms
//                if (i<100){
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                            textView.setText(String.valueOf(i)+"%");
//                        }
//                    });
//                    progressBar.setProgress(i);
//                    i++;
//                }else{
//                    //closing the timer
//                    timer.cancel();
//                    Intent intent =new Intent(SplashActivity.this,MainActivity.class);
//                    startActivity(intent);
//                    // close this activity
//                    finish();
//                }
//            }
//        }, 0, period);
    }
}
