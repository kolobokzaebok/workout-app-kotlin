package com.example.workoutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ItemExerciseBinding

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root) {
        val exerciseTvItem = binding.tvExerciseItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemExerciseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel = items[position]
        holder.exerciseTvItem.text = model.getId().toString()

        if (model.getIsSelected()) {
            holder.exerciseTvItem.background = ContextCompat.getDrawable(
                holder.itemView.context,
                R.drawable.item_exercise_background_selected
            )
        }
        if (model.getIsCompleted()) {
            holder.exerciseTvItem.background = ContextCompat.getDrawable(
                holder.itemView.context,
                R.drawable.item_exercise_background_completed
            )
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}