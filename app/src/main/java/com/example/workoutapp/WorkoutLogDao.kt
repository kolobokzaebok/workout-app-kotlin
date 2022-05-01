package com.example.workoutapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutLogDao {

    @Insert
    suspend fun insert(workoutLog: WorkoutLog)

    @Query("select * from `workout_log`")
    fun fetchAllWorkoutLogs(): Flow<List<WorkoutLog>>

}