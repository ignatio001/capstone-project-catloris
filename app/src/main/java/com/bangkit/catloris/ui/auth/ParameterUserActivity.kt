package com.bangkit.catloris.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.catloris.api.ApiConfig
import com.bangkit.catloris.databinding.ActivityParameterUserBinding
import com.bangkit.catloris.helper.MetricsRepository
import com.bangkit.catloris.helper.MetricsRequest
import com.bangkit.catloris.helper.MetricsViewModel
import com.bangkit.catloris.helper.MetricsViewModelFactory

class ParameterUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParameterUserBinding
    private val viewModel: MetricsViewModel by viewModels {
        MetricsViewModelFactory(MetricsRepository(ApiConfig.getApiService()))
    }

    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParameterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        userId = intent.getIntExtra("user_id", -1)

        val fatsOptions = arrayOf("Choose", "Underweight", "Ideal", "Overweight")
        val fatsValues = arrayOf(0f, 800f, 1500f, 2000f)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, fatsOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.parameterFatUser.adapter = adapter

        binding.parameterFatUser.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                binding.fatsValue.text = fatsValues[position].toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                binding.fatsValue.text = "0"
            }
        }

        setupSaveButton()
        observeViewModels()
    }

    private fun setupSaveButton() {
        binding.submitParButton.setOnClickListener {
            val userId = userId
            val age = binding.parameterAgeUser.text.toString().toIntOrNull()
            val height = binding.parameterHeightUser.text.toString().toFloatOrNull()
            val weight = binding.parameterWeightUser.text.toString().toFloatOrNull()
            val fats = binding.parameterFatUser.selectedItem.toString().trim()
            val fatValue = binding.fatsValue.text.toString().toFloatOrNull()

            if (userId == -1 && age == null && height == null && weight == null && fats == "Choose") {
                Toast.makeText(this, "All Fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = MetricsRequest(
                userId = userId,
                age = age,
                height = height,
                weight = weight,
                fats = fatValue
            )

            viewModel.metricsUser(request)

            val logIntent = Intent(this@ParameterUserActivity, LoginActivity::class.java)
            startActivity(logIntent)
            finish()
        }
    }

    private fun observeViewModels() {
        viewModel.metricsResult.observe(this) { response ->
            if (response.error == false) {
                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, response.message ?: "Metrics Failed to Upload", Toast.LENGTH_SHORT).show()
            }

            viewModel.isLoading.observe(this) { isLoading ->
                binding.submitParButton.isEnabled = !isLoading
            }

        }
    }

}