package com.example.workoutapp

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.ActivityExerciseBinding
import com.example.workoutapp.databinding.ExitDialogBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private enum class Timer(
        val len: Long,
        val interval: Long,
        val units: Int,
        val progressFinal: Int,
        val progressInitial: Int
    ) {
        EXERCISE(3000, 1000, 30, 300, 0),
        REST(1000, 1000, 10, 100, 0)
    }

    private val exerciseList: ArrayList<ExerciseModel> = Constants.getDefaultExerciseList()

    private val GET_READY: String = "Get Ready"
    private val UPCOMING_EXERCISE: String = "Upcoming Exercise: "
    private val NAVBAR_TITLE: String = "Back to Main"


    private var binding: ActivityExerciseBinding? = null
    private var countDownTimer: CountDownTimer? = null
    private var tts: TextToSpeech? = null
    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        tts = TextToSpeech(this, this)

        setSupportActionBar(binding?.toolbarExercise)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = NAVBAR_TITLE
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            showExitDialog()
        }

        showTimer(Timer.REST, 0)
        showExerciseRvList()
    }

    private fun showExerciseRvList() {
        binding?.rvExercises?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList)
        binding?.rvExercises?.adapter = exerciseAdapter
    }

    private fun showTimer(timer: Timer, exercisePosition: Int) {
        resetTimer()
        if (timer == Timer.EXERCISE) {
            binding?.tvGetReady?.text = exerciseList[exercisePosition].getName()
            binding?.ivExerciseImage?.visibility = View.VISIBLE
            binding?.tvUpcomingExercise?.visibility = View.INVISIBLE
            speak(exerciseList[exercisePosition].getName())
        } else {
            binding?.tvGetReady?.text = GET_READY
            binding?.ivExerciseImage?.visibility = View.INVISIBLE
            binding?.ivExerciseImage?.setImageResource(exerciseList[exercisePosition].getImage())
            if (exercisePosition < exerciseList.size - 1) {
                binding?.tvUpcomingExercise?.visibility = View.VISIBLE
                binding?.tvUpcomingExercise?.text =
                    UPCOMING_EXERCISE + exerciseList[exercisePosition].getName()
            }
        }
        binding?.pbCounter?.max = timer.units
        binding?.pbCounter?.progress = timer.progressFinal
        var counter: Int = timer.progressInitial
        countDownTimer = object : CountDownTimer(timer.len, timer.interval) {
            override fun onTick(p0: Long) {
                counter++
                binding?.pbCounter?.progress = timer.units - counter
                binding?.tvTimer?.text = (timer.units - counter).toString()
            }

            override fun onFinish() {
                when (timer) {
                    Timer.REST -> {
                        showTimer(Timer.EXERCISE, exercisePosition)
                        exerciseList[exercisePosition].setIsSelected(true)
                    }
                    Timer.EXERCISE -> {
                        exerciseList[exercisePosition].setIsSelected(false)
                        exerciseList[exercisePosition].setIsCompleted(true)
                        if (exercisePosition == exerciseList.size - 1) {
                            finish()
                            startActivity(Intent(this@ExerciseActivity, FinishActivity::class.java))
                        } else
                            showTimer(Timer.REST, exercisePosition + 1)
                    }
                }
                exerciseAdapter!!.notifyDataSetChanged()
            }
        }.start()
    }

    private fun resetTimer() {
        if (countDownTimer != null)
            countDownTimer?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        resetTimer()
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
        binding = null
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                Log.e("TTS", "Specified language is not supported!")
        } else {
            Log.e("TTS", "Initialization failed!")
        }
    }

    private fun speak(textToSpeak: String) {
        if (tts != null)
            tts!!.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun showExitDialog() {
        val exitDialog: Dialog = Dialog(this)
        val exitDialogBinding: ExitDialogBinding = ExitDialogBinding.inflate(layoutInflater)
        exitDialog.setContentView(exitDialogBinding.root)
        exitDialog.setCanceledOnTouchOutside(false)

        exitDialogBinding.btnYes.setOnClickListener {
            this@ExerciseActivity.finish()
            exitDialog.dismiss()
        }
        exitDialogBinding.btnNo.setOnClickListener {
            exitDialog.dismiss()
        }

        exitDialog.show()
    }

    override fun onBackPressed() {
        showExitDialog()
    }
}