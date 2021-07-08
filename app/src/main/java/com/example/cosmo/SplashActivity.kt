package com.example.cosmo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        스플래시 화면 뜨고 3초뒤에 액티비티 전환
        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable{
            val intent = Intent(this, InitialActivity::class.java)
            startActivity(intent)
            finish()
        }
        handler.postDelayed(runnable,3000)
    }
}