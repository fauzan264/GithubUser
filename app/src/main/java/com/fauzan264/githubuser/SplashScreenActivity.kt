package com.fauzan264.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            val moveMain = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(moveMain)
            finish()
        }, 3000)
    }
}