package com.bangkit.catloris.helper

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.catloris.databinding.ListItemWorkoutBinding
import com.bangkit.catloris.ui.sidefeatures.WorkoutDetailActivity
import com.bumptech.glide.Glide

class WorkoutAdapter(
    private val context: Context,
    private val workoutList: List<Workout>
) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    inner class WorkoutViewHolder(val binding: ListItemWorkoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val binding = ListItemWorkoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return WorkoutViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return workoutList.size
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workoutList[position]
        with(holder.binding) {
            Glide.with(context)
                .asGif()
                .load(workout.image)
                .into(workoutListImage)

            workoutListTitle.text = workout.title

            root.setOnClickListener {
                val intent = Intent(context, WorkoutDetailActivity::class.java).apply {
                    putExtra("WORKOUT", workout)
                }
                context.startActivity(intent)
            }
        }
    }

}