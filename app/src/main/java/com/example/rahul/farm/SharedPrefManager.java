package com.example.rahul.farm;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Rahul on 09-04-2017.
 */

public class SharedPrefManager {

    private static final String FACEBOOK_PREFS = "FBPrefs" ;

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getmInstance(Context context) {
        if(mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean saveUserInfo(String islogin, String name, String id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(FACEBOOK_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("islogin", islogin);
        editor.putString("name", name);
        editor.putString("id", id);
        editor.apply();
        return true;
    }

    public boolean saveUserLanguage(String language){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(FACEBOOK_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language", language);
        editor.apply();
        return true;
    }

    public String isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(FACEBOOK_PREFS, Context.MODE_PRIVATE);

        return sharedPreferences.getString("islogin","0");

    }

    public String getLanguage() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(FACEBOOK_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString("language","0");

    }

    public  void logOut() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(FACEBOOK_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
    }

    public String getUserName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(FACEBOOK_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString("name","0");

    }

    public String getUserId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(FACEBOOK_PREFS, Context.MODE_PRIVATE);

        return sharedPreferences.getString("id","0");
    }

}
