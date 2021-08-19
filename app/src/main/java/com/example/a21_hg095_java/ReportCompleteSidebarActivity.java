package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

public class ReportCompleteSidebarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView(R.layout.activity_report_complete_sidebar);
        Handler handler = new Handler();



        TextView openMessageTextView = (TextView) findViewById(R.id.openMessageTextView);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },2000); //2초 딜레이 후 자동꺼짐
    }

}