package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityBmiBinding
import kotlin.math.roundToInt

class BmiActivity : AppCompatActivity() {

    companion object {
        const val NAVBAR_TITLE: String = "Back to Main"
        const val IMPERIAL_HEIGHT_HINT: String = "HEIGHT (inch)"
        const val IMPERIAL_WEIGHT_HINT: String = "WEIGHT (lbs)"
        const val METRIC_HEIGHT_HINT: String = "HEIGHT (cm)"
        const val METRIC_WEIGHT_HINT: String = "WEIGHT (kgs)"
    }

    private var binding: ActivityBmiBinding? = null
    private var isImperial: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmi)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = NAVBAR_TITLE
        }
        binding?.toolbarBmi?.setNavigationOnClickListener {
            onBackPressed()
        }

        setUnitFields()
        calculateBmi()
        clearFields()
    }

    private fun setUnitFields() {
        binding?.tilHeight?.hint = IMPERIAL_HEIGHT_HINT
        binding?.tilWeight?.hint = IMPERIAL_WEIGHT_HINT
        binding?.rgUnits?.setOnCheckedChangeListener { _, option: Int ->
            if (option == R.id.rb_us_units) {
                isImperial = true
                binding?.tilHeight?.hint = IMPERIAL_HEIGHT_HINT
                binding?.tilWeight?.hint = IMPERIAL_WEIGHT_HINT
            } else {
                isImperial = false
                binding?.tilHeight?.hint = METRIC_HEIGHT_HINT
                binding?.tilWeight?.hint = METRIC_WEIGHT_HINT
            }
        }
    }

    private fun calculateBmi() {
        binding?.flCalculateBmi?.setOnClickListener {
            val heightInput: String = binding?.heightInput?.text.toString()
            val weightInput: String = binding?.weightInput?.text.toString()
            if (heightInput.isEmpty() or weightInput.isEmpty())
                Toast.makeText(this, "Please, provide weight and height", Toast.LENGTH_SHORT).show()
            else {
                val bmiResult: Double =
                    if (isImperial)
                        703 *
                                (weightInput.toDouble()
                                        / (heightInput.toDouble()
                                        * heightInput.toDouble()))
                    else 10000 * (weightInput.toDouble()
                            / (heightInput.toDouble() * heightInput.toDouble()))
                binding?.tvBmiResult?.text = "Result: ${bmiResult.roundToInt()}"
                binding?.tvBmiResult?.visibility = View.VISIBLE
                binding?.flClearFields?.visibility = View.VISIBLE
            }
        }
    }

    private fun clearFields() {
        binding?.flClearFields?.setOnClickListener {
            binding?.heightInput?.text?.clear()
            binding?.weightInput?.text?.clear()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.binding = null
    }
}