package com.example.successcontribution

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.successcontribution.screens.login.LoginActivity

import android.content.Intent
import android.os.Handler
import com.example.successcontribution.shared.Constant.TIMER

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed(Runnable {
            startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            finish()
        }, TIMER)

    }
}