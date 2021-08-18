package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.TextView;

public class ReturnReportCompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_report_complete);
        Handler handler = new Handler();

        //팝업창 클릭시 전전 액티비티로 전환됨(확인 버튼이 따로 없어서 이런식으로 구현해봄)
        //배경부분 클릭했을때도 구현해야할지 고민해야됨.
        TextView returnReportCompleteMessageTextView = (TextView) findViewById(R.id.returnReportCompleteMessageTextView);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000); //2초 딜레이 후 자동꺼짐
    }

}