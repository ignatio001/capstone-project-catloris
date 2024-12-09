package com.bangkit.catloris.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.catloris.api.ApiConfig
import com.bangkit.catloris.databinding.ActivityRegisterBinding
import com.bangkit.catloris.helper.RegisterViewModelFactory
import com.bangkit.catloris.helper.RegisterRepository
import com.bangkit.catloris.helper.RegisterRequest
import com.bangkit.catloris.helper.RegisterViewModel
import kotlin.random.Random

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(RegisterRepository(ApiConfig.getApiService()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupRegisterButton()
        observeViewModel()
    }

    private fun setupRegisterButton() {
        binding.registerButton.setOnClickListener {
            val userId = (1000..9999).random().toString()
            val fullname = binding.registerName.text.toString().trim()
            val email = binding.registerEmail.text.toString().trim()
            val password = binding.registerPass.text.toString().trim()
            val contact = binding.registerPhone.text.toString().trim()
            val gender = binding.registerGender.text.toString().trim()

            if (fullname.isEmpty() || email.isEmpty() || password.isEmpty() || contact.isEmpty() || gender.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = RegisterRequest(
                userId = userId,
                fullname = fullname,
                email = email,
                password = password,
                contact = contact,
                gender = gender
            )

            viewModel.registerUser(request)
        }
    }

    private fun observeViewModel() {
        viewModel.registerResult.observe(this) { response ->
            if (response.error == false) {
                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, response.message ?: "Register Gagal", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.registerButton.isEnabled = !isLoading
        }
    }
}