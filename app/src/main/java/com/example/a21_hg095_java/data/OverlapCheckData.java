package com.example.a21_hg095_java.data;

import com.google.gson.annotations.SerializedName;

public class OverlapCheckData {

    @SerializedName("nickname")
    String nickname;


    public OverlapCheckData(String userEmail) {
        this.nickname = userEmail;
    }
}
