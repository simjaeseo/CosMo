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
import com.example.a21_hg095_java.data.SharedPreference;
import com.example.a21_hg095_java.network.RetrofitClient;
import com.example.a21_hg095_java.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private SharedPreference sharedPreference;


    private Button login_login_button;
    private Button login_signup_button;
    private EditText login_id_edittext;
    private EditText login_password_editview;
    private ServiceApi service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_id_edittext = (EditText) findViewById(R.id.loginIdEditText);
        login_password_editview = (EditText) findViewById(R.id.loginPasswordEditView);
        login_login_button = (Button) findViewById(R.id.loginLoginButton);
        login_signup_button = findViewById(R.id.loginSignupButton);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        //회원가입 페이지로 이동
        login_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼 클릭 시
        login_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((login_id_edittext.length() == 0) ||(login_password_editview.length() == 0)){
                    Toast.makeText(getApplicationContext(), "로그인 정보를 모두 입력해주세요.",Toast.LENGTH_SHORT).show();
                } else{
                    attemptLogin();
                }
            }
        });
    }

    private void attemptLogin() {
        String nickname = login_id_edittext.getText().toString();
        String password = login_password_editview.getText().toString();

        startLogin(new LoginData(nickname, password));
    }


    private void startLogin(LoginData data) {
        service.userLogin(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//              LoginResponse result = response.body();
                if (response.body().getSuccess()) {

                    SharedPreference.getInstance().createToken(response.body().getToken());
                    SharedPreference.getInstance().createUserName(response.body().getUserName());

                    /*Toast.makeText(LoginActivity.this, SharedPreference.getInstance().getToken(), Toast.LENGTH_SHORT).show();*/

                    // 팝업창 띄운 후 스택 삭제한 다음 메인 액티티비로?
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else{
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
