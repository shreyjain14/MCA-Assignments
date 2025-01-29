package me.shreyjain.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var numberInput: EditText
    private var lastNumeric: Boolean = false
    private var stateError: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    
        numberInput = findViewById(R.id.numberInput)
    
        // Set up numeric click listeners
        setNumericOnClickListener()
        
        // Set up operator click listeners
        findViewById<Button>(R.id.multiply).setOnClickListener { onOperator(it as Button) }
        findViewById<Button>(R.id.divide).setOnClickListener { onOperator(it as Button) }
        findViewById<Button>(R.id.plus).setOnClickListener { onOperator(it as Button) }
        findViewById<Button>(R.id.subtract).setOnClickListener { onOperator(it as Button) }
        findViewById<Button>(R.id.percentage).setOnClickListener { onOperator(it as Button) }
        findViewById<Button>(R.id.parenthesis).setOnClickListener { onOperator(it as Button) }
        
        // Set up equals click listener
        findViewById<Button>(R.id.equals).setOnClickListener { onEqual() }
        
        // Set up clear click listener
        findViewById<Button>(R.id.clear).setOnClickListener { onClear() }
    }

    private fun setNumericOnClickListener() {
        val numericButtons = listOf(
            R.id.zero, R.id.one, R.id.two, R.id.three, R.id.four,
            R.id.five, R.id.six, R.id.seven, R.id.eight, R.id.nine, R.id.zerozero
        )

        for (id in numericButtons) {
            findViewById<Button>(id).setOnClickListener {
                onDigit(it as Button)
            }
        }
    }

    private fun setOperatorOnClickListener() {
        val operatorButtons = listOf(
            R.id.plus, R.id.subtract, R.id.multiply, R.id.divide, R.id.equals, R.id.dot, R.id.clear, R.id.parenthesis, R.id.percentage
        )

        for (id in operatorButtons) {
            findViewById<Button>(id).setOnClickListener {
                when (it.id) {
                    R.id.clear -> onClear()
                    R.id.equals -> onEqual()
                    else -> onOperator(it as Button)
                }
            }
        }
    }

    private fun onDigit(view: Button) {
        if (stateError) {
            numberInput.setText(view.text)
            stateError = false
        } else {
            numberInput.append(view.text)
        }
        lastNumeric = true
    }

    private fun onOperator(view: Button) {
        if (!stateError && lastNumeric) {
            val operator = when (view.id) {
                R.id.multiply -> "*"
                R.id.divide -> "/"
                R.id.plus -> "+"
                R.id.subtract -> "-"
                R.id.percentage -> "%"
                R.id.parenthesis -> "()"
                else -> view.text
            }
            numberInput.append(operator)
            lastNumeric = false
            lastDot = false    
        }
    }

    private fun onClear() {
        numberInput.setText("")
        lastNumeric = false
        stateError = false
        lastDot = false
    }

    private fun onEqual() {
        if (lastNumeric && !stateError) {
            val txt = numberInput.text.toString()
            try {
                val result = evaluate(txt)
                numberInput.setText(result.toString())
                lastDot = true
            } catch (ex: Exception) {
                numberInput.setText("Error")
                stateError = true
                lastNumeric = false
            }
        }
    }

    private fun evaluate(expression: String): Double {
        return ExpressionBuilder(expression).build().evaluate()
    }
}