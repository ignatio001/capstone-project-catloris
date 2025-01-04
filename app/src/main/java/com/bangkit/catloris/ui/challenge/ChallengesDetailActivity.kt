package com.bangkit.catloris.ui.challenge

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.catloris.R
import com.bangkit.catloris.databinding.ActivityChallengesDetailBinding
import com.bangkit.catloris.databinding.ActivityWorkoutDetailBinding

class ChallengesDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChallengesDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChallengesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.chOneButton.setOnClickListener {
            val chIntent = Intent(this, ChallengeScan::class.java)
            val oneChValue = 100f
            chIntent.putExtra("CH_ONE_VALUES", oneChValue)
            startActivity(chIntent)
            finish()
        }
    }
}