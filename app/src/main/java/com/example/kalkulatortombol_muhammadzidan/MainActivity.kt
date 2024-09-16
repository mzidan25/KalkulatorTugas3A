package com.example.kalkulatortombol_muhammadzidan

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent


class MainActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView
    private var lastNumeric: Boolean = false
    private var stateError: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)

        val btn0: Button = findViewById(R.id.btn0)
        val btn1: Button = findViewById(R.id.btn1)
        val btn2: Button = findViewById(R.id.btn2)
        val btn3: Button = findViewById(R.id.btn3)
        val btn4: Button = findViewById(R.id.btn4)
        val btn5: Button = findViewById(R.id.btn5)
        val btn6: Button = findViewById(R.id.btn6)
        val btn7: Button = findViewById(R.id.btn7)
        val btn8: Button = findViewById(R.id.btn8)
        val btn9: Button = findViewById(R.id.btn9)

        val btnPlus: Button = findViewById(R.id.btnPlus)
        val btnMinus: Button = findViewById(R.id.btnMinus)
        val btnMultiply: Button = findViewById(R.id.btnMultiply)
        val btnDivide: Button = findViewById(R.id.btnDivide)
        val btnDot: Button = findViewById(R.id.btnDot)
        val btnEqual: Button = findViewById(R.id.btnEqual)
        val btnClear: Button = findViewById(R.id.btnClear)

        btn0.setOnClickListener { onDigitPressed("0") }
        btn1.setOnClickListener { onDigitPressed("1") }
        btn2.setOnClickListener { onDigitPressed("2") }
        btn3.setOnClickListener { onDigitPressed("3") }
        btn4.setOnClickListener { onDigitPressed("4") }
        btn5.setOnClickListener { onDigitPressed("5") }
        btn6.setOnClickListener { onDigitPressed("6") }
        btn7.setOnClickListener { onDigitPressed("7") }
        btn8.setOnClickListener { onDigitPressed("8") }
        btn9.setOnClickListener { onDigitPressed("9") }

        btnPlus.setOnClickListener { onOperatorPressed("+") }
        btnMinus.setOnClickListener { onOperatorPressed("-") }
        btnMultiply.setOnClickListener { onOperatorPressed("*") }
        btnDivide.setOnClickListener { onOperatorPressed("/") }
        btnDot.setOnClickListener { onDecimalPressed() }
        btnEqual.setOnClickListener { onEqualPressed() }
        btnClear.setOnClickListener { onClearPressed() }
    }

    private fun onDigitPressed(digit: String) {
        if (tvDisplay.text == "0" && !stateError) {
            tvDisplay.text = digit
        } else if (stateError || tvDisplay.text == "Error") {
            tvDisplay.text = digit
            stateError = false
        } else if (tvDisplay.text.toString().contains("Infinity")) {
            tvDisplay.text = digit
        } else {
            tvDisplay.append(digit)
        }
        lastNumeric = true
    }


    private fun onOperatorPressed(operator: String) {
        if (lastNumeric && !stateError && !tvDisplay.text.toString().contains("Infinity")) {
            tvDisplay.append(operator)
            lastNumeric = false
            lastDot = false
        }
    }


    private fun onDecimalPressed() {
        if (lastNumeric && !stateError && !lastDot) {
            tvDisplay.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    private fun onEqualPressed() {
        if (lastNumeric && !stateError) {
            try {
                val expression = tvDisplay.text.toString()
                val result = evaluateExpression(expression)

                if (result.isInfinite() || result.isNaN()) {
                    tvDisplay.text = "Error"
                    stateError = true
                    lastNumeric = false
                } else {
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra("result", result)
                    intent.putExtra("nama", "Muhammad Zidan")
                    intent.putExtra("nim", "225150407111039")
                    startActivity(intent)
                }
            } catch (e: ArithmeticException) {
                tvDisplay.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }


    private fun onClearPressed() {
        tvDisplay.text = ""
        lastNumeric = false
        stateError = false
        lastDot = false
    }

    private fun evaluateExpression(expression: String): Double {
        return object : Any() {
            fun eval(): Double {
                return expression.split('+').map {
                    it.split('-').map {
                        it.split('*').map {
                            it.split('/').map { it.toDouble() }.reduce { acc, d -> acc / d }
                        }.reduce { acc, d -> acc * d }
                    }.reduce { acc, d -> acc - d }
                }.reduce { acc, d -> acc + d }
            }
        }.eval()
    }
}
