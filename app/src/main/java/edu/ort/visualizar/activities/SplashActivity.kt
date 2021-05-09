package edu.ort.visualizar.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.visualizar.R


class SplashActivity : AppCompatActivity() {

    private val splashTime = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        Handler().postDelayed({
            val splashIntent = Intent(this, LoginActivity::class.java)
            startActivity(splashIntent)
            finish()
        }, splashTime.toLong())
    }
}