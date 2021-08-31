package com.example.a21_hg095_java;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a21_hg095_java.data.SharedPreference;


public class SplashActivity extends AppCompatActivity {
    BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Handler handler = new Handler();

        //블루투스 미지원 기기 이용불가
        if (mBluetoothAdapter == null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SplashActivity.this, "블루투스를 지원하지 않는 기기는 서비스 이용이 불가능합니다.", Toast.LENGTH_SHORT).show();
                }
            }, 3000);
            finish();
            return;
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (SharedPreference.getInstance().getToken() == null) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // 최종적으로는 메인 액티비티로 이동하게 수정하기
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }, 3000);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}