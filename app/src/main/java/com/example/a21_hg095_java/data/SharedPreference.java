package com.example.a21_hg095_java.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public SharedPreference(Context context){
        this.context = context;
        pref = context.getSharedPreferences("CosMoSharedPref", Context.MODE_PRIVATE );
        editor = pref.edit();

    }

    public void createToken(String token){
        editor.putString("token", token);
        editor.commit();
    }

    public SharedPreferences getPref() {
        return pref;
    }
}
