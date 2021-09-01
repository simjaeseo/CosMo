package com.example.a21_hg095_java;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActiveActivity extends AppCompatActivity {

    //사이드바 변수 선언
    private ImageView btn_navi1;
    private DrawerLayout layout_drawer1;
    private NavigationView navi_view1;

    private Button detective_Button;
    private View.OnClickListener detective_Listener;
    int i = 1;

    private static int BLUETOOTH_STATE_UNKNOWN = -1;
    final static int BT_REQUEST_ENABLE = 1;


    //취소버튼 누를때 전전 액티비티로 넘어가기위해서 필요한 선언
    public static Class MainActiveActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_active);

        Intent intent2 = getIntent();
        MainActivity.ConnectedBluetoothThread mThreadConnectedBluetooth = (MainActivity.ConnectedBluetoothThread) intent2.getSerializableExtra("user");



        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);


        //사이드바 변수 할당
        btn_navi1 = findViewById(R.id.btn_navi1);
        layout_drawer1 = findViewById(R.id.layout_drawer1);
        navi_view1 = findViewById(R.id.naviview1);

        //사이드바 이미지 터치시 열리는 기능
        btn_navi1.setOnClickListener(v -> {
            layout_drawer1.openDrawer(GravityCompat.START); // START : left, END : right 랑 같은 말
        });
        // 사이드바 메뉴 아이템에 클릭시 속성 부여
        navi_view1.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.helmetBoxText1:
                        Intent intent = new Intent(getApplicationContext(), InformationScrollViewActivity.class);
                        startActivity(intent);
                        break;// 여기에 이용안내들어가고
                    case R.id.backDetectionText1:
                        Intent intent1 = new Intent(getApplicationContext(), InformationBackActivity.class);
                        startActivity(intent1); // 여기에 후방감지 들어가고
                        break;
                    case R.id.suggestion1:
                        Intent intent2 = new Intent(getApplicationContext(), QuestionsReportActivity.class);
                        startActivity(intent2); // 일단 건의함을 정리한거 들어가고
                        break;
                    case R.id.login1:
                        Toast.makeText(getApplicationContext(), "로그아웃", Toast.LENGTH_SHORT).show(); //로그인 페이지 들어감
                        break;
                }
                layout_drawer1.closeDrawers(); // 클릭 후 사이드바 닫힘
                return false;
            }
        });

        detective_Button = (Button) findViewById(R.id.backDetectionButton);
        detective_Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i % 2 == 1) {

                    // 블루투스 통신을 통해 후방감지 기능 off(구현하기)

                    detective_Button.setText("Off");
                    detective_Button.setBackgroundResource(R.drawable.main_button_shape);
                    detective_Button.setTextColor(Color.parseColor("#00C6C1"));
                    i++;

                } else if (i % 2 == 0) {

                    // 블루투스 통신을 통해 후방감지 기능 on(구현하기)


                    detective_Button.setText("On");
                    detective_Button.setBackgroundResource(R.drawable.main_backdetection_on_shape);
                    detective_Button.setTextColor(Color.parseColor("#FFFFFF"));
                    i++;
                } // 숫자를 0과 1로 반복되게 변화를 주어서 색변환 동작을 하게 구현
            }

        };

        detective_Button.setOnClickListener(detective_Listener);


        //취소버튼 누를때 전전 액티비티로 넘어가기위해서 필요한 선언
        MainActiveActivity = MainActiveActivity.this.getClass();

        //헬멧박스open 버튼 선언 후 클릭했을때 이벤트 발생(open할지말지 정하는 팝업창 뜸)
        Button boxOpenButton = (Button) findViewById(R.id.boxOpenButton);
        boxOpenButton.setOnClickListener(v -> {

            mThreadConnectedBluetooth.write("123");
            Intent intent = new Intent(getApplicationContext(), BoxOpenPopUpActivity.class);
            startActivity(intent);


        });




        //(반납할건지 말건지 정하는 팝업창 뜸)
        Button returnButton = (Button) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ReturnFirstActivity.class);
            startActivity(intent);
        });

    }
    // 사이드바 켜지고 뒤로가기 버튼 누르면 사이드바 닫힘 구현
    @Override
    public void onBackPressed() {
        if (layout_drawer1.isDrawerOpen(GravityCompat.START)) {
            layout_drawer1.closeDrawers();
        } else {
            super.onBackPressed(); //일반 백버튼 기능 수행
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    // 중간에 블루투스 끄면 뜨는 창
    private final BroadcastReceiver mReceiver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BLUETOOTH_STATE_UNKNOWN);
                if (state == BluetoothAdapter.STATE_OFF) {
                    //비활성화 되어 있다면 Intent 를 이용하여 활성화 창을 띄워 onActivityResult 에서 결과를 처리하게끔 함
                    Intent intentBluetoothEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intentBluetoothEnable, BT_REQUEST_ENABLE);
                }

            }
        }

    };

    //중간에 불루투스 끄고나서 뜬 창에서 예 아니오 버튼 둘중 하나 눌렀을떄
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
                            //비활성화 되어 있다면 Intent 를 이용하여 활성화 창을 띄워 onActivityResult 에서 결과를 처리하게끔 함
                            Intent intentBluetoothEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(intentBluetoothEnable, BT_REQUEST_ENABLE);
                        }
                    }, 2000);
                    Toast.makeText(MainActiveActivity.this, "블루투스 허용 취소 시 서비스 이용이 불가능합니다.", Toast.LENGTH_SHORT).show();

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}