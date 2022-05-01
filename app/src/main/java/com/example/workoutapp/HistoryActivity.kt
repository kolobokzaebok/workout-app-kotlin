package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    companion object {
        const val NAVBAR_TITLE: String = "Back to Main"
    }

    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistory)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = NAVBAR_TITLE
        }
        binding?.toolbarHistory?.setNavigationOnClickListener {
            onBackPressed()
        }

        val workoutLogDao: WorkoutLogDao = (application as WorkoutApp).db.workoutLogDao()
        getWorkoutLogs(workoutLogDao)
    }

    private fun getWorkoutLogs(workoutLogDao: WorkoutLogDao) {
        lifecycleScope.launch {
            workoutLogDao.fetchAllWorkoutLogs().collect { workoutLogs ->
                if (workoutLogs.isNotEmpty()) {
                    val items = ArrayList<String>()
                    for (log in workoutLogs) {
                        items.add(log.date)
                    }
                    binding?.rvWorkoutLogs?.layoutManager =
                        LinearLayoutManager(
                            this@HistoryActivity,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                    binding?.rvWorkoutLogs?.adapter = WorkoutLogAdapter(items)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.binding = null
    }
}