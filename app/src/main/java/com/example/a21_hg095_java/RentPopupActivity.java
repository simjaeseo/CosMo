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

import com.example.a21_hg095_java.data.QrData;
import com.example.a21_hg095_java.data.QrResponse;
import com.example.a21_hg095_java.data.SharedPreference;
import com.example.a21_hg095_java.data.macData;
import com.example.a21_hg095_java.network.RetrofitClient;
import com.example.a21_hg095_java.network.ServiceApi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RentPopupActivity extends AppCompatActivity {
    //서비스 테스트

    //서비스 바운드 변수
    private BTService btService;
    private boolean mBound;


    private String readMessage;

    private ServiceApi service;

    Button RentPopupYesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        지우지 말기
//        Intent intent2 = new Intent(getApplicationContext(),BTService.class);
//        intent2.putExtra("macAddress",SharedPreference.getInstance().getMacAddress() );
//        startService(intent2);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_popup);
//        Toast.makeText(RentPopupActivity.this, SharedPreference.getInstance().getMacAddress(), Toast.LENGTH_SHORT).show();
        service = RetrofitClient.getClient().create(ServiceApi.class);

//        Intent intent2 = new Intent(this,BTService.class);
//        intent2.putExtra("macAddress",SharedPreference.getInstance().getMacAddress() );

        RentPopupYesButton = (Button) findViewById(R.id.RentPopupYesButton);
        //확인 버튼
        RentPopupYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RentPopupYesButton.setEnabled(false);

                String goodStateCode = "700";
                String goodStateCode2 = "500";

                Handler handler = new Handler();
                Handler handler2 = new Handler();

                try {
                    // 블루투스 통신을 통해 값을 넘겨주기
                    btService.send("700");

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if(goodStateCode.equals(btService.receive())){

                                btService.send("500");

                                handler2.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        if(goodStateCode2.equals(btService.receive())){
                                            startRent(new QrData(SharedPreference.getInstance().getQrNumber()));

//                                            // 대여완료되었습니다 환영합니다! 팝업 2초 뜨게하고
//                                            Intent intent = new Intent(RentPopupActivity.this, RentCompleteActivity.class);
//                                            RentPopupYesButton.setEnabled(true);
//                                            startActivity(intent);
//                                            finish();
                                        }else{
                                            Toast.makeText(RentPopupActivity.this, "1대여 과정 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                            RentPopupYesButton.setEnabled(true);

                                            // 뭐로해야하지
                                        }

                                    }
                                }, 3000); //1초 딜레이 후 자동꺼짐


                            }else{
                                Toast.makeText(RentPopupActivity.this, "대여 과정 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                RentPopupYesButton.setEnabled(true);

                            }
                        }
                    }, 3000); //2초 딜레이 후 자동꺼짐
                }
                catch (Exception e){
                    e.printStackTrace(); //오류 출력(방법은 여러가지)
                }




            }
        });

        Button RentPopupCancelButton = (Button) findViewById(R.id.RentPopupCancelButton);
        //확인 버튼
        RentPopupCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //shared preference mac주소 널값으로 만들까?
//                SharedPreference.getInstance().createMacAddress("");

                //여기서 블루투스 연결이 되어있다면, 통신 해제하는 부분 구현하는게 좋을거같음
//                btService.BTend();

                //전전 메인 액티비티로 이동
                Intent intent = new Intent(RentPopupActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


            }
        });
    }










//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
////        지우지 말기
////        Intent intent2 = new Intent(getApplicationContext(),BTService.class);
////        intent2.putExtra("macAddress",SharedPreference.getInstance().getMacAddress() );
////        startService(intent2);
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_rent_popup);
////        Toast.makeText(RentPopupActivity.this, SharedPreference.getInstance().getMacAddress(), Toast.LENGTH_SHORT).show();
//
////        Intent intent2 = new Intent(this,BTService.class);
////        intent2.putExtra("macAddress",SharedPreference.getInstance().getMacAddress() );
//
//        Button RentPopupYesButton = (Button) findViewById(R.id.RentPopupYesButton);
//        //확인 버튼
//        RentPopupYesButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RentPopupYesButton.setEnabled(false);
//
//                String goodStateCode = "700";
//                String goodStateCode2 = "500";
//
//                Handler handler = new Handler();
//                Handler handler2 = new Handler();
//
//
////                try {
////                    // 블루투스 통신을 통해 값을 넘겨주기
////                    btService.send("700");
////                    handler.postDelayed(new Runnable() {
////                        @Override
////                        public void run() {
////
////                            if(goodStateCode.equals(btService.receive())){
////                                btService.send("500");
////
////                                // 대여완료되었습니다 환영합니다! 팝업 2초 뜨게하고
////                                Intent intent = new Intent(RentPopupActivity.this, RentCompleteActivity.class);
////                                startActivity(intent);
////                                finish();
////                            }else{
////                                Toast.makeText(RentPopupActivity.this, "대여 과정 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
////
////                            }
////                        }
////                    }, 3000); //2초 딜레이 후 자동꺼짐
////                }
////                catch (Exception e){
////                    Toast.makeText(RentPopupActivity.this, "123대여 과정 중 에러가 발생했습니다. ", Toast.LENGTH_SHORT).show();
////                }
//
//                try {
//                    // 블루투스 통신을 통해 값을 넘겨주기
//                    btService.send("700");
//
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            if(goodStateCode.equals(btService.receive())){
//
//                                btService.send("500");
//
//                                handler2.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//                                        if(goodStateCode2.equals(btService.receive())){
//                                            // 대여완료되었습니다 환영합니다! 팝업 2초 뜨게하고
//                                            Intent intent = new Intent(RentPopupActivity.this, RentCompleteActivity.class);
//                                            RentPopupYesButton.setEnabled(true);
//                                            startActivity(intent);
//                                            finish();
//                                        }else{
//                                            Toast.makeText(RentPopupActivity.this, "1대여 과정 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
//                                            RentPopupYesButton.setEnabled(true);
//
//                                            // 뭐로해야하지
//                                        }
//
//                                    }
//                                }, 1500); //1초 딜레이 후 자동꺼짐
//
//
//                            }else{
//                                Toast.makeText(RentPopupActivity.this, "대여 과정 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
//                                RentPopupYesButton.setEnabled(true);
//
//                            }
//                        }
//                    }, 1500); //2초 딜레이 후 자동꺼짐
//                }
//                catch (Exception e){
//                    e.printStackTrace(); //오류 출력(방법은 여러가지)
//                }
//
//
//
//
//            }
//        });
//
//        Button RentPopupCancelButton = (Button) findViewById(R.id.RentPopupCancelButton);
//        //확인 버튼
//        RentPopupCancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //shared preference mac주소 널값으로 만들까?
////                SharedPreference.getInstance().createMacAddress("");
//
//                //여기서 블루투스 연결이 되어있다면, 통신 해제하는 부분 구현하는게 좋을거같음
////                btService.BTend();
//
//                //전전 메인 액티비티로 이동
//                Intent intent = new Intent(RentPopupActivity.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//
//
//            }
//        });
//    }





    // qr코드 서버와 통신
    private void startRent(QrData data) {
        //헬멧박스 MAC 주소 받아와서 그 맥주소 shared preference에 저장 후 대여팝업에서 호출해 사용하기
        service.userRent("Bearer "+SharedPreference.getInstance().getToken(), data).enqueue(new Callback<QrResponse>() {
            @Override

            public void onResponse(Call<QrResponse> call, Response<QrResponse> response) {
                if (response.body().getSuccess()) {

                    // 대여완료되었습니다 환영합니다! 팝업 2초 뜨게하고
                    Intent intent = new Intent(RentPopupActivity.this, RentCompleteActivity.class);
                    RentPopupYesButton.setEnabled(true);
                    startActivity(intent);
                    finish();


//            //동기화 처리
//            Intent intent = new Intent(getApplicationContext(), RentPopupActivity.class);
//            startActivity(intent);








                    // 서버로 데이터보냄( 만약 db에 저장되어있는 qr코드가 아니면 응답메세지로 응답하기(이건 토스트메세지로 뿌려주기))
                    //db에 저장되어있는게 맞다면? -> 사용자의 대여상태와 헬멧박스의 대여상태를 바꿔줘야하는데... 이건 다음 팝업창에서 확인 누를때 해야겠지...?
                    // 그럼 여기서는 qr코드가 db에 있는지 없는지만 확인해야겠네?
                    // 그럼 qr코드를 의미있는 값으로 한다면(ex 블루투스 mac주소 ?? ) 이 값을 이용할 수 도 있겠다!

                }else{
                    Toast.makeText(RentPopupActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<QrResponse> call, Throwable t) {
                Toast.makeText(RentPopupActivity.this, "대여 에러 발생", Toast.LENGTH_SHORT).show();
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