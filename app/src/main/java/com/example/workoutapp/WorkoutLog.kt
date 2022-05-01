package com.example.workoutapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_log")
data class WorkoutLog(
    @PrimaryKey
    val date: String
)
