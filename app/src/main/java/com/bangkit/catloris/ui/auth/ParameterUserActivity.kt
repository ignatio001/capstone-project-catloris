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
import com.bangkit.catloris.databinding.ActivityParameterUserBinding
import com.bangkit.catloris.helper.MetricsViewModel

class ParameterUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParameterUserBinding
    private val metricsViewModel: MetricsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParameterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.metricsProgressbar.visibility = View.GONE

        val fatOption = arrayOf("Choose","Underweight", "Normal", "Overweight")
        val fatAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            fatOption
        )

        // For Submit Metrics User
        binding.submitParButton.setOnClickListener {
            val age = binding.parameterAgeUser.toString().toIntOrNull() ?: 0
            val height = binding.parameterHeightUser.text.toString().toFloatOrNull() ?: 0f
            val weight = binding.parameterWeightUser.text.toString().toFloatOrNull() ?: 0f
            val fatsPercentage = binding.parameterFatUser.selectedItem.toString()
            val fats: Float

            if (fatsPercentage == "Choose") {
                showToast("Choose fats calculation first!!")
                return@setOnClickListener
            } else if (fatsPercentage == "Underweight") {
                fats = 800f
            } else if (fatsPercentage == "Normal") {
                fats = 1500f
            } else {
                fats = 2000f
            }

            if (validateInput(age, height,weight, fats)) {
                metricsViewModel.metricsUser(age, height, weight, fats)
            }

            val logIntent = Intent(this, LoginActivity::class.java)
            startActivity(logIntent)
            this.finish()
        }

        observeViewModels()

        fatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.parameterFatUser.adapter = fatAdapter
    }

    private fun observeViewModels() {
        metricsViewModel.isLoading.observe(this, Observer { isLoading ->
            binding.metricsProgressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        metricsViewModel.metricsResult.observe(this, Observer { response ->
            if (response.error == true) {
                showToast("Submit Metrics Failed: ${response.message}")
            } else {
                showToast("Submit Metrics Successful: ${response.message}")
                finish()
            }
        })
    }

    private fun validateInput(age: Int, height: Float, weight: Float, fats: Float): Boolean {
        return when {
            age <= 0 -> {
                showToast("Age tidak boleh kosong")
                false
            }

            height <= 0f -> {
                showToast("Height tidak boleh kosong")
                false
            }

            weight <= 0f -> {
                showToast("Weight tidak boleh kosong")
                false
            }

            fats <= 0f -> {
                showToast("fats tidak boleh kosong")
                false
            }

            else -> true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}