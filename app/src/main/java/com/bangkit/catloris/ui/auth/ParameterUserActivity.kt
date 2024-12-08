package com.bangkit.catloris.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.bangkit.catloris.R
import com.bangkit.catloris.api.ApiConfig
import com.bangkit.catloris.databinding.ActivityParameterUserBinding
import com.bangkit.catloris.helper.MetricsRepository
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

        setupSpinner()
        setupSaveButton()
        observeViewModels()
    }

    private fun setupSaveButton() {
        binding.submitParButton.setOnClickListener {
            val age = binding.parameterAgeUser.text.toString().toIntOrNull()
            val height = binding.parameterHeightUser.text.toString().toFloatOrNull()
            val weight = binding.parameterWeightUser.text.toString().toFloatOrNull()
            val fats = binding.parameterFatUser.selectedItem.toString()

            if (age != null && height != null && weight != null && fats != "Choose") {
                viewModel.sendMetrics(userId, age, height, weight, fats.toFloat())
            } else {
                Toast.makeText(this, "All fields are required and fats option must be selected", Toast.LENGTH_SHORT).show()
            }

            val logIntent = Intent(this@ParameterUserActivity, LoginActivity::class.java)
            startActivity(logIntent)
            finish()
        }
    }

    private fun setupSpinner() {
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
    }

    private fun observeViewModels() {
        viewModel.metricsState.observe(this) { response ->
            response?.let {
                if (it.error!!) {
                    Toast.makeText(this, it.message ?: "Metrics saved successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.message ?: "Failed to save metrics", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}