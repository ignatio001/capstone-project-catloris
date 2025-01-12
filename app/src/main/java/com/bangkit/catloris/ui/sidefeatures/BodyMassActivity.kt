package com.bangkit.catloris.ui.sidefeatures

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.catloris.MainActivity
import com.bangkit.catloris.R
import com.bangkit.catloris.databinding.ActivityBodyMassBinding

class BodyMassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBodyMassBinding
    private lateinit var whoCategory: String
    private lateinit var asiaCategory: String
    private lateinit var bmiResult: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBodyMassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.bmiResultButton.setOnClickListener { bmiResult() }

        binding.button.setOnClickListener {
            val weight = binding.weight.text.toString().toDoubleOrNull()
            val height = binding.height.text.toString().toDoubleOrNull()
            val age = binding.age.text.toString().toIntOrNull()

            if (weight == null || height == null || age == null) {
                Toast.makeText(this, "Please fill your data correctly", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val bmi = weight / ((height / 100) * (height / 100))

            whoCategory = when {
                bmi < 18.5 -> "Underweight"
                bmi in 18.5..24.9 -> "Normal"
                bmi in 25.0..29.9 -> "Overweight"
                bmi in 30.0..39.9 -> "Obesity Type 1"
                bmi in 40.0..49.9 -> "Obesity Type 2"
                bmi >= 50.0 -> "Obesity Type 3"
                else -> "Undefined"
            }

            asiaCategory = when {
                bmi < 18.5 -> "Underweight"
                bmi in 18.5..22.9 -> "Normal"
                bmi in 23.0..24.9 -> "Overweight"
                bmi in 25.0..29.9 -> "Pra Obesity"
                bmi >= 30.0 -> "Obesity"
                else -> "Undefined"
            }

            Toast.makeText(this, whoCategory + asiaCategory, Toast.LENGTH_LONG).apply {
                setGravity(Gravity.CENTER, 0, 0)
                show()
            }

            binding.bmiResult.text = whoCategory
        }
    }

    private fun bmiResult() {
        bmiResult = binding.bmiResult.text.toString().trim()

        val bmiSharedPreferences = getSharedPreferences("bmi_user", MODE_PRIVATE)
        bmiSharedPreferences.edit()
            .putString("bmi_user", bmiResult)
            .apply()

        val saveResultIntent = Intent(this, MainActivity::class.java)
        saveResultIntent.putExtra("bmi_user", bmiResult)
        startActivity(saveResultIntent)
        finish()


    }
}