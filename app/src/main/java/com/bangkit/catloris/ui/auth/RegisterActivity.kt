package com.bangkit.catloris.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.catloris.R
import com.bangkit.catloris.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.registerButton.setOnClickListener {
            val logIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(logIntent)
            finish()
        }

        binding.loginBtn.setOnClickListener {
            val logbackIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(logbackIntent)
            finish()
        }
    }
}