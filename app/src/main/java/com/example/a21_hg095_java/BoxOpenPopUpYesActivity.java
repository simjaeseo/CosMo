package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class BoxOpenPopUpYesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView(R.layout.activity_box_open_pop_up_yes);


        //팝업창 클릭시 전전 액티비티로 전환됨(확인 버튼이 따로 없어서 이런식으로 구현해봄)
        //배경부분 클릭했을때도 구현해야할지 고민해야됨.
        TextView openMessageTextView = (TextView) findViewById(R.id.openMessageTextView);
        openMessageTextView.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActiveActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);
        });



    }
}