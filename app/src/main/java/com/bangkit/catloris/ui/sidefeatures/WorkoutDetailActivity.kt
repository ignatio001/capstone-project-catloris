package com.bangkit.catloris.ui.sidefeatures

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.catloris.databinding.ActivityWorkoutDetailBinding
import com.bangkit.catloris.helper.Workout

class WorkoutDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val workout = intent.getParcelableExtra<Workout>("WORKOUT")

        workout?.let {
            binding.workTitle.text = it.title
            binding.workIllustration.setImageResource(it.image)
        }

        binding.workBackButton.setOnClickListener{
            finish()
        }

    }
}