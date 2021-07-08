package com.example.cosmo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class InitialActivity : AppCompatActivity() {
    private lateinit var boxOpenButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)
        boxOpenButton = findViewById(R.id.boxOpenButton)
        boxOpenButton.setClickable(false);

    }
}