package me.shreyjain.splashscreen

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivityLog"
    private lateinit var logTextView: TextView

    // Helper method to log lifecycle events and display them on screen.
    private fun logAndDisplay(message: String) {
        Log.d(TAG, message)
        logTextView.append("$message\n")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameRollTextView = findViewById<TextView>(R.id.nameRollTextView)
        nameRollTextView.text = "Shrey Jain 2447249"

        // Reference the TextView for displaying lifecycle logs.
        logTextView = findViewById(R.id.logTextView)
        logAndDisplay("onCreate() called")
    }

    override fun onStart() {
        super.onStart()
        logAndDisplay("onStart() called")
    }

    override fun onResume() {
        super.onResume()
        logAndDisplay("onResume() called")
    }

    override fun onPause() {
        logAndDisplay("onPause() called")
        super.onPause()
    }

    override fun onStop() {
        logAndDisplay("onStop() called")
        super.onStop()
    }

    override fun onDestroy() {
        logAndDisplay("onDestroy() called")
        super.onDestroy()
    }
}
