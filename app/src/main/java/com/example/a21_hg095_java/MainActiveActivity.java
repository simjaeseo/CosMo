package com.example.a21_hg095_java;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.a21_hg095_java.data.SharedPreference;
import com.example.a21_hg095_java.network.RetrofitClient;
import com.example.a21_hg095_java.network.ServiceApi;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private ServiceApi service;
    String ex_longitude, ex_latitude;


    //수신테스트
    private BTService btService;
    private boolean mBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AndroidThreeTen.init(this);
        service = RetrofitClient.getClientWeatherAPI().create(ServiceApi.class);
        SharedPreference.getInstance().createBackDetectionStatus("true");

        // 강수량에 따라 배경 그림 바꾸기
//        attemptWeather();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_active);

//        Toast.makeText(MainActiveActivity.this, btService.receive(), Toast.LENGTH_SHORT).show();

//        Intent intent2 = getIntent();
//        MainActivity.ConnectedBluetoothThread mThreadConnectedBluetooth = (MainActivity.ConnectedBluetoothThread) intent2.getSerializableExtra("user");



        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);

        ex_latitude = SharedPreference.getInstance().getLatitude();
        ex_longitude = SharedPreference.getInstance().getLongitude();

        //사이드바 변수 할당
        btn_navi1 = findViewById(R.id.btn_navi1);
        layout_drawer1 = findViewById(R.id.layout_drawer1);
        navi_view1 = findViewById(R.id.naviview1);

        View nav_header_view = navi_view1.getHeaderView(0);

        TextView nav_header_id_text = (TextView) nav_header_view.findViewById(R.id.userName);
        nav_header_id_text.setText(SharedPreference.getInstance().getUserName() + "님!");

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
//                    case R.id.login1:
//                        Toast.makeText(getApplicationContext(), "로그아웃", Toast.LENGTH_SHORT).show(); //로그인 페이지 들어감
//                        break;
                }
                layout_drawer1.closeDrawers(); // 클릭 후 사이드바 닫힘
                return false;
            }
        });

        detective_Button = (Button) findViewById(R.id.backDetectionButton);
        detective_Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detective_Button.setEnabled(false);
                // 후방감지 기능 off
                if (i % 2 == 1) {

                    //블루투스 통신을 통해 라즈베리파이에게 값을 전달
                    String goodStateCode = "400";
                    Handler handler = new Handler();

                    try {
                        // 블루투스 통신을 통해 값을 넘겨주기
                        btService.send("400");
                    }
                    catch (Exception e){
                        detective_Button.setEnabled(true);
                        Toast.makeText(MainActiveActivity.this, "123후방감지 기능 작동 중 에러가 발생했습니다. ", Toast.LENGTH_SHORT).show();
                    }

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if(goodStateCode.equals(btService.receive())){

                                SharedPreference.getInstance().createBackDetectionStatus("false");
                                detective_Button.setText("Off");
                                detective_Button.setBackgroundResource(R.drawable.main_button_shape);
                                detective_Button.setTextColor(Color.parseColor("#00C6C1"));
                                detective_Button.setEnabled(true);
                                i++;
                            }else{
                                Toast.makeText(MainActiveActivity.this, "후방감지 기능 작동 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                detective_Button.setEnabled(true);

                            }
                        }
                    }, 1500); //2초 딜레이 후 자동꺼짐



                    //후방감지 기능 on
                } else if (i % 2 == 0) {


                    //블루투스 통신을 통해 라즈베리파이에게 값을 전달
                    String goodStateCode = "500";
                    Handler handler = new Handler();

                    try {
                        detective_Button.setEnabled(false);

                        // 블루투스 통신을 통해 값을 넘겨주기
                        btService.send("500");
                    }
                    catch (Exception e){
                        detective_Button.setEnabled(true);

                        Toast.makeText(MainActiveActivity.this, "123후방감지 기능 작동 중 에러가 발생했습니다. ", Toast.LENGTH_SHORT).show();
                    }

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if(goodStateCode.equals(btService.receive())){

                                SharedPreference.getInstance().createBackDetectionStatus("true");
                                detective_Button.setText("On");
                                detective_Button.setBackgroundResource(R.drawable.main_backdetection_on_shape);
                                detective_Button.setTextColor(Color.parseColor("#FFFFFF"));
                                detective_Button.setEnabled(true);

                                i++;
                            }else{
                                Toast.makeText(MainActiveActivity.this, "후방감지 기능 작동 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }, 1500); //2초 딜레이 후 자동꺼짐


                } // 숫자를 0과 1로 반복되게 변화를 주어서 색변환 동작을 하게 구현
            }

        };

        detective_Button.setOnClickListener(detective_Listener);


        //취소버튼 누를때 전전 액티비티로 넘어가기위해서 필요한 선언
        MainActiveActivity = MainActiveActivity.this.getClass();

        //헬멧박스open 버튼 선언 후 클릭했을때 이벤트 발생(open할지말지 정하는 팝업창 뜸)
        Button boxOpenButton = (Button) findViewById(R.id.boxOpenButton);
        boxOpenButton.setOnClickListener(v -> {

//            mThreadConnectedBluetooth.write("123");
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
//            super.onBackPressed(); //일반 백버튼 기능 수행\
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeIntent);
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




//
//    private void attemptWeather() {
//        LocalTime now = LocalTime.now();
//        int hour = now.getHour();
//
//        String pageNo = "1";
//        if(hour <= 9){
//            pageNo = "1";
//        }else if (hour<=13) {
//            pageNo = "2";
//        }else if (hour <= 17){
//            pageNo = "3";
//
//        }else if (hour <= 22){
//            pageNo = "4";
//        }
//
//        String numOfRows = "50";
//        String dataType = "JSON";
//        String base_date = "20211003";
//        String base_time = "0500";
//        String nx = SharedPreference.getInstance().getLongitude();
//        String ny = SharedPreference.getInstance().getLatitude();
//
//        startWeather(pageNo, numOfRows, dataType, base_date, base_time, nx, ny);
//    }
//
//
//    private void startWeather(
//            String pageNo,
//            String numOfRows,
//            String dataType,
//            String base_date,
//            String base_time,
//            String nx,
//            String ny) {
//        service.userWeather(pageNo, numOfRows, dataType, base_date, base_time, nx, ny).enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//
//                LocalTime now = LocalTime.now();
//                int hour = now.getHour();
//                String hour2 =  "";
//
//                if( hour < 10){
//                    hour2 = "0"+ hour +"00";
//                }else {
//                    hour2 = hour + "00";
//                }
//
//                //파싱
//                JsonArray item = response.body().getAsJsonObject("response").getAsJsonObject("body").getAsJsonObject("items").getAsJsonArray("item");
//                String c = "";
//                for (int i = 0; i < item.size(); i++) {
//                    JsonElement ele = item.get(i);
//                    if(ele.getAsJsonObject().get("category").getAsString().equals("PCP") && ele.getAsJsonObject().get("fcstTime").getAsString().equals(hour2) ){
//                        c = ele.getAsJsonObject().get("fcstValue").getAsString();   // 강수관한 정보가 c에 저장됨
//                    }
//
//
////                    nowWeather.setText(c);
//
//                    //여기서 배경화면 바꾸기 !!!!!!!!!!!!!!!
//                    //조건 따져야함 강수없음? 몇미리? 그외?(시간 벗어났을때)
//
//                }
//
////                test123.setText( obj.toString() );
//
////                Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
//
////                if (response.body().getitem().isEmpty()) {
////
////                    Toast.makeText(MainActivity.this, response.body().getitem().toString(), Toast.LENGTH_SHORT).show();
////
////                    /*Toast.makeText(LoginActivity.this, SharedPreference.getInstance().getToken(), Toast.LENGTH_SHORT).show();*/
////
////
////                }else{
////                    Toast.makeText(MainActivity.this, response.body().getitem().toString(), Toast.LENGTH_SHORT).show();
////                }
//            }
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(MainActiveActivity.this, "날씨 에러 발생", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//    @Override
//    public void onResume(){
//        super.onResume();
//
//
//
//
//        if( ex_latitude != SharedPreference.getInstance().getLatitude() ||  ex_longitude != SharedPreference.getInstance().getLongitude() ) {
//
//
//            SharedPreference.getInstance().createLatitude(SharedPreference.getInstance().getLatitude());
//            Toast.makeText(getApplicationContext(), "로그인", Toast.LENGTH_SHORT).show(); //로그아웃 페이지 들어감
//
//            test123.setText(SharedPreference.getInstance().getLatitude());
//
//            SharedPreference.getInstance().createLongitude(SharedPreference.getInstance().getLongitude());
//            test1234.setText(SharedPreference.getInstance().getLongitude());
//
//
//
//            attemptWeather();
//            ex_latitude = SharedPreference.getInstance().getLatitude();
//            ex_longitude = SharedPreference.getInstance().getLongitude();
//
//        }
//
//
//
////        SharedPreference.getInstance().createLatitude( Double.toString(longitude));
////        SharedPreference.getInstance().createLongitude(Double.toString(latitude));
//    }

}