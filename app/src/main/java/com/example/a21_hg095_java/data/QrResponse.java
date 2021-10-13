package com.example.a21_hg095_java.data;

import com.google.gson.annotations.SerializedName;

public class QrResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("macAddress")
    private String macAddress;




    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getMacAddress() {
        return macAddress;
    }

}
