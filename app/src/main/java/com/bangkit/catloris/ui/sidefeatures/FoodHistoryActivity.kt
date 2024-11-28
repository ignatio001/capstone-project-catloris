package com.bangkit.catloris.ui.sidefeatures

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.catloris.R
import com.bangkit.catloris.databinding.ActivityBodyMassBinding
import com.bangkit.catloris.databinding.ActivityFoodHistoryBinding

class FoodHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }
}