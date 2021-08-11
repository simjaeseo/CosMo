package com.example.a21_hg095_java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void startBarcodeReader(View activity_main) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(false);//바코드 인식시 소리
        intentIntegrator.initiateScan();
        Intent intent = new Intent(getApplicationContext(),MainActiveActivity.class); // 다음 넘어갈 클래스 지정
        startActivity(intent);

    } //qr 인식화면
}