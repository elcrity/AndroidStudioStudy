package com.example.debuging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlin.concurrent.thread

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        logging()
        division()
//        first()
    }

    private fun division(){
        val numerator = 60
        var denominator = 4
        thread(start = true) {
            repeat(4) {
                Thread.sleep(3000)
                runOnUiThread {
                    findViewById<TextView>(R.id.division_textview).setText("${numerator / denominator}")
                    denominator--
                }
            }
        }
    }

    private fun logging(){
        Log.e(TAG, "ERROR: a serious error like an app crash")
        Log.w(TAG, "WARN: warns about the potential for serious errors")
        Log.i(TAG, "INFO: reporting technical information, such as an operation succeeding")
        Log.d(TAG, "DEBUG: reporting technical information useful for debugging")
        Log.v(TAG, "VERBOSE: more verbose than DEBUG logs")
    }

    fun first(){
        second()
        Log.v(TAG, "1")
    }
    fun second(){
        third()
        Log.v(TAG, "2")
    }
    fun third(){
        fourth()
        Log.v(TAG, "3")
    }
    fun fourth(){
        Log.v(TAG, "4")
    }
}