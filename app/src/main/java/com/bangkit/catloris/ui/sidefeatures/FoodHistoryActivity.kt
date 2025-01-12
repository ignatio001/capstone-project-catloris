package com.bangkit.catloris.ui.sidefeatures

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.catloris.R
import com.bangkit.catloris.databinding.ActivityBodyMassBinding
import com.bangkit.catloris.databinding.ActivityFoodHistoryBinding
import com.bangkit.catloris.helper.Food
import com.bangkit.catloris.helper.FoodAdapter

class FoodHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodHistoryBinding

    private val foodRecommendations = mapOf(
        "Underweight" to listOf(
            Food("Ayam Goreng Krispi", R.drawable.food1, 255),
            Food("Nasi Padang", R.drawable.food2, 450),
            Food("Rendang", R.drawable.food3, 330)
        ),
        "Normal" to listOf(
            Food("Nasi Goreng", R.drawable.food4, 300),
            Food("Bakso", R.drawable.food5, 180),
            Food("Sate", R.drawable.food6, 250)
        ),
        "Overweight" to listOf(
            Food("Seblak", R.drawable.food7, 240),
            Food("Tempe Goreng", R.drawable.food8, 182),
            Food("Sop", R.drawable.food9, 140)
        )
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val bodyCondition = intent.getStringExtra("user_con")
        binding.myCon.text = bodyCondition

        val foodList = foodRecommendations[bodyCondition] ?: foodRecommendations["Normal"]!!

        val adapter = FoodAdapter(foodList)
        binding.foodRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.foodRecyclerView.adapter = adapter


    }
}