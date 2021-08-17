package com.example.a21_hg095_java.data;

import com.google.gson.annotations.SerializedName;

public class SignupData {
    @SerializedName("nickname")
    private String nickname;

    @SerializedName("password")
    private String password;

    public SignupData(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}
