package com.example.unitconvertpractice

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
//        binding.resultText.text = when(binding.startunitSpinner){
//            "mm" -> 1
//            "cm" -> 2
//            "m" -> 3
//            else -> 4
    }
}