package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Button;

public class QuestionsReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView(R.layout.activity_questions_report);

        //신고 버튼 선언 후 클릭했을때 이벤트 발생(반납 확인 클릭 후 팝업창 뜸)
        Button returnSecondReportButton = (Button) findViewById(R.id.questions_returnreport_button);
        returnSecondReportButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ReportCompleteSidebarActivity.class);


            //1. 만약 신고 내용안에 아무것도 안적혀있으면 클릭해도 액티비티 전환되지않도록 구현
            //2. 서버와 통신해서 신고 내용 로그 찍어둘수 있도록 구현하기


            startActivity(intent);
            finish();
        });

        //취소 버튼 선언 후 클릭시 이벤트 발생하도록 코딩(이전 반납팝업창으로 돌아가고 해당 팝업은 종료)
        Button returnSecondCancelButton = (Button) findViewById(R.id.questions_cancle_button);
        returnSecondCancelButton.setOnClickListener(v -> {
            finish();

        });




    }
    /*팝업 밖 선택시 닫힘 방지*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if( event.getAction() == MotionEvent.ACTION_OUTSIDE ) {
            return false;
        }
        return true;
    }


}