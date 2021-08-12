package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Button;

public class BoxOpenPopUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*타이틀(ActionBar 영역) 제거하기
        * setContentView(R.layout.activity_box_open_pop_up); 보다 먼저 선언되어야함
        * */
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView(R.layout.activity_box_open_pop_up);


        // 확인 버튼 선언 후 클릭시 이벤트 발생하도록 코딩(통신해서 헬멧박스 open 후 다음 팝업창 띄우기)
        Button yesButton = (Button) findViewById(R.id.yesButton);
        yesButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BoxOpenPopUpYesActivity.class);


            //헬멧박스 open 부분이니, 서버랑 라즈베리파이랑 통신하는 부분 추가해야함
            //실제 헬멧박스를 오픈해야하니.



            startActivity(intent);

        });



        //취소 버튼 선언 후 클릭시 이벤트 발생하도록 코딩(이전 액티비티로 돌아감)
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> {
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
