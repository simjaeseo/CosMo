package com.example.a21_hg095_java.network;

import com.example.a21_hg095_java.data.LoginData;
import com.example.a21_hg095_java.data.LoginResponse;
import com.example.a21_hg095_java.data.SignupData;
import com.example.a21_hg095_java.data.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApi {
    @POST("/login")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/register")
    Call<SignupResponse> userJoin(@Body SignupData data);
}
