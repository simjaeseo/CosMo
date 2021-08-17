package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a21_hg095_java.data.LoginData;
import com.example.a21_hg095_java.data.LoginResponse;
import com.example.a21_hg095_java.network.RetrofitClient;
import com.example.a21_hg095_java.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button login_login_button;
    private Button login_signup_button;
    private EditText login_id_edittext;
    private EditText login_password_editview;
    private ServiceApi service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText login_id_edittext = (EditText) findViewById(R.id.login_id_edittext);
        EditText login_password_editview = (EditText) findViewById(R.id.login_password_editview);
        Button login_login_button = (Button) findViewById(R.id.login_login_button);
        Button login_signup_button = findViewById(R.id.login_signup_button);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        // 확인 버튼 선언 후 클릭시 이벤트 발생하도록 코딩(로그인 완료 후 성공 팝업창 띄우기)
        login_login_button.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginCompleteActivity.class);
            attemptLogin();
        });

        //회원가입 페이지로 이동
        login_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    private void attemptLogin() {
        String email = login_id_edittext.getText().toString();
        String password = login_password_editview.getText().toString();

        startLogin(new LoginData(email, password));
    }

    private void startLogin(LoginData data) {
        service.userLogin(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse result = response.body();
                Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
