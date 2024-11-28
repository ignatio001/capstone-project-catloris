package com.bangkit.catloris.ui.challenge

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.catloris.R
import com.bangkit.catloris.databinding.ActivityChallengesDetailBinding
import com.bangkit.catloris.databinding.ActivityWorkoutDetailBinding

class ChallengesDetailActivity : AppCompatActivity() {
    private var binding: ActivityChallengesDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChallengesDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }
}