package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button login_signup_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 확인 버튼 선언 후 클릭시 이벤트 발생하도록 코딩(로그인 완료 후 성공 팝업창 띄우기)
        Button login_login_button = (Button) findViewById(R.id.login_login_button);
        login_login_button.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginCompleteActivity.class);

            //서버 통신 후 로그인 실패시 (아이디, 비밀번호 오류) 실패팝업 띄우는 부분 추가예정





            startActivity(intent);

        });


        //회원가입 페이지로 이동
        login_signup_button = findViewById(R.id.login_signup_button);
        login_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }
}