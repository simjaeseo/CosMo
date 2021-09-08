package com.example.a21_hg095_java;

import static com.example.a21_hg095_java.BTService.BT_MESSAGE_READ;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a21_hg095_java.data.SharedPreference;

import java.io.UnsupportedEncodingException;

public class RentCompleteActivity extends AppCompatActivity {
    //서비스 바운드 변수
    private BTService btService;
    private boolean mBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_complete);

          // 수요일 오프라인때 테스트해보기
//        // 리턴값이 500이 아니라면 다시 대여창으로 돌아가게 하기
//        //이게 될지 테스트해보기
//        if(!(btService.receive().equals("500"))){
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//        }

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActiveActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }, 2000); //2초 딜레이 후 자동꺼짐

    }





    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, BTService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBound){
            unbindService(mConnection);
            mBound= false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BTService.MyBinder binder = (BTService.MyBinder) service;
            btService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //예기치 않은 종료
        }
    };
}