package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;

public class InformationActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*타이틀(ActionBar 영역) 제거하기
         * setContentView(R.layout.activity_box_open_pop_up); 보다 먼저 선언되어야함
         * */
        requestWindowFeature( Window.FEATURE_NO_TITLE );

        setContentView(R.layout.activity_return_first_report_first);
        setContentView(R.layout.activity_information2);
    }
}