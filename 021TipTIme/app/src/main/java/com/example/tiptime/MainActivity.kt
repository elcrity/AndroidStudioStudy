package com.example.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculateButton.setOnClickListener{ calculateTip() }

    }

    private fun calculateTip() {
        val serviceCost = binding.costOfService.text.toString()
        val cost = serviceCost.toDouble()

        val tipPercentage = when(binding.tipOption.checkedRadioButtonId){
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }
        var tip = cost * tipPercentage

        if(cost == null || cost == 0.0){
            displayTip(0.0)
            return
        }

        if(binding.roundUpSwitch.isChecked) tip = kotlin.math.ceil(tip)

        displayTip(tip)
    }

    private fun displayTip(tip : Double){
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        //NumberFormat.getCurrencyInstance(Locale.US).format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}