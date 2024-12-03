package com.bangkit.catloris.ui.sidefeatures

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.catloris.R
import com.bangkit.catloris.databinding.ActivityWorkoutBinding
import com.bangkit.catloris.helper.Workout
import com.bangkit.catloris.helper.WorkoutAdapter

class WorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutBinding
    private lateinit var adapter: WorkoutAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val workoutList = listOf(
            Workout("Push Up", "push_up_illustration"),
            Workout("Sit Up", "sit_up_illustration")
        )

        adapter = WorkoutAdapter(this, workoutList)
        binding.workoutRecycler.layoutManager = LinearLayoutManager(this)
        binding.workoutRecycler.adapter = adapter

    }
}