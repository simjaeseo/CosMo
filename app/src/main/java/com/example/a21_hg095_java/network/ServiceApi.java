package com.example.a21_hg095_java.network;

import com.example.a21_hg095_java.data.LoginData;
import com.example.a21_hg095_java.data.LoginResponse;
import com.example.a21_hg095_java.data.SignupData;
import com.example.a21_hg095_java.data.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

//서버에 요청보내는 부분 정의
public interface ServiceApi {
    @POST("/auth/login")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/auth/register")
    Call<SignupResponse> userJoin(@Body SignupData data);

    /*@POST("/rent")
    Call<RentResponse> userRent(@Header("Authorization" String token))*/
}

