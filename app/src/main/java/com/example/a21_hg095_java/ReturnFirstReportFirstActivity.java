package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

public class ReturnFirstReportFirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*타이틀(ActionBar 영역) 제거하기
         * setContentView(R.layout.activity_box_open_pop_up); 보다 먼저 선언되어야함
         * */
        requestWindowFeature( Window.FEATURE_NO_TITLE );

        setContentView(R.layout.activity_return_first_report_first);




        //신고 버튼 선언 후 클릭했을때 이벤트 발생(반납 확인 클릭 후 팝업창 뜸)
        Button returnSecondReportButton = (Button) findViewById(R.id.returnSecondReportButton);
        returnSecondReportButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ReturnReportCompleteActivity.class);


            //1. 만약 신고 내용안에 아무것도 안적혀있으면 클릭해도 액티비티 전환되지않도록 구현
            //2. 서버와 통신해서 신고 내용 로그 찍어둘수 있도록 구현하기


            startActivity(intent);
        });


        //취소 버튼 선언 후 클릭시 이벤트 발생하도록 코딩(이전 액티비티로 돌아감)
        Button returnSecondCancelButton = (Button) findViewById(R.id.returnSecondCancelButton);
        returnSecondCancelButton.setOnClickListener(v -> {
            finish();

        });



    }





}