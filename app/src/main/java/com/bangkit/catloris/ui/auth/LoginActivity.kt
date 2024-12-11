package com.bangkit.catloris.ui.auth

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.catloris.MainActivity
import com.bangkit.catloris.databinding.ActivityLoginBinding
import com.bangkit.catloris.helper.LoginRepository
import com.bangkit.catloris.helper.LoginViewModel
import com.bangkit.catloris.helper.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(LoginRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupActions()
        observeViewModel()
        playAnimation()
    }

    private fun setupActions() {
        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString().trim()
            val password = binding.passLogin.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Tolong isi data Anda", Toast.LENGTH_SHORT).show()
            } else {
                login(email, password) // Mengirim data login
            }

            val sharedPreferences = getSharedPreferences("email_user", MODE_PRIVATE)
            sharedPreferences.edit()
                .putString("email_user", email)
                .apply()

            val mainIntent = Intent(this, MainActivity::class.java)
            mainIntent.putExtra("email_user", email)
            startActivity(mainIntent)
            finish()
        }

        binding.registerBtn.setOnClickListener {
            val regIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(regIntent)
            this.finish()
        }
    }

    private fun observeViewModel() {
        loginViewModel.loginResponse.observe(this) { response ->
            response?.let {
                if (!it.error!!) {
                    saveToken(it.data?.accessToken ?: "")
                    Toast.makeText(this, "Login Sukses", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        loginViewModel.errorState.observe(this) { errorMessage ->
            errorMessage?.let {
                Log.e("LoginActivity", it)
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login(email: String, password: String) {
        loginViewModel.login(email, password)
    }

    private fun saveToken(token: String) {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        sharedPreferences.edit()
            .putString("access_token", token)
            .apply()
    }


    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.loginImg, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 7000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }
}