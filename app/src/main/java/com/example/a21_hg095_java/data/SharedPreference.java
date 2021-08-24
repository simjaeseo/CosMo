package com.example.a21_hg095_java.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    private static SharedPreference instance;

    public static SharedPreference getInstance() {
        if (instance == null) {
            instance = new SharedPreference();
        }
        return instance;

    }

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SharedPreference(){
        pref = MyApplication.getContext().getSharedPreferences("CosMoSharedPref", Context.MODE_PRIVATE );
        editor = pref.edit();

    }

    public void createToken(String token){
        editor.putString("token", token);
        editor.apply();
    }

    public String getToken() {
        return pref.getString("token","");
    }
}

