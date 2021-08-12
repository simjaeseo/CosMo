package com.example.a21_hg095_java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActiveActivity extends AppCompatActivity {

    private Button detective_Button;
    private ImageView imageView;
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
                    i++;
                } else if (i % 2 == 0) {
                    detective_Button.setText("후방감지off");
                    i++;
                }
            }

        };


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