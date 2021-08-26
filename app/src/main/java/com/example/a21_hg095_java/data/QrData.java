package com.example.a21_hg095_java.data;

import com.google.gson.annotations.SerializedName;

public class QrData {
    @SerializedName("qrcode")
    String qrcode;



    public QrData(String qrcode) {

        this.qrcode = qrcode;

    }
}
