package com.example.a21_hg095_java.data;

import com.google.gson.annotations.SerializedName;

public class LoginData {

    @SerializedName("nickname")
    String nickname;

    @SerializedName("password")
    String password;

    public LoginData(String userEmail, String userPwd) {
        this.nickname = userEmail;
        this.password = userPwd;
    }
}
