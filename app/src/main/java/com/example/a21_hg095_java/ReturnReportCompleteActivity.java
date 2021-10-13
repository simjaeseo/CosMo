package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a21_hg095_java.data.SharedPreference;

public class ReturnReportCompleteActivity extends AppCompatActivity {
    //서비스 바운드 변수
    private BTService btService;
    private boolean mBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_report_complete);
        Handler handler = new Handler();

        //팝업창 클릭시 전전 액티비티로 전환됨(확인 버튼이 따로 없어서 이런식으로 구현해봄)
        //배경부분 클릭했을때도 구현해야할지 고민해야됨.
        TextView returnReportCompleteMessageTextView = (TextView) findViewById(R.id.returnReportCompleteMessageTextView);




//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                finish();
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//            }
//        }, 2000); //2초 딜레이 후 자동꺼짐


//        String goodStateCode = "700";

        try {
            // 블루투스 통신을 통해 값을 넘겨주기
//            btService.send("700");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    SharedPreference.getInstance().createQrNumber("");
                    SharedPreference.getInstance().createMacAddress("");

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
//                    if(goodStateCode.equals(btService.receive())){
//                        // 대여완료되었습니다 환영합니다! 팝업 2초 뜨게하고
//                        SharedPreference.getInstance().createQrNumber("");
//                        SharedPreference.getInstance().createMacAddress("");
//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }else{
//                        Toast.makeText(ReturnReportCompleteActivity.this, "반납 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
//
//                    }
                }
            }, 2000); //2초 딜레이 후 자동꺼짐
        }
        catch (Exception e){
            Toast.makeText(ReturnReportCompleteActivity.this, "123반납 중 에러가 발생했습니다. ", Toast.LENGTH_SHORT).show();
        }




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