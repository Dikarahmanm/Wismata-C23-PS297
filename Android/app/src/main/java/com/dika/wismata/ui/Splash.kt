package com.dika.wismata.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.dika.wismata.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val DURATION = 2000L
class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            delay(DURATION)
            val intent = Intent(this@Splash, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}