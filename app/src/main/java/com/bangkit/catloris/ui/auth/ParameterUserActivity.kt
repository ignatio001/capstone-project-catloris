package com.bangkit.catloris.ui.auth

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.catloris.R
import com.bangkit.catloris.databinding.ActivityParameterUserBinding

class ParameterUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParameterUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParameterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fatOption = arrayOf("Underweight", "Normal", "Overweight")
        val fatAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            fatOption
        )

        fatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.parameterFatUser.adapter = fatAdapter

    }
}