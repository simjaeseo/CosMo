package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.a21_hg095_java.data.SharedPreference;

public class ReturnFirstActivity extends AppCompatActivity {
    //서비스 바운드 변수
    private BTService btService;
    private boolean mBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView(R.layout.activity_return_first);


        //반납확인 버튼 선언 후 클릭했을때 이벤트 발생(반납 확인 클릭 후 팝업창 뜸)
        Button returnFirstYesButton = (Button) findViewById(R.id.returnFirstYesButton);
        returnFirstYesButton.setOnClickListener(v -> {
            returnFirstYesButton.setEnabled(false);

            /*            if 헬멧 잘 안 닫쳤다면
                    warning 액티비티 창 띄우고
                    else if 헬멧이 없다면
                    warning 액티비티 창 띄우고
                    else 둘다 괜찮다면
                    load check 액티비티 창 띄우고*/


            //1. 헬멧박스안에 헬멧이 들어있는지 확인할 수 있도록 센서값을 확인해야함.(들어있으면 짐 있는지 확인하는 액티비티 띄우고,
            //    안들어있으면 헬멧 넣었는지 확인해달라는 액티비티 띄우기)  -> 라즈베리파이와 통신 구현
            //2. 만약 1번 케이스에서 문제가 없으면 다음 액티비티로 전환되도록 구현
            // --> 추가해야할 액티비티 : ReturnHelmetBeingWarningActivity, ReturnLoadCheckActivity, ReturnCompleteActivity


            //블루투스 통신을 통해 라즈베리파이에게 값을 전달
            String goodStateCode = "600";
            String magnetStateCode = "1";
            String goodStateCode2 = "400";
            String goodStateCode3 = "500";

            Handler handler = new Handler();
            Handler handler2 = new Handler();
            Handler handler3 = new Handler();


            //촬영용
            handler3.postDelayed(new Runnable() {
                @Override
                public void run() {

                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

//                            테스트 용
//                            Intent intent = new Intent(getApplicationContext(), ReturnHelmetBeingWarningActivity.class);
                            Intent intent = new Intent(getApplicationContext(), ReturnLoadCheckActivity.class); //테스트 위해 load_check로 넘어가게 함
                            startActivity(intent);
                            finish();

                        }
                    }, 1500); //1초 딜레이 후 자동꺼짐


                }
            }, 1500); //1초 딜레이 후 자동꺼짐






//            try {
//                // 블루투스 통신을 통해 값을 넘겨주기
//                btService.send("600");
//
//
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if(goodStateCode.equals(btService.receive())){
//
////                            Intent intent = new Intent(getApplicationContext(), ReturnLoadCheckActivity.class);
////                            startActivity(intent);
////                            finish(); //자연스럽게 꺼지게 함
//
//                            // 자석 센서값 확인후 if else로 나누기
//
//                            handler2.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    // 자석 센서값이 계속 들어오면 안됨.
//                                    if(magnetStateCode.equals(btService.magnetReceive())){
//
//                                        btService.send("400");
//
//                                        handler3.postDelayed(new Runnable() {
//                                            @Override
//                                            public void run() {
//
//                                                if(goodStateCode2.equals(btService.receive())){
//                                                    Intent intent = new Intent(getApplicationContext(), ReturnLoadCheckActivity.class);
//                                                    returnFirstYesButton.setEnabled(true);
//                                                    startActivity(intent);
//                                                    finish(); //자연스럽게 꺼지게 함
//
//                                                }else{
//                                                    Toast.makeText(ReturnFirstActivity.this, "1반납 과정 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
//                                                    returnFirstYesButton.setEnabled(true);
//
//                                                }
//
//                                            }
//                                        }, 1500); //1초 딜레이 후 자동꺼짐
//
////                                        //헬멧이 헬멧박스 안에 있을때
////                                        Intent intent = new Intent(getApplicationContext(), ReturnLoadCheckActivity.class);
////                                        startActivity(intent);
////                                        finish(); //자연스럽게 꺼지게 함
//                                    }else{
//
//
//                                        //후방감지기능 on 인 상태였을떄
//                                        if(SharedPreference.getInstance().getBackDetectionStatus().equals("true")){
//
//                                            btService.send("500");
//
//                                            handler3.postDelayed(new Runnable() {
//                                                @Override
//                                                public void run() {
//
//                                                    if(goodStateCode3.equals(btService.receive())){
//                                                        //헬멧이 헬멧박스 안에 없을때
//                                                        Intent intent = new Intent(getApplicationContext(), ReturnHelmetBeingWarningActivity.class);
//                                                        returnFirstYesButton.setEnabled(true);
//                                                        startActivity(intent);
//                                                        finish(); //자연스럽게 꺼지게 함
//                                                    }else{
//                                                        Toast.makeText(ReturnFirstActivity.this, "1반납 과정 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
//                                                        returnFirstYesButton.setEnabled(true);
//
//                                                        // 뭐로해야하지
//                                                    }
//
//                                                }
//                                            }, 3000); //1초 딜레이 후 자동꺼짐
//
//
//                                        }else{
//                                            //후방감지 기능 off 였을때
//
//                                            btService.send("400");
//
//                                            handler3.postDelayed(new Runnable() {
//                                                @Override
//                                                public void run() {
//
//                                                    if(goodStateCode2.equals(btService.receive())){
//                                                        //헬멧이 헬멧박스 안에 없을때
//                                                        Intent intent = new Intent(getApplicationContext(), ReturnHelmetBeingWarningActivity.class);
//                                                        returnFirstYesButton.setEnabled(true);
//                                                        startActivity(intent);
//                                                        finish(); //자연스럽게 꺼지게 함
//                                                    }else{
//                                                        Toast.makeText(ReturnFirstActivity.this, "1반납 과정 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
//                                                        returnFirstYesButton.setEnabled(true);
//
//                                                        // 뭐로해야하지
//                                                    }
//
//                                                }
//                                            }, 1500); //1초 딜레이 후 자동꺼짐
//
//                                        }
//
//
//
////                                        //헬멧이 헬멧박스 안에 없을때
////                                        Intent intent = new Intent(getApplicationContext(), ReturnHelmetBeingWarningActivity.class);
////                                        startActivity(intent);
////                                        finish(); //자연스럽게 꺼지게 함
//
//                                    }
//                                }
//                            }, 1500); //1초 딜레이 후 자동꺼짐
//
//
//                        }else{
//                            Toast.makeText(ReturnFirstActivity.this, "반납 과정 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
//                            returnFirstYesButton.setEnabled(true);
//
//                        }
//                    }
//                }, 1500); //2초 딜레이 후 자동꺼짐
//            }
//            catch (Exception e){
//                e.printStackTrace(); //오류 출력(방법은 여러가지)
//            }



                // 테스트 용
//            Intent intent = new Intent(getApplicationContext(), ReturnHelmetBeingWarningActivity.class);
//            Intent intent = new Intent(getApplicationContext(), ReturnLoadCheckActivity.class); //테스트 위해 load_check로 넘어가게 함
//            startActivity(intent);
//            finish();
        });

        //신고 버튼 선언 후 클릭했을때 이벤트 발생(반납 확인 클릭 후 팝업창 뜸)
        Button returnFirstReportButton = (Button) findViewById(R.id.returnFirstReportButton);
        returnFirstReportButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ReturnFirstReportFirstActivity.class);
            startActivity(intent);
            finish(); //자연스럽게 꺼지게 함
        });


        //취소 버튼 선언 후 클릭시 이벤트 발생하도록 코딩(이전 액티비티로 돌아감)
        Button returnFirstCancelButton = (Button) findViewById(R.id.returnFirstCancelButton);
        returnFirstCancelButton.setOnClickListener(v -> {
            finish();

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
}