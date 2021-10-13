package com.example.a21_hg095_java.data;

import com.google.gson.annotations.SerializedName;

public class ReturnData {
    @SerializedName("QRnumber")
    String QRnumber;


    public ReturnData(String QRnumber) {
        this.QRnumber = QRnumber;

    }
}
