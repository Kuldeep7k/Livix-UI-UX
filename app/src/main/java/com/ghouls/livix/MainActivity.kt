package com.ghouls.livix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Delay for splash screen
        val splashScreenDuration = 3000L // 3 seconds

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainLogoScreen::class.java)
            startActivity(intent)
            finish()
        }, splashScreenDuration)
    }
}
