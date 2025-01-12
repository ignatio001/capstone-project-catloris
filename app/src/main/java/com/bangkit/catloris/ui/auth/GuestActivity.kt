package com.bangkit.catloris.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.catloris.MainActivity
import com.bangkit.catloris.databinding.ActivityGuestBinding

class GuestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuestBinding
    private lateinit var nameGuest: String
    private lateinit var emailGuest: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.guestEnter.setOnClickListener { toDashboard() }
        binding.guestBack.setOnClickListener { toLogin() }

    }

    private fun toLogin() {
        val backLogin = Intent(this, LoginActivity::class.java)
        startActivity(backLogin)
        finish()
    }

    private fun toDashboard() {
        nameGuest = binding.guestName.text.toString().trim()
        emailGuest = binding.guestEmail.text.toString().trim()

        if (nameGuest.isEmpty() || emailGuest.isEmpty()) {
            Toast.makeText(this, "Please Fill out your data", Toast.LENGTH_SHORT).show()
        } else {
            val namePreferences = getSharedPreferences("name_user", MODE_PRIVATE)
            namePreferences.edit()
                .putString("name_user", nameGuest)
                .apply()

            val emailPreferences = getSharedPreferences("email_guest", MODE_PRIVATE)
            emailPreferences.edit()
                .putString("email_guest", emailGuest)
                .apply()

            val dashIntent = Intent(this, MainActivity::class.java).apply {
                putExtra("name_user", nameGuest)
                putExtra("email_guest", emailGuest)
            }
            startActivity(dashIntent)
            finish()
        }
    }
}