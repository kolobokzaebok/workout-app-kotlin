package com.example.workoutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.WorkoutLogItemBinding

class WorkoutLogAdapter(private val items: ArrayList<String>) :
    RecyclerView.Adapter<WorkoutLogAdapter.ViewHolder>() {

    class ViewHolder(binding: WorkoutLogItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val workoutItemLinearLayout = binding.llWorkoutLogItem
        val workoutItemTvId = binding.tvWorkoutLogItemId
        val workoutItemTvDate = binding.tvWorkoutLogItemDate

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            WorkoutLogItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val logPos: Int = position + 1
        holder.workoutItemTvId.text = logPos.toString()
        holder.workoutItemTvDate.text = items[position]
        if (logPos % 2 == 0)
            holder.workoutItemLinearLayout.setBackgroundColor(
                ContextCompat.getColor(
                    holder.workoutItemLinearLayout.context,
                    R.color.light_blue
                )
            )
    }

    override fun getItemCount(): Int {
        return items.size
    }
}