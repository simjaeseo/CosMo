package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QrActivity extends AppCompatActivity {

    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);


        qrScan = new IntentIntegrator(this);
        qrScan.setCaptureActivity(QrThemeActivity.class);
        qrScan.setOrientationLocked(false); // default가 세로모드인데 휴대폰 방향에 따라 가로, 세로로 자동 변경됩니다.
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

                // 서버로 데이터보냄( 만약 db에 저장되어있는 qr코드가 아니면 응답메세지로 응답하기(이건 토스트메세지로 뿌려주기))
                //db에 저장되어있는게 맞다면? -> 사용자의 대여상태와 헬멧박스의 대여상태를 바꿔줘야하는데... 이건 다음 팝업창에서 확인 누를때 해야겠지...?
                // 그럼 여기서는 qr코드가 db에 있는지 없는지만 확인해야겠네?
                // 그럼 qr코드를 의미있는 값으로 한다면(ex 블루투스 mac주소 ?? ) 이 값을 이용할 수 도 있겠다!

                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        finish();

        super.onBackPressed();
    }



}