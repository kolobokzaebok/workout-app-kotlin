package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.workoutapp.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    companion object {
        private const val NAVBAR_TITLE: String = "Back to Main"
        private const val TAG: String = "FinishActivity"
    }

    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarFinish)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = NAVBAR_TITLE
        }
        binding?.toolbarFinish?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btFinish?.setOnClickListener {
            finishAffinity()
        }

        val workoutLogDao: WorkoutLogDao = (application as WorkoutApp).db.workoutLogDao()
        logWorkout(workoutLogDao)
    }

    private fun logWorkout(workoutLogDao: WorkoutLogDao) {
        val simpleDateFormat: SimpleDateFormat =
            SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())
        val workoutFinishTimestamp: String = simpleDateFormat.format(Calendar.getInstance().time)
        Log.d(TAG, "Workout finish time: '$workoutFinishTimestamp'")
        lifecycleScope.launch {
            workoutLogDao.insert(WorkoutLog(workoutFinishTimestamp))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.binding = null
    }
}