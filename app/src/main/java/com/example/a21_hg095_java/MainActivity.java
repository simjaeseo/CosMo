package com.example.a21_hg095_java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    // 초기 변수 설정
    DrawerLayout drawerLayout;
    ImageView btMenu;
    RecyclerView recyclerView;
    public static ArrayList<String> arrayList = new ArrayList<>();
    com.example.navigationdrawer.MainAdapter adapter;

    public static void closeDrawer(DrawerLayout drawerLayout) {
        //Check condition
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            //사이드바 열려있으면
            //사이드바 닫아
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //변수 할당
        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);
        recyclerView = findViewById(R.id.recycler_view);

        //리스트 배열 초기화
        arrayList.clear();

        //메뉴 항목을 리스트배열에 추가하기
        arrayList.add("헬멧박스 이용안내");
        arrayList.add("후방감지 이용안내");
        arrayList.add("건의함");
        arrayList.add("로그인");

        //어댑터 초기화
        adapter = new com.example.navigationdrawer.MainAdapter(this, arrayList);
        //레이아웃 매니저 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //set adapter
        recyclerView.setAdapter(adapter);

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open drawer
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


    }

    public void startBarcodeReader(View activity_main) {
/*        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(false);//바코드 인식시 소리
        intentIntegrator.initiateScan();*/
        Intent intent = new Intent(getApplicationContext(),MainActiveActivity.class); // 다음 넘어갈 클래스 지정
        startActivity(intent);

    } //qr 인식화면

   /* @Override
    protected void onPause() {
        super.onPause();
        //close drawer
        closeDrawer(drawerLayout);
    }*/
}
