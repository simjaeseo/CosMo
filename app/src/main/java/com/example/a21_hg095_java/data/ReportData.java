package com.example.a21_hg095_java.data;

import com.google.gson.annotations.SerializedName;

public class ReportData {

    @SerializedName("QRnumber")
    String QRnumber;

    @SerializedName("content")
    String content;

    @SerializedName("helmetStatus")
    String helmetStatus;

    public ReportData(String QRnumber, String content, String helmetStatus) {
        this.QRnumber = QRnumber;
        this.content = content;
        this.helmetStatus = helmetStatus;
    }

}
