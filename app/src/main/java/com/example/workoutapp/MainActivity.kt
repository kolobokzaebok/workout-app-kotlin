package com.example.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.workoutapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.flStartButton?.setOnClickListener {
            val intent: Intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        binding?.flBmiButton?.setOnClickListener {
            val intent: Intent = Intent(this, BmiActivity::class.java)
            startActivity(intent)
        }

        binding?.flHistoryButton?.setOnClickListener {
            val intent: Intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.binding = null
    }
}