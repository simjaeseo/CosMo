package com.example.a21_hg095_java;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActiveActivity extends AppCompatActivity {

    private Button detective_Button;
    private View.OnClickListener detective_Listener;
    int i = 1;

    //취소버튼 누를때 전전 액티비티로 넘어가기위해서 필요한 선언
    public static Class MainActiveActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_active);

        detective_Button = (Button) findViewById(R.id.backDetectionButton);
        detective_Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i % 2 == 1) {
                    detective_Button.setText("후방감지on");
                    detective_Button.setBackgroundColor(Color.parseColor("#37b328"));
                    i++;
                } else if (i % 2 == 0) {
                    detective_Button.setText("후방감지off");
                    detective_Button.setBackgroundColor(Color.parseColor("#BFDCFE"));
                    i++;
                } // 숫자를 0과 1로 반복되게 변화를 주어서 색변환 동작을 하게 구현
            }

        };

        detective_Button.setOnClickListener(detective_Listener);


        //취소버튼 누를때 전전 액티비티로 넘어가기위해서 필요한 선언
        MainActiveActivity = MainActiveActivity.this.getClass();

        //헬멧박스open 버튼 선언 후 클릭했을때 이벤트 발생(open할지말지 정하는 팝업창 뜸)
        Button boxOpenButton = (Button) findViewById(R.id.boxOpenButton);
        boxOpenButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BoxOpenPopUpActivity.class);
            startActivity(intent);
        });




        //헬멧박스open 버튼 선언 후 클릭했을때 이벤트 발생(반납할건지 말건지 정하는 팝업창 뜸)
        Button returnButton = (Button) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ReturnFirstActivity.class);
            startActivity(intent);
        });

    }
}