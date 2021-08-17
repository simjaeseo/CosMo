package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.a21_hg095_java.data.SignupData;
import com.example.a21_hg095_java.data.SignupResponse;
import com.example.a21_hg095_java.network.RetrofitClient;
import com.example.a21_hg095_java.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private ServiceApi service;
    private EditText signup_pw_EditText;
    private EditText signup_id_EditText;
    private EditText signup_pwcheck_EditText;
    private Button signup_done_button;
    private ProgressBar mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText signup_id_EditText = (EditText) findViewById(R.id.signup_id_EditText);
        EditText signup_pw_EditText = (EditText) findViewById(R.id.signup_pw_EditText);
        EditText signup_pwcheck_EditText = (EditText) findViewById(R.id.signup_pwcheck_EditText);


        service = RetrofitClient.getClient().create(ServiceApi.class);

        //회원가입 확인 클릭시
        Button signup_done_button = findViewById(R.id.signup_done_button);
        signup_done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptJoin();


            }
        });
    }
    private void attemptJoin() {
        signup_id_EditText.setError(null);
        signup_pw_EditText.setError(null);

        String name = signup_id_EditText.getText().toString();
        String password = signup_pw_EditText.getText().toString();


        startJoin(new SignupData(name, password));
    }

    private void startJoin(SignupData data) {
        service.userJoin(data).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                SignupResponse result = response.body();
                Toast.makeText(SignupActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if (result.getCode() == 200) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

