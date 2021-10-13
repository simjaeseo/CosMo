package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.a21_hg095_java.data.SharedPreference;

public class BoxOpenPopUpActivity extends AppCompatActivity {

    //서비스 바운드 변수
    private BTService btService;
    private boolean mBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_box_open_pop_up);

        // 블루투스 통신해서 헬멧박스 open 후 완료 팝업창 띄우기
        Button yesButton = (Button) findViewById(R.id.yesButton);
        yesButton.setOnClickListener(v -> {

            yesButton.setEnabled(false);

            //블루투스 통신을 통해 라즈베리파이에게 값을 전달
            String goodStateCode = "700";
            String goodStateCode2 = "400";
            String goodStateCode3 = "500";

            Handler handler = new Handler();
            Handler handler2 = new Handler();



//            try {
//                // 블루투스 통신을 통해 값을 넘겨주기
//                btService.send("700");
//            }
//            catch (Exception e){
//                Toast.makeText(BoxOpenPopUpActivity.this, "123잠금장치 해제 과정 중 에러가 발생했습니다. ", Toast.LENGTH_SHORT).show();
//            }
//
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    if(goodStateCode.equals(btService.receive())){
//                        Intent intent = new Intent(getApplicationContext(), BoxOpenPopUpYesActivity.class);
//                        startActivity(intent);
//                        finish(); //자연스럽게 꺼지게 함
//                    }else{
//                        Toast.makeText(BoxOpenPopUpActivity.this, "잠금장치 해제 과정 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//            }, 700); //2초 딜레이 후 자동꺼짐
            try {
                // 블루투스 통신을 통해 값을 넘겨주기
                btService.send("700");


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(goodStateCode.equals(btService.receive())){

                            //후방감지기능 on 인 상태였을떄
                            if(SharedPreference.getInstance().getBackDetectionStatus().equals("true")){

                                btService.send("500");

                                handler2.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        if(goodStateCode3.equals(btService.receive())){
                                            // 대여완료되었습니다 환영합니다! 팝업 2초 뜨게하고
                                            Intent intent = new Intent(getApplicationContext(), BoxOpenPopUpYesActivity.class);
                                            yesButton.setEnabled(true);
                                            startActivity(intent);
                                            finish(); //자연스럽게 꺼지게 함
                                        }else{
                                            yesButton.setEnabled(true);

                                            Toast.makeText(BoxOpenPopUpActivity.this, "1잠금장치 해제 과정 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();

                                            // 뭐로해야하지
                                        }

                                    }
                                }, 1500); //1초 딜레이 후 자동꺼짐


                            }else{
                                //후방감지 기능 off 였을때

                                btService.send("400");

                                handler2.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        if(goodStateCode2.equals(btService.receive())){
                                            // 대여완료되었습니다 환영합니다! 팝업 2초 뜨게하고
                                            Intent intent = new Intent(getApplicationContext(), BoxOpenPopUpYesActivity.class);
                                            yesButton.setEnabled(true);
                                            startActivity(intent);
                                            finish(); //자연스럽게 꺼지게 함
                                        }else{
                                            yesButton.setEnabled(true);

                                            Toast.makeText(BoxOpenPopUpActivity.this, "1잠금장치 해제 과정 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();

                                            // 뭐로해야하지
                                        }

                                    }
                                }, 1500); //1초 딜레이 후 자동꺼짐

                            }





                        }else{
                            yesButton.setEnabled(true);
                            Toast.makeText(BoxOpenPopUpActivity.this, "대여 과정 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 1500); //2초 딜레이 후 자동꺼짐
            }
            catch (Exception e){
                e.printStackTrace(); //오류 출력(방법은 여러가지)
            }

        });


        //취소 버튼 선언 후 클릭시 이벤트 발생하도록 코딩(이전 액티비티로 돌아감)
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> {
            finish();

        });


    }


    /*    *//*팝업 밖 선택시 닫힘 방지*//*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if( event.getAction() == MotionEvent.ACTION_OUTSIDE ) {
            return false;
        }
        return true;
    }*/





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