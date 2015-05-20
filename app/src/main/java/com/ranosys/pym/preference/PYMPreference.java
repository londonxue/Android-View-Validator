package com.ranosys.pym.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by ranosys-sid on 15/5/15.
 */
public class PYMPreference {
    private static String FILE_NAME = "pym_preference";
    private static int MODE_PRIVATE = 0;
    private SharedPreferences sharedPreferences;
    private static PYMPreference pymPreference;

    public static String AUTH_KEY = "auth_key";

    private String keySet[] = {AUTH_KEY};

    private PYMPreference() {}

    public static void init(Context context){
        if(pymPreference == null){
            pymPreference = new PYMPreference();
            pymPreference.sharedPreferences = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        }
    }

    public static PYMPreference getInstance(){
        if(pymPreference == null){
            Log.d(PYMPreference.class.getName(), "PYMPreference not initialize yet. Call PYMPreference.init() to initialize it." );
        }
        return pymPreference;
    }

    public void saveStringValue(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getStringValue(String key){
        return sharedPreferences.getString(key, null);
    }

    public void clearPreference(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
