package com.lueinfo.bshop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.lueinfo.bshop.Adapter.LanguageDatabase;
import com.lueinfo.bshop.Adapter.ReadLanguage;

import java.util.Locale;

public class Language extends Activity {
Button button1,bt2,bt3,bt4;
    private Boolean exit = false;

    String log="";

SharedPreferences SM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
    }


    @Override
    protected void onResume() {

        super.onResume();
    }


    public void language(View view)
    {

        onchooselang();
    }
    private String getResString(int resId){
        return getResources().getString(resId);
    }

    public void onchooselang()
    {

        try {
            final String s[] = {getResString(R.string.en), getResString(R.string.zh)};
            AlertDialog.Builder adb = new AlertDialog.Builder(Language.this);
            adb.setTitle(R.string.txt_setting_changeyourlocale);
            final String lan = ReadLanguage.read(Language.this);
            adb.setAdapter(new ArrayAdapter(Language.this, android.R.layout.simple_list_item_1, s), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    switch (which) {
                        case -1:

                            break;
                        case 0:
                            log = "EN";
                            editor.putString("log", log);
                            editor.commit();
                            break;
                        case 1:
//                           log = "VI";
                            log = "ZH";
                            editor.putString("log", log);
                            editor.commit();
                            break;

                        default:

                            break;
                    }

                    if (!log.equals(lan)) {
                        showRestartConfirmDlg(log);
                    }
                }
            });
            adb.show();
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Exception "+e, Toast.LENGTH_LONG).show();
        }
    }

    public void showRestartConfirmDlg(final String log)
    {
        AlertDialog.Builder adb=new AlertDialog.Builder(Language.this);
        adb.setTitle(getResources().getString(R.string.msg_change_locale));
        adb.setMessage(getResources().getString(R.string.languageischanging));
        adb.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeLocale(log);
            }
        });
        adb.setNegativeButton("Cancel",null);
        adb.show();
    }

    private void changeLocale(String language_code){
        Resources res = getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(language_code.toLowerCase());
        res.updateConfiguration(conf, dm);

        LanguageDatabase languageDatabase=new LanguageDatabase(Language.this);
        languageDatabase.open();
        languageDatabase.update(language_code);
        languageDatabase.close();
        restartApp();
    }

    private void restartApp(){
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
       // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
