package com.bangkit.catloris.helper

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.catloris.databinding.ListItemDashboardBinding

class DashboardAdapter(private val items: List<DashboardItem>) : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {
    inner class DashboardViewHolder(val binding: ListItemDashboardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val binding = ListItemDashboardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DashboardViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val item = items[position]
        holder.binding.slideImage.setImageResource(item.imageRes)

        holder.binding.root.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
            it.context.startActivity(intent)
        }
    }
}