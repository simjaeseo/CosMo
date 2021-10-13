package com.example.a21_hg095_java.data;

import com.google.gson.annotations.SerializedName;

public class macData {

    @SerializedName("qrcode")
    String qrcode;



    public macData(String qrcode) {

        this.qrcode = qrcode;

    }

}
