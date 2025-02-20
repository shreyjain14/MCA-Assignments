package me.shreyjain.classwork

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val switchMode = findViewById<Switch>(R.id.switchMode)
        val toggleButton = findViewById<ToggleButton>(R.id.toggleButton)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        val tvSeekValue = findViewById<TextView>(R.id.tvSeekValue)
        val chipGroup = findViewById<ChipGroup>(R.id.chipGroup)
        val btnShowSelectedChips = findViewById<Button>(R.id.btnShowSelectedChips)
        val tvSelectedChips = findViewById<TextView>(R.id.tvSelectedChips)

        // FAB Click Action
        fab.setOnClickListener {
            Toast.makeText(this, "FAB Clicked!", Toast.LENGTH_SHORT).show()
        }

        // Switch
        switchMode.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Dark Mode: $isChecked", Toast.LENGTH_SHORT).show()
        }

        // Toggle Button
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Toggle State: $isChecked", Toast.LENGTH_SHORT).show()
        }

        // SeekBar
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvSeekValue.text = "Seek Value: $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Show selected Chips
        btnShowSelectedChips.setOnClickListener {
            val selectedChips = mutableListOf<String>()
            for (i in 0 until chipGroup.childCount) {
                val chip = chipGroup.getChildAt(i) as Chip
                if (chip.isChecked) {
                    selectedChips.add(chip.text.toString())
                }
            }
            tvSelectedChips.text = "Selected Skills: ${selectedChips.joinToString(", ")}"
        }
    }
}
