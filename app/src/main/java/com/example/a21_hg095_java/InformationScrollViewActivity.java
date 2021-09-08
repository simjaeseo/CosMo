package com.example.a21_hg095_java;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a21_hg095_java.data.SharedPreference;

public class InformationScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_scroll_view);
        SharedPreference.getInstance().createToken("");

        }
    }