package com.ghouls.livix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed

class MainLogoScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_logo_screen)

        Handler(Looper.getMainLooper()).postDelayed(2000){
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}