package com.bangkit.catloris.ui.sidefeatures

import android.content.Intent
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
            Workout("Push Up", R.raw.push_up_illustration),
            Workout("Sit Up", R.raw.sit_up_illustration),
            Workout("Plank", R.raw.plank_illustration),
            Workout("Squat", R.raw.squat_illustration),
            Workout("Crunch", R.raw.crunch_illustration),
            Workout("Bicycle Crunch", R.raw.bicycle_crunch_illustration),
            Workout("Flutter Kick", R.raw.flutter_kick_illustration),
            Workout("Leg Raise", R.raw.leg_raise_illustration),
            Workout("Lunges", R.raw.lunges_illustration),
            Workout("Reverse Crunch", R.raw.reverse_crunch_illustration)

        )

        adapter = WorkoutAdapter(workoutList) { workout ->
            val intent = Intent(this, WorkoutDetailActivity::class.java).apply {
                putExtra("WORKOUT_TITLE", workout.title)
                putExtra("WORKOUT_ILLUSTRATION", workout.imageResource)
            }
            startActivity(intent)

        }
        binding.workoutRecycler.layoutManager = LinearLayoutManager(this)
        binding.workoutRecycler.adapter = adapter

    }
}