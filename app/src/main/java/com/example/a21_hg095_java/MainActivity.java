package com.example.a21_hg095_java;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter mBluetoothAdapter;
    //블루투스 활성화 상태
    final static int BT_REQUEST_ENABLE = 1;


    //사이드바 변수 선언
    private ImageView btn_navi;
    private DrawerLayout layout_drawer;
    private NavigationView navi_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //사이드바 변수 할당
        btn_navi = findViewById(R.id.btn_navi);
        layout_drawer = findViewById(R.id.layout_drawer);
        navi_view = findViewById(R.id.navi_view);

        //사이드바 이미지 터치시 열리는 기능
        btn_navi.setOnClickListener(v -> {
            layout_drawer.openDrawer(GravityCompat.START); // START : left, END : right 랑 같은 말
        });
        // 사이드바 메뉴 아이템에 클릭시 속성 부여
        navi_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() { // 네비게이션 메뉴 아이템에 클릭 속성 부여
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.helmetBoxText:
                        Intent intent = new Intent(getApplicationContext(), InformationScrollViewActivity.class);
                        startActivity(intent);
                        break;// 여기에 이용안내들어가고
                    case R.id.backDetectionText:
                        Intent intent1 = new Intent(getApplicationContext(), InformationBackActivity.class);
                        startActivity(intent1); // 여기에 후방감지 들어가고
                        break;
                    case R.id.suggestion:
                        Intent intent2 = new Intent(getApplicationContext(), QuestionsReportActivity.class);
                        startActivity(intent2); // 일단 건의함을 정리한거 들어가고
                        break;
                    case R.id.login:
                        Toast.makeText(getApplicationContext(), "로그인", Toast.LENGTH_SHORT).show(); //로그아웃 페이지 들어감
                        break;
                }
                layout_drawer.closeDrawers(); // 클릭 후 사이드바 닫힘
                return false;
            }
        });



/*
        // 재서가 만든 QR 스캐너 코드
        //대여하기 버튼 선언 후 클릭했을때 이벤트 발생(QR코드 스캐너 화면 뜸)
        Button rentButton = (Button) findViewById(R.id.rentButton);
        rentButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QrActivity.class);
            startActivity(intent);
        });
*/
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //대여하기 버튼 클릭시 이동하는 임시 코드
        Button rentButton = (Button) findViewById(R.id.rentButton);
        rentButton.setOnClickListener(v -> {

            // 블루투스가 꺼져있으면 키도록 하는 부분
            if (!mBluetoothAdapter.isEnabled()) {
                BluetoothOn();

            }
            else {
                //최종적으로는 Qr액티비티로 가도록 수정해야함
                Intent intent = new Intent(MainActivity.this, MainActiveActivity.class);
                startActivity(intent);
            }

        });


    }
    // 사이드바 켜지고 뒤로가기 버튼 누르면 사이드바 닫힘 구현
    @Override
    public void onBackPressed() {
        if (layout_drawer.isDrawerOpen(GravityCompat.START)) {
            layout_drawer.closeDrawers();
        } else {
            super.onBackPressed(); //일반 백버튼 기능 수행
        }
    }

    /*  public void startBarcodeReader(View activity_main) {
     *//*        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(false);//바코드 인식시 소리
        intentIntegrator.initiateScan();*//*
        Intent intent = new Intent(getApplicationContext(), MainActiveActivity.class); // 다음 넘어갈 클래스 지정
        startActivity(intent);

    } //qr 인식화면*/

    // 일단 패스
    protected void BluetoothOn(){
        //비활성화 되어 있다면 Intent 를 이용하여 활성화 창을 띄워 onActivityResult 에서 결과를 처리하게끔 함
        Intent intentBluetoothEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intentBluetoothEnable, BT_REQUEST_ENABLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BT_REQUEST_ENABLE:
                if (resultCode == RESULT_OK) { // 블루투스 활성화를 확인을 클릭하였다면
                    Toast.makeText(getApplicationContext(), "블루투스 활성화", Toast.LENGTH_LONG).show();

                } else if (resultCode == RESULT_CANCELED) { // 블루투스 활성화를 취소를 클릭하였다면
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "블루투스 허용 취소 시 서비스 이용이 불가능합니다.", Toast.LENGTH_SHORT).show();
                        }
                    }, 3000);

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}





