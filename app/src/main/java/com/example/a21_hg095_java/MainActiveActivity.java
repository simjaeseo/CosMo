package com.example.a21_hg095_java;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActiveActivity extends AppCompatActivity {

    private Button detective_Button;
    private ImageView imageView;
    private View.OnClickListener detective_Listener;
    int i = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_active);

        detective_Button = (Button) findViewById(R.id.backDetectionButton);
        detective_Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i % 2 == 1) {
                    detective_Button.setText("후방감지on");
                    i++;
                } else if (i % 2 == 0) {
                    detective_Button.setText("후방감지off");
                    i++;
                }
            }

        };
    }
}