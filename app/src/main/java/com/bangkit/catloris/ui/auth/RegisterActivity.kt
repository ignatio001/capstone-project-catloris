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

        binding.loginBtn.setOnClickListener {
            val logbackIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(logbackIntent)
            finish()
        }

    }

    private fun setupRegisterButton() {
        binding.registerButton.setOnClickListener {
            val userId = Random.nextInt(1000, 9999)
            val fullname = binding.registerName.text.toString()
            val email = binding.registerEmail.text.toString()
            val password = binding.registerPass.text.toString()
            val contact = binding.registerPhone.text.toString()
            val gender = binding.registerGender.text.toString()

            if (fullname.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && contact.isNotEmpty()) {
                viewModel.registerUser(userId, fullname, email, password, contact, gender)
            } else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            }

            val metricsIntent = Intent(this@RegisterActivity, ParameterUserActivity::class.java).apply {
                putExtra("user_id", userId)
            }
            startActivity(metricsIntent)
        }
    }


    private fun observeViewModel() {
        viewModel.registerState.observe(this) { response ->
            response?.let {
                if (it.error == false) {
                    Toast.makeText(this, it.message ?: "Register Successful", Toast.LENGTH_SHORT).show()
                } else if (it.error == true) {
                    Toast.makeText(this, it.message ?: "Register failed", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                Toast.makeText(this, "No response from server", Toast.LENGTH_SHORT).show()
            }
        }
    }



}