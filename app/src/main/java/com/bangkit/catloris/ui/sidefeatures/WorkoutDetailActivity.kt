package com.bangkit.catloris.ui.sidefeatures

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.catloris.R
import com.bangkit.catloris.databinding.ActivityWorkoutDetailBinding
import com.bumptech.glide.Glide

class WorkoutDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutDetailBinding
    private var timer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 60000
    private val initialTimeInMillis: Long = 60000
    private var isPaused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val workoutTitle = intent.getStringExtra("WORKOUT_TITLE")

        binding.workTitle.text = workoutTitle
        binding.workIllustration.setImageResource(R.drawable.workout_cat_illustration)


        updateTimerText()

        binding.workStart.setOnClickListener {
            startTimer()
        }

        binding.workPause.setOnClickListener {
            pauseTimer()
        }

        binding.workContinue.setOnClickListener {
            continueTimer()
        }

        binding.workReset.setOnClickListener{
            resetTimer()
        }

        binding.workBackButton.setOnClickListener {
            onBackPressed()
        }


    }

    private fun resetTimer() {
        timer?.cancel()
        timeLeftInMillis = initialTimeInMillis
        isPaused = false
        updateTimerText()
    }

    private fun continueTimer() {
        if (isPaused) {
            isPaused = false
            startTimer()
        }
    }

    private fun pauseTimer() {
        if (!isPaused) {
            timer?.cancel()
            isPaused = true
        }
    }

    private fun startTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimerText()
            }

            override fun onFinish() {
                timeLeftInMillis = 0
                updateTimerText()
            }
        }.start()
    }

    private fun updateTimerText() {
        val seconds = (timeLeftInMillis / 1000).toInt()
        binding.workCountdown.text = seconds.toString()
    }
}