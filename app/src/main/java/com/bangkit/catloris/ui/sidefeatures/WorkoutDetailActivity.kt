package com.bangkit.catloris.ui.sidefeatures

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        val workout = intent.getParcelableExtra<Workout>("WORKOUT")

        workout?.let {
            binding.workTitle.text = it.title

            val gifUri = Uri.parse("android.resource://${packageName}/raw/${it.image}")

            Glide.with(this)
                .asGif()
                .load(gifUri)
                .into(binding.workIllustration)
        }

        binding.workBackButton.setOnClickListener{
            finish()
        }

    }
}