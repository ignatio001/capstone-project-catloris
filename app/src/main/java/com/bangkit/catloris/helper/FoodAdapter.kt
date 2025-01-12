package com.bangkit.catloris.helper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.catloris.databinding.ListItemFoodBinding
import com.bangkit.catloris.utils.loadImage

class FoodAdapter(private val foodList: List<Food>) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    class FoodViewHolder(private val binding: ListItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food)
        {
            binding.nameFood.text = food.name
            binding.caloriesFood.text = "${food.calories} kcal"
            binding.imageFood.loadImage(food.imageRes.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodAdapter.FoodViewHolder {
        val binding = ListItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodAdapter.FoodViewHolder, position: Int) {
        val food = foodList[position]
        holder.bind(food)
    }

    override fun getItemCount(): Int = foodList.size
}