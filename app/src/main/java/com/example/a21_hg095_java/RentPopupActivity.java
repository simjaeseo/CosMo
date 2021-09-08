package com.example.a21_hg095_java;

import static com.example.a21_hg095_java.BTService.BT_MESSAGE_READ;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a21_hg095_java.data.SharedPreference;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

public class RentPopupActivity extends AppCompatActivity {
    //서비스 테스트

    //서비스 바운드 변수
    private BTService btService;
    private boolean mBound;

    private String readMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_popup);
//        Toast.makeText(RentPopupActivity.this, SharedPreference.getInstance().getMacAddress(), Toast.LENGTH_SHORT).show();

//        Intent intent2 = new Intent(this,BTService.class);
//        intent2.putExtra("macAddress",SharedPreference.getInstance().getMacAddress() );

        Button RentPopupYesButton = (Button) findViewById(R.id.RentPopupYesButton);
        //확인 버튼
        RentPopupYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String good = "500";
                int i=0;

//                startService(intent2);

                // 블루투스 통신을 통해 값을 넘겨주기
                btService.send("500");



//                while (true) {
//                    if (btService.receive() != null) {
//                        break;
//                    }
//                    SystemClock.sleep(500);
//                }
//                Toast.makeText(RentPopupActivity.this, readMessage, Toast.LENGTH_SHORT).show();



//                Intent intent = new Intent(RentPopupActivity.this, RentCompleteActivity.class);
//                startActivity(intent);
//                finish();

                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(good.equals(SharedPreference.getInstance().getBTState())){
                            Toast.makeText(RentPopupActivity.this, btService.receive()+"1", Toast.LENGTH_SHORT).show();

//                            // 대여완료되었습니다 환영합니다! 팝업 2초 뜨게하고
//                            Intent intent = new Intent(RentPopupActivity.this, RentCompleteActivity.class);
//                            startActivity(intent);
//                            finish();
                        }else{
                            Toast.makeText(RentPopupActivity.this, btService.receive()+"2", Toast.LENGTH_SHORT).show();
//                            Handler handler = new Handler();
//
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
//                                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    startActivity(intent2);
//                                }
//                            }, 1500); //2초 딜레이 후 자동꺼짐
                        }
//                            Toast.makeText(RentPopupActivity.this, btService.receive(), Toast.LENGTH_SHORT).show();
                    }
                }, 6000); //2초 딜레이 후 자동꺼짐

//
//                while(true){
//                    if(btService.receive().equals("500")){
//                         Toast.makeText(RentPopupActivity.this, btService.receive()+"1", Toast.LENGTH_SHORT).show();
//
////                         대여완료되었습니다 환영합니다! 팝업 2초 뜨게하고
////                        Intent intent = new Intent(RentPopupActivity.this, RentCompleteActivity.class);
////                        startActivity(intent);
////                        finish();
//                        break;
//                    }
////                    else{ //3초
////                         Toast.makeText(RentPopupActivity.this, btService.receive() + "2", Toast.LENGTH_SHORT).show();
////
//////                        Toast.makeText(RentPopupActivity.this, "블루투스 통신 에러로 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
//////                        Handler handler = new Handler();
//////
//////                        handler.postDelayed(new Runnable() {
//////                            @Override
//////                            public void run() {
//////                                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
//////                                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//////                                startActivity(intent2);
//////                            }
//////                        }, 2000); //2초 딜레이 후 자동꺼짐
////                        break;
////                    }
//
//                    try {
//                        TimeUnit.MILLISECONDS.sleep(2000);
//                        i++;
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }








//                // 대여완료되었습니다 환영합니다! 팝업 2초 뜨게하고
//                Intent intent = new Intent(RentPopupActivity.this, RentCompleteActivity.class);
//                startActivity(intent);
//                finish();
            }
        });

        Button RentPopupCancelButton = (Button) findViewById(R.id.RentPopupCancelButton);
        //확인 버튼
        RentPopupCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //shared preference mac주소 널값으로 만들까?
//                SharedPreference.getInstance().createMacAddress("");



                //전전 메인 액티비티로 이동
                Intent intent = new Intent(RentPopupActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
        });
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



//    private final Handler mHandler = new Handler(){
//        public void handleMessage(android.os.Message msg){
//            if(msg.what == BT_MESSAGE_READ){
//                String readMessage = null;
//                try {
//                    readMessage = new String((byte[]) msg.obj, "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                SharedPreference.getInstance().createNumber(readMessage);
//            }
//        }
//    };




//    private final Handler mHandler = new Handler(){
//        public void handleMessage(android.os.Message msg){
//            if(msg.what == BT_MESSAGE_READ){
//                readMessage = null;
//                try {
//                    readMessage = new String((byte[]) msg.obj, "UTF-8");
//
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    };


}