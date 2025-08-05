package com.buildx.app.ui.guest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.buildx.app.LoginActivity
import com.buildx.app.MainActivity
import com.buildx.app.R

/**
 * نشاط وضع الضيف الذي يسمح للمستخدمين بتجربة التطبيق بدون تسجيل دخول
 */
class GuestModeActivity : AppCompatActivity() {

    private lateinit var btnContinueAsGuest: Button
    private lateinit var btnLogin: Button
    private lateinit var tvGuestModeInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_mode)

        // تهيئة العناصر
        btnContinueAsGuest = findViewById(R.id.btn_continue_as_guest)
        btnLogin = findViewById(R.id.btn_login)
        tvGuestModeInfo = findViewById(R.id.tv_guest_mode_info)

        // إعداد المستمعين للنقر
        btnContinueAsGuest.setOnClickListener {
            // حفظ حالة وضع الضيف في التفضيلات المشتركة
            val sharedPreferences = getSharedPreferences("BuildXPrefs", MODE_PRIVATE)
            sharedPreferences.edit().putBoolean("isGuestMode", true).apply()

            // الانتقال إلى النشاط الرئيسي
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnLogin.setOnClickListener {
            // الانتقال إلى شاشة تسجيل الدخول
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // عرض معلومات وضع الضيف
        tvGuestModeInfo.text = getString(R.string.guest_mode_info)
    }
}