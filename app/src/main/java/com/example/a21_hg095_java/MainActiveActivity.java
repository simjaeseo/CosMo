package com.example.a21_hg095_java;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActiveActivity extends AppCompatActivity {

    private Button detective_Button;
    private View.OnClickListener detective_Listener;
    int i = 1;
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

    //취소버튼 누를때 전전 액티비티로 넘어가기위해서 필요한 선언
    public static Class MainActiveActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_active);

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
        arrayList.add("로그아웃");

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

        detective_Button = (Button) findViewById(R.id.backDetectionButton);
        detective_Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i % 2 == 1) {
                    detective_Button.setText("후방감지on");
                    detective_Button.setBackgroundColor(Color.parseColor("#37b328"));
                    i++;
                } else if (i % 2 == 0) {
                    detective_Button.setText("후방감지off");
                    detective_Button.setBackgroundColor(Color.parseColor("#000000"));
                    i++;
                }
            }

        };

        detective_Button.setOnClickListener(detective_Listener);


        //취소버튼 누를때 전전 액티비티로 넘어가기위해서 필요한 선언
        MainActiveActivity = MainActiveActivity.this.getClass();

        //헬멧박스open 버튼 선언 후 클릭했을때 이벤트 발생(open할지말지 정하는 팝업창 뜸)
        Button boxOpenButton = (Button) findViewById(R.id.boxOpenButton);
        boxOpenButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BoxOpenPopUpActivity.class);
            startActivity(intent);
        });


    }
}