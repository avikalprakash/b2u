package com.lueinfo.bshop.Adapter;

import android.content.Context;
import android.database.Cursor;

public class ReadLanguage {
    public static String read(Context ctx)
    {
        String language="";
        LanguageDatabase languageDatabase=new LanguageDatabase(ctx);
        languageDatabase.open();
        Cursor cu=languageDatabase.returnall();
        if(cu.moveToFirst())
        {
            do {
                language=cu.getString(0);
            }
            while (cu.moveToNext());
        }
        languageDatabase.close();
        return language;
    }
}
