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


    public void createQrNumber(String QRnumber){
        editor.putString("QRnumber", QRnumber);
        editor.apply();
    }

    public String getQrNumber() {
        return pref.getString("QRnumber","");
    }

    public void createBTflag(String BTflag){
        editor.putString("BTflag", BTflag);
        editor.apply();
    }

    public String getBTflag() {
        return pref.getString("BTflag","");
    }

    public void createUserName(String userName){
        editor.putString("userName", userName);
        editor.apply();
    }

    public String getUserName() {
        return pref.getString("userName","");
    }

    public void createLatitude(String latitude){
        editor.putString("latitude", latitude);
        editor.apply();
    }

    public String getLatitude() {
        return pref.getString("latitude","");
    }

    public void createLongitude(String longitude){
        editor.putString("longitude", longitude);
        editor.apply();
    }

    public String getLongitude() {
        return pref.getString("longitude","");
    }

    public void createBackDetectionStatus(String backdetectionStatus){
        editor.putString("backdetectionStatus", backdetectionStatus);
        editor.apply();
    }

    public String getBackDetectionStatus() {
        return pref.getString("backdetectionStatus","");
    }

    public void createasd(String asd){
        editor.putString("asd", asd);
        editor.apply();
    }

    public String getasd() {
        return pref.getString("asd","");
    }


//    public void createBTState(String state){
//        editor.putString("state", state);
//        editor.apply();
//    }
//
//    public String getBTState() {
//        return pref.getString("state","");
//    }

}

