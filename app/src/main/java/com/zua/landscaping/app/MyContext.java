package com.zua.landscaping.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by 123 on 2016/3/18.
 */
public class MyContext {

    private static MyContext myContext;

    public Context mContext;

    private SharedPreferences mPreferences;

    public static MyContext getInstance() {
        if (myContext == null) {
            myContext = new MyContext();
        }
        return myContext;
    }

    private MyContext() {
    }

    private MyContext(Context context){
        mContext = context;
        myContext = this;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void init(Context context){
        myContext = new MyContext(context);
    }

    public SharedPreferences getSharedPreferences(){
        return mPreferences;
    }
}
