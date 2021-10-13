package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.a21_hg095_java.data.OverlapCheckData;
import com.example.a21_hg095_java.data.OverlapCheckResponse;
import com.example.a21_hg095_java.data.SignupData;
import com.example.a21_hg095_java.data.SignupResponse;
import com.example.a21_hg095_java.network.RetrofitClient;
import com.example.a21_hg095_java.network.ServiceApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private ServiceApi service;
    private EditText signup_pw_EditText;
    private EditText signup_id_EditText;
    private EditText signup_name_EditText;
    private EditText signup_pwcheck_EditText;
    private Button signup_done_button;
    private Button idOverlapCheckButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup_id_EditText = (EditText) findViewById(R.id.signup_id_EditText);
        signup_pw_EditText = (EditText) findViewById(R.id.signup_pw_EditText);
        signup_pwcheck_EditText = (EditText) findViewById(R.id.signup_pwcheck_EditText);
        signup_name_EditText = (EditText) findViewById(R.id.signup_name_EditText);
        service = RetrofitClient.getClient().create(ServiceApi.class);




        //중복확인 확인 클릭시
        idOverlapCheckButton = (Button) findViewById(R.id.idOverlapCheckButton);
        idOverlapCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptOverlapCheck();

            }
        });




        //회원가입 확인 클릭시
        signup_done_button = (Button) findViewById(R.id.signup_done_button);
        signup_done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((signup_id_EditText.length() == 0) ||(signup_pw_EditText.length() == 0) || (signup_pwcheck_EditText.length() == 0)){
                    Toast.makeText(getApplicationContext(), "회원가입 정보를 모두 입력해주세요.",Toast.LENGTH_SHORT).show();
                } else if(!signup_pw_EditText.getText().toString().equals(signup_pwcheck_EditText.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "비밀번호 확인이 일치하는지 확인해주세요.",Toast.LENGTH_SHORT).show();
                }else{
                    attemptJoin();
                }
            }
        });



        //회원가입 취소버튼 터치시
        Button signup_cancle_button = (Button) findViewById(R.id.signup_cancle_button);
        signup_cancle_button.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();

        });




    }


    private void attemptJoin() {

        String nickname = signup_id_EditText.getText().toString();
        String name = signup_name_EditText.getText().toString();
        String password = signup_pw_EditText.getText().toString();

        //아이디 비번 인자로 넣어서 서버에 요청하는 부분
        startJoin(new SignupData(nickname, name, password));
    }

    private void startJoin(SignupData data) {
        service.userJoin(data).enqueue(new Callback<SignupResponse>() {
            //요청해서 응답이 왔을 때
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if (response.body().getSuccess()) {
                    Toast.makeText(SignupActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(SignupActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            //요청해서 응답이 안왔을 때
            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void attemptOverlapCheck() {

        String nickname = signup_id_EditText.getText().toString();

        //아이디 비번 인자로 넣어서 서버에 요청하는 부분
        startOverlapCheck(new OverlapCheckData(nickname));
    }

    private void startOverlapCheck(OverlapCheckData data) {
        service.userOverlaptest(data).enqueue(new Callback<OverlapCheckResponse>() {
            //요청해서 응답이 왔을 때
            @Override
            public void onResponse(Call<OverlapCheckResponse> call, Response<OverlapCheckResponse> response) {


                if(response.body().getSuccess()) {
                    Toast.makeText(SignupActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SignupActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            //요청해서 응답이 안왔을 때
            @Override
            public void onFailure(Call<OverlapCheckResponse> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "중복확인 에러 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }



}

