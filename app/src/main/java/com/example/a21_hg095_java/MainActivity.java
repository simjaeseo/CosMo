package com.example.a21_hg095_java;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.a21_hg095_java.data.SharedPreference;
import com.example.a21_hg095_java.network.RetrofitClient;
import com.example.a21_hg095_java.network.ServiceApi;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jakewharton.threetenabp.AndroidThreeTen;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    double latitude;
    double longitude;

    BluetoothAdapter mBluetoothAdapter;
    //블루투스 활성화 상태
    final static int BT_REQUEST_ENABLE = 1;
    BluetoothDevice helmetbox;

    //사이드바 변수 선언
    private ImageView btn_navi;
    private DrawerLayout layout_drawer;
    private NavigationView navi_view;

    TextView test123;
    TextView test1234;
    TextView nowWeather;
    String ex_longitude, ex_latitude;


    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final int GPS_UTIL_LOCATION_PERMISSION_REQUEST_CODE = 100;
    private static final int GPS_UTIL_LOCATION_RESOLUTION_REQUEST_CODE = 101;
    public static final int DEFAULT_LOCATION_REQUEST_PRIORITY = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
    public static final long DEFAULT_LOCATION_REQUEST_INTERVAL = 10000L;
    public static final long DEFAULT_LOCATION_REQUEST_FAST_INTERVAL = 5000L;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;

    // 마지막으로 뒤로 가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로 가기 버튼을 누를 때 표시
    private Toast toast;

    private ServiceApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AndroidThreeTen.init(this);
        service = RetrofitClient.getClientWeatherAPI().create(ServiceApi.class);

        // 강수량에 따라 배경 그림 바꾸기
        attemptWeather();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        test123 = (TextView) findViewById(R.id.test);
        test1234 = (TextView) findViewById(R.id.test2);
        nowWeather = (TextView) findViewById(R.id.nowWeather);
        //사이드바 변수 할당
        btn_navi = findViewById(R.id.btn_navi);
        layout_drawer = findViewById(R.id.layout_drawer);
        navi_view = findViewById(R.id.navi_view);

        View nav_header_view = navi_view.getHeaderView(0);

        TextView nav_header_id_text = (TextView) nav_header_view.findViewById(R.id.userName);
        TextView nav_header_textView1 = (TextView) nav_header_view.findViewById(R.id.nav_header_textView1);
        if(SharedPreference.getInstance().getToken().equals("")){
            nav_header_textView1.setText("로그인이 필요합니다!");
            nav_header_id_text.setText("");

        }else{
            nav_header_id_text.setText(SharedPreference.getInstance().getUserName() + "님!");
        }



        test123.setText(SharedPreference.getInstance().getLatitude());
        test1234.setText(SharedPreference.getInstance().getLongitude());

        ex_latitude = SharedPreference.getInstance().getLatitude();
        ex_longitude = SharedPreference.getInstance().getLongitude();

        //사이드바 이미지 터치시 열리는 기능
        btn_navi.setOnClickListener(v -> {
            if(SharedPreference.getInstance().getToken().equals("")){
                navi_view.getMenu().findItem(R.id.login).setTitle("로그인");
                nav_header_textView1.setText("로그인이 필요합니다!");
                nav_header_id_text.setText("");

            }else{
                navi_view.getMenu().findItem(R.id.login).setTitle("로그아웃");
                nav_header_id_text.setText(SharedPreference.getInstance().getUserName() + "님!");

            }
            layout_drawer.openDrawer(GravityCompat.START); // START : left, END : right 랑 같은 말
        });


        // 사이드바 메뉴 아이템에 클릭시 속성 부여
        navi_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() { // 네비게이션 메뉴 아이템에 클릭 속성 부여
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.helmetBoxText:
//                        attemptWeather();
//                        Intent intent = new Intent(getApplicationContext(), InformationScrollViewActivity.class);
//                        startActivity(intent);
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
                        if(item.getTitle().equals("로그아웃")) {
                            Intent intent3 = new Intent(MainActivity.this, LogoutPopupActivity.class);
                            startActivity(intent3);
                        }else{
                            Intent intent3 = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent3);
                        }


//                        Toast.makeText(getApplicationContext(), "로그인", Toast.LENGTH_SHORT).show(); //로그아웃 페이지 들어감
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
            if(SharedPreference.getInstance().getToken().equals("")){
                Toast.makeText(getApplicationContext(), "로그인을 먼저 진행해주세요.", Toast.LENGTH_LONG).show();
                return;
            }
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



    @Override
    public void onResume(){
        super.onResume();




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
////            attemptWeather();
//            ex_latitude = SharedPreference.getInstance().getLatitude();
//            ex_longitude = SharedPreference.getInstance().getLongitude();
//
//        }



//        SharedPreference.getInstance().createLatitude( Double.toString(longitude));
//        SharedPreference.getInstance().createLongitude(Double.toString(latitude));
    }


    // 사이드바 켜지고 뒤로가기 버튼 누르면 사이드바 닫힘 구현
    @Override
    public void onBackPressed() {
        if (layout_drawer.isDrawerOpen(GravityCompat.START)) {
            layout_drawer.closeDrawers();
        } else {
//            super.onBackPressed(); //일반 백버튼 기능 수행
            //super.onBackPressed();
            // 기존 뒤로 가기 버튼의 기능을 막기 위해 주석 처리 또는 삭제

            // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
            // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지났으면 Toast 출력
            // 2500 milliseconds = 2.5 seconds
            if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
                backKeyPressedTime = System.currentTimeMillis();
                toast = Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG);
                toast.show();
                return;
            }
            // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
            // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지나지 않았으면 종료
            if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
                finish();
                toast.cancel();
                toast = Toast.makeText(this,"이용해 주셔서 감사합니다.",Toast.LENGTH_LONG);
                toast.show();
            }

//            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
//            homeIntent.addCategory(Intent.CATEGORY_HOME);
//            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(homeIntent);
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








    private void attemptWeather() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();

        LocalDate now2 = LocalDate.now();
        String year = Integer.toString(now2.getYear());
        int monthValue = now2.getMonthValue();
        int dayOfMonth = now2.getDayOfMonth();

        System.out.println(monthValue);
        String month = "";
        String day = "";

        month = Integer.toString(monthValue);
        day = Integer.toString(dayOfMonth);

        if(monthValue <10){
            month = "0"+ monthValue;
        }else if(dayOfMonth < 10){
            day = "0" + dayOfMonth;
        }





        String pageNo = "1";
        if(hour <= 9){
            pageNo = "1";
        }else if (hour<=13) {
            pageNo = "2";
        }else if (hour <= 17){
            pageNo = "3";

        }else if (hour <= 22){
            pageNo = "4";
        }

        String numOfRows = "50";
        String dataType = "JSON";
        String base_date = year + month + day;
        String base_time = "0500";
        String nx = SharedPreference.getInstance().getLongitude();
        String ny = SharedPreference.getInstance().getLatitude();
//        Toast.makeText(getApplicationContext(), base_date, Toast.LENGTH_LONG).show();

        startWeather(pageNo, numOfRows, dataType, base_date, base_time, nx, ny);
    }


    private void startWeather(String pageNo, String numOfRows, String dataType, String base_date, String base_time, String nx, String ny) {
        service.userWeather(pageNo, numOfRows, dataType, base_date, base_time, nx, ny).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                LocalTime now = LocalTime.now();
                int hour = now.getHour();
                String hour2 =  "";

                if( hour < 10){
                    hour2 = "0"+ hour +"00";
                }else {
                    hour2 = hour + "00";
                }

                //파싱
                JsonArray item = response.body().getAsJsonObject("response").getAsJsonObject("body").getAsJsonObject("items").getAsJsonArray("item");
                String c = "";
                for (int i = 0; i < item.size(); i++) {
                    JsonElement ele = item.get(i);
                    if(ele.getAsJsonObject().get("category").getAsString().equals("PCP") && ele.getAsJsonObject().get("fcstTime").getAsString().equals(hour2) ){
                            c = ele.getAsJsonObject().get("fcstValue").getAsString();   // 강수관한 정보가 c에 저장됨
                    }


                    nowWeather.setText(c);

                    // 비가 올때
                    if(!c.equals("강수 없음")){
                        layout_drawer.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_background_image_rain));

                    }

                    //여기서 배경화면 바꾸기 !!!!!!!!!!!!!!!
                    //조건 따져야함 강수없음? 몇미리? 그외?(시간 벗어났을때)

                }

//                test123.setText( obj.toString() );

//                Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();

//                if (response.body().getitem().isEmpty()) {
//
//                    Toast.makeText(MainActivity.this, response.body().getitem().toString(), Toast.LENGTH_SHORT).show();
//
//                    /*Toast.makeText(LoginActivity.this, SharedPreference.getInstance().getToken(), Toast.LENGTH_SHORT).show();*/
//
//
//                }else{
//                    Toast.makeText(MainActivity.this, response.body().getitem().toString(), Toast.LENGTH_SHORT).show();
//                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MainActivity.this, "날씨 에러 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }







}





