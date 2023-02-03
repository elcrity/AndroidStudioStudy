package com.example.unitconvertpractice

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputBinding
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.unitconvertpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.convertButton.setOnClickListener {
            convertUnit()
        }


// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.unit_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.startunitSpinner.adapter = adapter
            binding.resultunitSpinner.adapter = adapter
        }
    }

    private fun convertUnit() {
        Log.v("MainActivity", "convertUnit start")
        var num = binding.startText.text.toString().toDouble()
        Log.v("MainActivity", "make num")
        num = when(binding.startunitSpinner.selectedItem.toString()) {
            "mm" -> {
                Log.v("MainActivity", "mmS")
                num
            }
            "cm" -> {
                Log.v("MainActivity", "cmS")
                num * 10.0
            }
            "m" -> {
                Log.v("MainActivity", "mS")
                num * 1000.0
            }
            else -> {
                Log.v("MainActivity", "kmS")
                num * 100000.0
            }
        }

        num = when(binding.resultunitSpinner.selectedItem.toString()){
            "mm" -> {
                Log.v("MainActivity", "mmR")
                num
            }
            "cm" -> {
                Log.v("MainActivity", "cmR")
                num / 10.0
            }
            "m" -> {
                Log.v("MainActivity", "mR")
                num / 1000.0
            }
            else -> {
                Log.v("MainActivity", "kmR")
                num / 100000.0
            }
        }

        binding.resultText.text = num.toString()
    }
}