package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.a21_hg095_java.data.QrData;
import com.example.a21_hg095_java.data.QrResponse;
import com.example.a21_hg095_java.data.SharedPreference;
import com.example.a21_hg095_java.network.RetrofitClient;
import com.example.a21_hg095_java.network.ServiceApi;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrActivity extends AppCompatActivity {

    private IntentIntegrator qrScan;
    private ServiceApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        service = RetrofitClient.getClient().create(ServiceApi.class);


        qrScan = new IntentIntegrator(this);
        qrScan.setCaptureActivity(QrThemeActivity.class);
        qrScan.setOrientationLocked(false); // default가 세로모드인데 휴대폰 방향에 따라 가로, 세로로 자동 변경됩니다.
        qrScan.setBeepEnabled(false);

        //qrScan.setPrompt("QR코드를 사각형 안에 인식해주세요.");
        qrScan.initiateScan();



    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {

                Toast.makeText(this, "QR코드 인증이 취소되었습니다.", Toast.LENGTH_LONG).show();
                finish();
                // todo
            } else {

                startRent(new QrData(result.getContents()));


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    // qr코드 서버와 통신
    private void startRent(QrData data) {
        //헬멧박스 MAC 주소 받아와서 그 맥주소 shared preference에 저장 후 대여팝업에서 호출해 사용하기
        service.userRent("Bearer "+SharedPreference.getInstance().getToken(), data).enqueue(new Callback<QrResponse>() {
            @Override
            public void onResponse(Call<QrResponse> call, Response<QrResponse> response) {
                if (response.body().getSuccess()) {

                    SharedPreference.getInstance().createMacAddress(response.body().getMacAddress());

        try {

            Intent intent2 = new Intent(getApplicationContext(),BTService.class);
            intent2.putExtra("macAddress",SharedPreference.getInstance().getMacAddress() );
            startService(intent2);


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "6757567블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();

        }


            //동기화 처리
            Intent intent = new Intent(getApplicationContext(), RentPopupActivity.class);
            startActivity(intent);







//                    // 라즈베리파이
//                    Toast.makeText(QrActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//
//

                    // 서버로 데이터보냄( 만약 db에 저장되어있는 qr코드가 아니면 응답메세지로 응답하기(이건 토스트메세지로 뿌려주기))
                    //db에 저장되어있는게 맞다면? -> 사용자의 대여상태와 헬멧박스의 대여상태를 바꿔줘야하는데... 이건 다음 팝업창에서 확인 누를때 해야겠지...?
                    // 그럼 여기서는 qr코드가 db에 있는지 없는지만 확인해야겠네?
                    // 그럼 qr코드를 의미있는 값으로 한다면(ex 블루투스 mac주소 ?? ) 이 값을 이용할 수 도 있겠다!

                }else{
                    Toast.makeText(QrActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<QrResponse> call, Throwable t) {
                Toast.makeText(QrActivity.this, "QR인식 에러 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();

        super.onBackPressed();
    }
}