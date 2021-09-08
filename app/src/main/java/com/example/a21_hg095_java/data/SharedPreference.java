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

    public void createMacAddress(String macAddress){
        editor.putString("macAddress", macAddress);
        editor.apply();
    }

    public String getMacAddress() {
        return pref.getString("macAddress","");
    }


    public void createNumber(String number){
        editor.putString("number", number);
        editor.apply();
    }

    public String getNumber() {
        return pref.getString("number","");
    }

    public void createBTState(String state){
        editor.putString("state", state);
        editor.apply();
    }

    public String getBTState() {
        return pref.getString("state","");
    }

}

