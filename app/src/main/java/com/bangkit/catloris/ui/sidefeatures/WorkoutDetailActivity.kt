package com.bangkit.catloris.ui.sidefeatures

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.catloris.R
import com.bangkit.catloris.databinding.ActivityWorkoutDetailBinding
import com.bangkit.catloris.helper.Workout
import com.bumptech.glide.Glide

class WorkoutDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val workoutTitle = intent.getStringExtra("WORKOUT_TITLE")
        val workoutImage = intent.getIntExtra("WORKOUT_IMAGE", 0)

        binding.workTitle.text = workoutTitle

        // Check if image resource is valid
        if (workoutImage != 0) {
            Glide.with(this)
                .asGif() // Ensure GIF format is handled
                .load(workoutImage) // Load resource ID
                .into(binding.workIllustration)
        } else {
            // Set a default image or placeholder if no image is passed
            binding.workIllustration.setImageResource(R.drawable.calories_health)
        }

        // Handle back button click
        binding.workBackButton.setOnClickListener {
            onBackPressed()
        }


    }
}