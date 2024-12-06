package com.bangkit.catloris.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.bangkit.catloris.R
import com.bangkit.catloris.databinding.ActivityRegisterBinding
import com.bangkit.catloris.helper.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.registerProgressbard.visibility = View.GONE

        val genOption = arrayOf("Choose Gender","Male", "Female")
        val genAdapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item,
            genOption
        )

        // For Register
        binding.registerButton.setOnClickListener {
            val name = binding.registerName.toString().trim()
            val email = binding.registerEmail.toString().trim()
            val contact = binding.registerPhone.toString().trim()
            val password = binding.registerPass.toString().trim()
            val gender = binding.registerGender.selectedItem.toString()

            if (gender == "Choose Gender") {
                showToast("Choose your Gender First!!")
                return@setOnClickListener
            }

            if (validateInput(name, email, password, contact, gender)) {
                registerViewModel.registerUser(name, email, password, contact, gender)
            }

            val logIntent = Intent(this@RegisterActivity, ParameterUserActivity::class.java)
            logIntent.putExtra("par_username", name)
            startActivity(logIntent)
        }

        observeViewModels()

        binding.loginBtn.setOnClickListener {
            val logbackIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(logbackIntent)
            finish()
        }



        genAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.registerGender.adapter = genAdapter


    }

    private fun observeViewModels() {
        registerViewModel.isLoading.observe(this, Observer { isLoading ->
            binding.registerProgressbard.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        registerViewModel.registerResult.observe(this, Observer { response ->
            if (response.error == true) {
                showToast("Registration Failed: ${response.message}")
            } else {
                showToast("Registration Successfull: ${response.message}")
                finish()
            }
        })
    }

    private fun validateInput(name: String, email: String, password: String, contact: String, gender: String): Boolean {
        return when {
            name.isEmpty() -> {
                showToast("Nama tidak boleh kosong")
                false
            }
            email.isEmpty() -> {
                showToast("Email tidak boleh kosong")
                false
            }
            password.isEmpty() -> {
                showToast("Password tidak boleh kosong")
                false
            }

            contact.isEmpty() -> {
                showToast("Contact tidak boleh kosong")
                false
            }
            gender.isEmpty() -> {
                showToast("Gender harus pilih")
                false
            }

            else -> true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}