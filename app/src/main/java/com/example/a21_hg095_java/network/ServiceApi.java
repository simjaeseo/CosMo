package com.example.a21_hg095_java.network;

import com.example.a21_hg095_java.data.LoginData;
import com.example.a21_hg095_java.data.LoginResponse;
import com.example.a21_hg095_java.data.OverlapCheckData;
import com.example.a21_hg095_java.data.OverlapCheckResponse;
import com.example.a21_hg095_java.data.QrData;
import com.example.a21_hg095_java.data.QrResponse;
import com.example.a21_hg095_java.data.ReportData;
import com.example.a21_hg095_java.data.ReportResponse;
import com.example.a21_hg095_java.data.ReturnData;
import com.example.a21_hg095_java.data.ReturnResponse;
import com.example.a21_hg095_java.data.SignupData;
import com.example.a21_hg095_java.data.SignupResponse;
import com.example.a21_hg095_java.data.WeatherResponse;
import com.example.a21_hg095_java.data.macData;
import com.example.a21_hg095_java.data.macResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

//서버에 요청보내는 부분 정의
public interface ServiceApi {
    @POST("/auth/login")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/auth/register")
    Call<SignupResponse> userJoin(@Body SignupData data);

    @POST("/auth/overlapCheck")
    Call<OverlapCheckResponse> userOverlaptest(@Body OverlapCheckData data);

    @POST("/cosmo/macAddrPull")
    Call<macResponse> userMac(@Header("Authorization") String token, @Body macData data);

//    @POST("/cosmo/rent")
//    Call<QrResponse> userRent(@Header("Authorization") String token, @Body QrData data);

    @POST("/cosmo/rent")
    Call<QrResponse> userRent(@Header("Authorization") String token, @Body QrData data);

    @POST("/cosmo1/report")
    Call<ReportResponse> userReport(@Header("Authorization") String token, @Body ReportData data);

    @POST("/cosmo/return")
    Call<ReturnResponse> userReturn(@Body ReturnData data);

    @GET("/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=2xS00HA5tfQTM6V8lnj%2FrdMgpl0Et1ow0JN6g07aqOngLc8H24ioQ3fAO%2Flk8KBlIY%2BaHkg4z6BkLYhH537uAg%3D%3D")
    Call<JsonObject> userWeather(
                                @Query("pageNo") String pageNo,
                                 @Query("numOfRows") String numOfRows,
                                 @Query("dataType") String dataType,
                                 @Query("base_date") String base_date,
                                 @Query("base_time") String base_time,
                                 @Query("nx") String nx,
                                 @Query("ny") String ny);


    /*@POST("/rent")
    Call<RentResponse> userRent(@Header("Authorization") String token)*/
}

