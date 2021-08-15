package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Questions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 문의사항 - 서비스 이용 관련 건의 클릭시 팝업창
        Button questions_service_button = (Button) findViewById(R.id.questions_service_button);
        questions_service_button.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), QuestionsServiceActivity.class);


            startActivity(intent);
        });

        // 문의사항 - 분실 고장 신고 클릭시 팝업창
        Button questions_report_button = (Button) findViewById(R.id.questions_report_button);
        questions_report_button.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), QuestionsReportActivity.class);

            setContentView(R.layout.activity_questions);
        });

    }

}