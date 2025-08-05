package com.buildx.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: Button
    private lateinit var tvForgotPassword: TextView
    private lateinit var tvRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize views
        tilEmail = findViewById(R.id.til_email)
        tilPassword = findViewById(R.id.til_password)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        tvForgotPassword = findViewById(R.id.tv_forgot_password)
        tvRegister = findViewById(R.id.tv_register)

        // Set click listeners
        btnLogin.setOnClickListener {
            if (validateInputs()) {
                // For demo purposes, we'll just navigate to MainActivity
                // In a real app, you would authenticate with a server
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        tvForgotPassword.setOnClickListener {
            // Show forgot password dialog or navigate to forgot password screen
            Toast.makeText(this, "Forgot password clicked", Toast.LENGTH_SHORT).show()
        }

        tvRegister.setOnClickListener {
            // Navigate to register screen
            Toast.makeText(this, "Register clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        // Validate email
        val email = etEmail.text.toString().trim()
        if (email.isEmpty()) {
            tilEmail.error = "البريد الإلكتروني مطلوب"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.error = "البريد الإلكتروني غير صالح"
            isValid = false
        } else {
            tilEmail.error = null
        }

        // Validate password
        val password = etPassword.text.toString()
        if (password.isEmpty()) {
            tilPassword.error = "كلمة المرور مطلوبة"
            isValid = false
        } else if (password.length < 6) {
            tilPassword.error = "كلمة المرور يجب أن تكون 6 أحرف على الأقل"
            isValid = false
        } else {
            tilPassword.error = null
        }

        return isValid
    }
}