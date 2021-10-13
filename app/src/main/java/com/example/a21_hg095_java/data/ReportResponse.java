package com.example.a21_hg095_java.data;

import com.google.gson.annotations.SerializedName;

public class ReportResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;


    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

}
