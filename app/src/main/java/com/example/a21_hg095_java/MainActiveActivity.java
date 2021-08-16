package com.example.a21_hg095_java;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

public class MainActiveActivity extends AppCompatActivity {

    //사이드바 변수 선언
    private ImageView btn_navi1;
    private DrawerLayout layout_drawer1;
    private NavigationView navi_view1;

    private Button detective_Button;
    private View.OnClickListener detective_Listener;
    int i = 1;

    //취소버튼 누를때 전전 액티비티로 넘어가기위해서 필요한 선언
    public static Class MainActiveActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_active);

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
                        Toast.makeText(getApplicationContext(), "헬멧박스 이용안내", Toast.LENGTH_SHORT).show(); // 여기에 이용안내들어가고
                    case R.id.backDetectionText1:
                        Toast.makeText(getApplicationContext(), "후방감지 이용안내", Toast.LENGTH_SHORT).show(); // 여기에 후방감지 들어가고
                        break;
                    case R.id.suggestion1:
                        Toast.makeText(getApplicationContext(), "건의함", Toast.LENGTH_SHORT).show(); // 일단 건의함을 정리한거 들어가고
                        break;
                    case R.id.login1:
                        Toast.makeText(getApplicationContext(), "로그인", Toast.LENGTH_SHORT).show(); //로그인 페이지 들어감
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
                    detective_Button.setText("후방감지on");
                    detective_Button.setBackgroundColor(Color.parseColor("#37b328"));
                    i++;
                } else if (i % 2 == 0) {
                    detective_Button.setText("후방감지off");
                    detective_Button.setBackgroundColor(Color.parseColor("#BFDCFE"));
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
            Intent intent = new Intent(getApplicationContext(), BoxOpenPopUpActivity.class);
            startActivity(intent);
        });




        //헬멧박스open 버튼 선언 후 클릭했을때 이벤트 발생(반납할건지 말건지 정하는 팝업창 뜸)
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
}