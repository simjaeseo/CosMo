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
import android.widget.EditText;
import android.widget.Toast;

import com.example.a21_hg095_java.data.LoginData;
import com.example.a21_hg095_java.data.LoginResponse;
import com.example.a21_hg095_java.data.ReportData;
import com.example.a21_hg095_java.data.ReportResponse;
import com.example.a21_hg095_java.data.SharedPreference;
import com.example.a21_hg095_java.network.RetrofitClient;
import com.example.a21_hg095_java.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnFirstReportFirstActivity extends AppCompatActivity {
    //서비스 바운드 변수
    private BTService btService;
    private boolean mBound;

    String helmetStatus = null;

    EditText reportContentEditText;

    private ServiceApi service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /*타이틀(ActionBar 영역) 제거하기
         * setContentView(R.layout.activity_box_open_pop_up); 보다 먼저 선언되어야함
         * */
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_return_first_report_first);

        service = RetrofitClient.getClient().create(ServiceApi.class);


        //신고 버튼 선언 후 클릭했을때 이벤트 발생(반납 확인 클릭 후 팝업창 뜸)
        Button returnSecondReportButton = (Button) findViewById(R.id.returnSecondReportButton);
        returnSecondReportButton.setOnClickListener(v -> {


            reportContentEditText = (EditText) findViewById(R.id.reportContentEditText);
            if(reportContentEditText.getText().toString().equals("")){
                Toast.makeText(ReturnFirstReportFirstActivity.this, "신고 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }else{
                //2. 서버와 통신해서 신고 내용 로그 찍어둘수 있도록 구현하기


                String goodStateCode = "600";
                String magnetStateCode = "1";

                Handler handler = new Handler();
                Handler handler2 = new Handler();


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //헬멧이 헬멧박스 안에 없을때
                        helmetStatus = "false";

                        // 서버와 통신하는 부분

                        attemptReport();

                        // 헬멧이 없다는것을 DB에 저장시키기
                        // 신고내역들을 따로 처리할수 있는 테이블 만들어야겠음

                        // 통신 끝나고 액티비티 전환
                        Intent intent = new Intent(getApplicationContext(), ReturnReportCompleteActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }, 1500); //1초 딜레이 후 자동꺼짐



                try {
                    // 블루투스 통신을 통해 값을 넘겨주기
                    btService.send("600");

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if(goodStateCode.equals(btService.receive())){

                                // 자석 센서값 확인후 if else로 나누기
                                handler2.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        // 자석 센서값이 계속 들어오면 안됨.
                                        if(magnetStateCode.equals(btService.magnetReceive())){
                                            //헬멧이 헬멧박스 안에 있을때
                                            helmetStatus = "true";
                                        }else{
                                            //헬멧이 헬멧박스 안에 없을때
                                            helmetStatus = "false";
                                        }

                                        // 서버와 통신하는 부분

                                        attemptReport();

                                        // 헬멧이 없다는것을 DB에 저장시키기
                                        // 신고내역들을 따로 처리할수 있는 테이블 만들어야겠음

//                                        // 통신 끝나고 액티비티 전환
//                                        Intent intent = new Intent(getApplicationContext(), ReturnReportCompleteActivity.class);
//                                        startActivity(intent);
//                                        finish();

                                    }
                                }, 1500); //1초 딜레이 후 자동꺼짐



                            }else{
                                Toast.makeText(ReturnFirstReportFirstActivity.this, "반납 과정 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 1500); //2초 딜레이 후 자동꺼짐
                }
                catch (Exception e){
                    e.printStackTrace(); //오류 출력(방법은 여러가지)
                }




            }




            // 확인 누르면 센서값 보내서 반납 되도록 하기
            // 여기서는 자석감지센서 on 할 필요없이 그냥 다 종료되도록
            // --> 상태값 따로 만들어야할듯

        });

        //취소 버튼 선언 후 클릭시 이벤트 발생하도록 코딩(이전 반납팝업창으로 돌아가고 해당 팝업은 종료)
        Button returnSecondCancelButton = (Button) findViewById(R.id.returnSecondCancelButton);
        returnSecondCancelButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ReturnFirstActivity.class);
            startActivity(intent);
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




    private void attemptReport() {
        String content = reportContentEditText.getText().toString();
        String QRnumber = SharedPreference.getInstance().getQrNumber();

        startReport(new ReportData(QRnumber, content, helmetStatus));
    }


    private void startReport(ReportData data) {
        service.userReport("Bearer "+SharedPreference.getInstance().getToken(), data).enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
//              LoginResponse result = response.body();
                if (response.body().getSuccess()) {

                    // 팝업창 띄운 후 스택 삭제한 다음 메인 액티티비로?
                    Intent intent = new Intent(ReturnFirstReportFirstActivity.this, ReturnReportCompleteActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(ReturnFirstReportFirstActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                Toast.makeText(ReturnFirstReportFirstActivity.this, "신고 에러 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }


}