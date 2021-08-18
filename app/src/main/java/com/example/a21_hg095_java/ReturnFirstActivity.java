package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

public class ReturnFirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView(R.layout.activity_return_first);


        //반납확인 버튼 선언 후 클릭했을때 이벤트 발생(반납 확인 클릭 후 팝업창 뜸)
        Button returnFirstYesButton = (Button) findViewById(R.id.returnFirstYesButton);
        returnFirstYesButton.setOnClickListener(v -> {
            /*            if 헬멧 잘 안 닫쳤다면
                    warning 액티비티 창 띄우고
                    else if 헬멧이 없다면
                    warning 액티비티 창 띄우고
                    else 둘다 괜찮다면
                    load check 액티비티 창 띄우고*/


            //1. 헬멧박스안에 헬멧이 들어있는지 확인할 수 있도록 센서값을 확인해야함.(들어있으면 짐 있는지 확인하는 액티비티 띄우고,
            //    안들어있으면 헬멧 넣었는지 확인해달라는 액티비티 띄우기)  -> 라즈베리파이와 통신 구현
            //2. 만약 1번 케이스에서 문제가 없으면 다음 액티비티로 전환되도록 구현
            // --> 추가해야할 액티비티 : ReturnHelmetBeingWarningActivity, ReturnLoadCheckActivity, ReturnCompleteActivity



            Intent intent = new Intent(getApplicationContext(), ReturnLoadCheckActivity.class); //테스트 위해 load_check로 넘어가게 함
            startActivity(intent);
            finish();
        });

        //신고 버튼 선언 후 클릭했을때 이벤트 발생(반납 확인 클릭 후 팝업창 뜸)
        Button returnFirstReportButton = (Button) findViewById(R.id.returnFirstReportButton);
        returnFirstReportButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ReturnFirstReportFirstActivity.class);
            startActivity(intent);
            finish();
        });


        //취소 버튼 선언 후 클릭시 이벤트 발생하도록 코딩(이전 액티비티로 돌아감)
        Button returnFirstCancelButton = (Button) findViewById(R.id.returnFirstCancelButton);
        returnFirstCancelButton.setOnClickListener(v -> {
            finish();

        });


    }
}