package com.example.a21_hg095_java.data;

import com.google.gson.annotations.SerializedName;

public class SignupData {
    @SerializedName("userName")
    private String userName;

    @SerializedName("userPwd")
    private String userPwd;

    public SignupData(String userName, String userPwd) {
        this.userName = userName;
        this.userPwd = userPwd;
    }
}
