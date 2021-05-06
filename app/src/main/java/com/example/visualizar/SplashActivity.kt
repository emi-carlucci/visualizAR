package com.example.visualizar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {

    private val splashTime = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Thread.sleep(splashTime.toLong())
        val splashIntent = Intent(this, LoginActivity::class.java).apply {}
        startActivity(splashIntent)
    }
}