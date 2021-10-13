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

import com.example.a21_hg095_java.data.ReportData;
import com.example.a21_hg095_java.data.ReportResponse;
import com.example.a21_hg095_java.data.ReturnData;
import com.example.a21_hg095_java.data.ReturnResponse;
import com.example.a21_hg095_java.data.SharedPreference;
import com.example.a21_hg095_java.network.RetrofitClient;
import com.example.a21_hg095_java.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnLoadCheckActivity extends AppCompatActivity {
    //서비스 바운드 변수
    private BTService btService;
    private boolean mBound;

    private ServiceApi service;
    Button loadCheckYesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*타이틀(ActionBar 영역) 제거하기
         * setContentView(R.layout.activity_box_open_pop_up); 보다 먼저 선언되어야함
         * */
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView(R.layout.activity_return_load_check);

        service = RetrofitClient.getClient().create(ServiceApi.class);


        // 확인 버튼 선언 후 클릭시 이벤트 발생하도록 코딩(통신해서 헬멧박스 open 후 다음 팝업창 띄우기)
        loadCheckYesButton = (Button) findViewById(R.id.loadCheckYesButton);
        loadCheckYesButton.setOnClickListener(v -> {


            Handler handler = new Handler();

            //헬멧박스 반납되면서 종료 서버로 반납처리 헬멧박스 기능 잠금
            String goodStateCode = "800";


            //촬영용
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                            attemptReturn();

                    Intent intent = new Intent(getApplicationContext(), ReturnCompleteActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 1500); //1초 딜레이 후 자동꺼짐




//            try {
//                // 블루투스 통신을 통해 값을 넘겨주기
//                btService.send("800");
//                loadCheckYesButton.setEnabled(false);
//
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if(goodStateCode.equals(btService.receive())){
//
//                            attemptReturn();
//
//                        }else{
//                            loadCheckYesButton.setEnabled(true);
//                            Toast.makeText(ReturnLoadCheckActivity.this, "반납 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                }, 2000); //2초 딜레이 후 자동꺼짐
//            }
//            catch (Exception e){
//                loadCheckYesButton.setEnabled(true);
//
//                Toast.makeText(ReturnLoadCheckActivity.this, "123반납 중 에러가 발생했습니다. ", Toast.LENGTH_SHORT).show();
//            }


        });



        //취소 버튼 선언 후 클릭시 이전 팝업창으로 이동
        Button loadCheckCancelButton = (Button) findViewById(R.id.loadCheckCancelButton);
        loadCheckCancelButton.setOnClickListener(v -> {
//            Intent intent = new Intent(getApplicationContext(), ReturnFirstActivity.class);
//            startActivity(intent);
            finish();
        });

    }


    /*팝업 밖 선택시 닫힘 방지*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if( event.getAction() == MotionEvent.ACTION_OUTSIDE ) {
            return false;
        }
        return true;
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








    private void attemptReturn() {
        String QRnumber = SharedPreference.getInstance().getQrNumber();

        startReturn(new ReturnData(QRnumber));
    }


    private void startReturn(ReturnData data) {
        service.userReturn(data).enqueue(new Callback<ReturnResponse>() {
            @Override
            public void onResponse(Call<ReturnResponse> call, Response<ReturnResponse> response) {
//              LoginResponse result = response.body();
                if (response.body().getSuccess()) {

                    Intent intent = new Intent(getApplicationContext(), ReturnCompleteActivity.class);
                    loadCheckYesButton.setEnabled(true);
                    startActivity(intent);
                    finish();

                }else{
                    loadCheckYesButton.setEnabled(true);
                    Toast.makeText(ReturnLoadCheckActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ReturnResponse> call, Throwable t) {
                loadCheckYesButton.setEnabled(true);
                Toast.makeText(ReturnLoadCheckActivity.this, "반납 에러 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
