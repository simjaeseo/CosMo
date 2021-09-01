package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a21_hg095_java.data.SharedPreference;

public class RentPopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_popup);
        Toast.makeText(RentPopupActivity.this, SharedPreference.getInstance().getMacAddress(), Toast.LENGTH_SHORT).show();

        Button RentPopupYesButton = (Button) findViewById(R.id.RentPopupYesButton);
        //확인 버튼
        RentPopupYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 블루투스 통신을 통해 값을 넘겨주기



                // 대여완료되었습니다 환영합니다! 팝업 2초 뜨게하고
                Intent intent = new Intent(RentPopupActivity.this, RentCompleteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button RentPopupCancelButton = (Button) findViewById(R.id.RentPopupCancelButton);
        //확인 버튼
        RentPopupCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //shared preference mac주소 널값으로 만들까?
                SharedPreference.getInstance().createMacAddress("");

                //전전 메인 액티비티로 이동
                Intent intent = new Intent(RentPopupActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
        });
    }
}