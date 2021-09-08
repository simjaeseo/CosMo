package com.example.a21_hg095_java.data;

import com.google.gson.annotations.SerializedName;

public class SignupData {
    @SerializedName("nickname")
    private String nickname;

    @SerializedName("name")
    private String name;

    @SerializedName("password")
    private String password;


    public SignupData(String nickname, String name, String password) {
        this.nickname = nickname;
        this.name = name;
        this.password = password;
    }
}
