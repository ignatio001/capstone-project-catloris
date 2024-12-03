package com.bangkit.catloris.helper

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.catloris.databinding.ListItemWorkoutBinding
import com.bangkit.catloris.ui.sidefeatures.WorkoutDetailActivity
import com.bumptech.glide.Glide

class WorkoutAdapter(
    private val workoutList: List<Workout>,
    private val onItemClick: (Workout) -> Unit
) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    inner class WorkoutViewHolder(val binding: ListItemWorkoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val binding = ListItemWorkoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WorkoutViewHolder(binding)
    }

    override fun getItemCount(): Int = workoutList.size

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workoutList[position]
        with(holder.binding) {
            workoutListTitle.text = workout.title
            Glide.with(root.context)
                .asGif()
                .load(workout.imageResource)
                .into(workoutListImage)

            root.setOnClickListener {
                onItemClick(workout)
            }
        }
    }


}