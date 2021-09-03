package com.example.a21_hg095_java;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.a21_hg095_java.data.SharedPreference;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter mBluetoothAdapter;
    //블루투스 활성화 상태
    final static int BT_REQUEST_ENABLE = 1;
    BluetoothDevice helmetbox;

    //사이드바 변수 선언
    private ImageView btn_navi;
    private DrawerLayout layout_drawer;
    private NavigationView navi_view;


    //테스트 코드(삭제 예정)
    BluetoothSocket mBluetoothSocket;
    final static int BT_CONNECTING_STATUS = 3;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    ConnectedBluetoothThread mThreadConnectedBluetooth;

    //취소버튼 누를때 전전 액티비티로 넘어가기위해서 필요한 선언
    public static Class MainActivity;

    //서비스 테스트
    private BTService _btService;

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

        //취소버튼 누를때 전전 액티비티로 넘어가기위해서 필요한 선언
        MainActivity = MainActivity.this.getClass();

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
//                //최종적으로는 Qr액티비티로 가도록 수정해야함
//                Intent intent = new Intent(MainActivity.this, MainActiveActivity.class);
//                startActivity(intent);

//                //서비스테스트
//                _btService = new BTService(getApplicationContext());




//                _btService.connect(SharedPreference.getInstance().getMacAddress());

//                String Address = "B8:27:EB:82:D7:27";
//                helmetbox = mBluetoothAdapter.getRemoteDevice(Address);

//        try {
//
//            // 우리가 연결에 필요한 값은 장치의 주소로 for 문으로 페어링 된 모든 장치를 검색을 하면서 매개 변수 값과 비교하여 같다면 그 장치의 주소 값을 얻어옵니다
//            // 그러면 mBluetoothDevice에 연결될 mBluetoothSocket이 초기화되며
//            mBluetoothSocket = helmetbox.createRfcommSocketToServiceRecord(BT_UUID);
//            //그 후 connect()를 호출하여 연결을 시작
//            mBluetoothSocket.connect();
//            //우리가 블루투스를 연결하고 데이터를 전송할 때는 우리가 원하는 때에 전송하면 됩니다. 하지만 데이터는 언제 수신받을 지 몰라 데이터 수신을 위한 쓰레드를 따로 만들어서 처리해야 합니다.
//            // 그에 따라 mBluetoothSocket 를 매개변수로 mThreadConnectedBluetooth 쓰레드를 생성해줍니다.
//            mThreadConnectedBluetooth = new ConnectedBluetoothThread(mBluetoothSocket);
//            mThreadConnectedBluetooth.start();
////            mBluetoothHandler.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget();
//        } catch (IOException e) {
//            Toast.makeText(getApplicationContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
//        }
                Intent intent = new Intent(MainActivity.this, QrActivity.class);
//                intent.putExtra("user", mThreadConnectedBluetooth); startActivity(intent);
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







    //ConnectedBluetoothThread 쓰레드
    //ConnectedBluetoothThread 스레드의 시작이며 이 스레드에서 사용할 전역 객체들을 선언
    //위에서 사용한 소켓이 이미 메인 액티비티 자체의 소켓이니 그대로 사용해도 되지만 쓰레드 내부 자체에서만 사용할 소켓 객체를 추가하였습니다.
    public class ConnectedBluetoothThread extends Thread implements Serializable {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //쓰레드 초기화 과정입니다. getInputStream()와 getOutputStream()을 사용하여 소켓을 통한 전송을 처리하는 InputStream 및 OutputStream을 가져옴
        //간단하게 말하자면 데이터 전송 및 수신하는 길을 만들어주는 작업
        public ConnectedBluetoothThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;

        }
        //수신받은 데이터는 언제 들어올지 모르니 항상 확인해야 합니다. 그에 따라 while 반복문 처리로 데이터가 존재한다면 데이터를 읽어오는 작업을 해줌
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
//                    bytes = mmInStream.available();
                      bytes = mmInStream.read(buffer);
                    if (bytes != 0) {
                        SystemClock.sleep(100);
                        bytes = mmInStream.available();
                        bytes = mmInStream.read(buffer, 0, bytes);
//                        mBluetoothHandler.obtainMessage(BT_MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                        Toast.makeText(getApplicationContext(), "발생했습니다.", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "발생했습니다.", Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }
        //데이터 전송을 위한 ConnectedBluetoothThread 스레드의 메서드로 88, 89번째 줄에서 사용
        public void write(String str) {
            byte[] bytes = str.getBytes();
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "데이터 전송 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }
        //블루투스 소켓을 닫는 메서드입니다. 애플리케이션을 닫으면 어차피 자동으로 닫아지니 여기서 따로 사용할 일은 없겠음
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 해제 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }
}





