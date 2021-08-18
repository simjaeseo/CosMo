package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Button;

public class ReturnLoadCheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*타이틀(ActionBar 영역) 제거하기
         * setContentView(R.layout.activity_box_open_pop_up); 보다 먼저 선언되어야함
         * */
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView(R.layout.activity_return_load_check);



        // 확인 버튼 선언 후 클릭시 이벤트 발생하도록 코딩(통신해서 헬멧박스 open 후 다음 팝업창 띄우기)
        Button loadCheckYesButton = (Button) findViewById(R.id.loadCheckYesButton);
        loadCheckYesButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ReturnCompleteActivity.class);


            //헬멧박스 반납되면서 종료 서버로 반납처리 헬멧박스 기능 잠금



            startActivity(intent);
            finish();

        });



        //취소 버튼 선언 후 클릭시 이전 팝업창으로 이동
        Button loadCheckCancelButton = (Button) findViewById(R.id.loadCheckCancelButton);
        loadCheckCancelButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ReturnFirstActivity.class);
            startActivity(intent);
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
