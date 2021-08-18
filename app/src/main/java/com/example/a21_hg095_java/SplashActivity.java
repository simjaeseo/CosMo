package com.example.a21_hg095_java;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.setContentView(1300085);
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = (Runnable)(new Runnable() {
            public final void run() {
                Intent intent = new Intent((Context)SplashActivity.this, MainActivity.class);

                // 여기서 사용자가 로그인이 이미 완료된 상태라면 메인 액티비티로 자동 넘어가도록 구현해야 할듯!
                // 로그인 했는지 안했는지는 토큰을 이용하면 되지않을까 생각.

                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        });
        handler.postDelayed(runnable, 3000L);
    }

}