package com.buildx.app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.buildx.app.ui.guest.GuestModeActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Simulate a delay for the splash screen
        Handler(Looper.getMainLooper()).postDelayed({
            // Check if user has seen onboarding and login status
            val sharedPreferences = getSharedPreferences("BuildXPrefs", MODE_PRIVATE)
            val hasSeenOnboarding = sharedPreferences.getBoolean("hasSeenOnboarding", false)
            val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
            val isGuestMode = sharedPreferences.getBoolean("isGuestMode", false)
            
            // Navigate to appropriate screen
            val intent = when {
                !hasSeenOnboarding -> Intent(this, OnboardingActivity::class.java)
                isLoggedIn -> Intent(this, MainActivity::class.java)
                isGuestMode -> Intent(this, MainActivity::class.java)
                else -> Intent(this, GuestModeActivity::class.java)
            }
            
            startActivity(intent)
            finish()
        }, 2000) // 2 seconds delay
    }
}