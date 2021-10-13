package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a21_hg095_java.data.SharedPreference;

public class LogoutPopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_popup);


        Button logoutYesButton = (Button) findViewById(R.id.logoutYesButton);
        //확인 버튼
        logoutYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreference.getInstance().createToken("");
                SharedPreference.getInstance().createUserName("");

                finish();
            }
        });


        Button logoutCancelButton = (Button) findViewById(R.id.logoutCancelButton);
        //확인 버튼
        logoutCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}