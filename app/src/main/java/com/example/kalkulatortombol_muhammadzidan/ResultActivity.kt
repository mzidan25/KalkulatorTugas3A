package com.example.kalkulatortombol_muhammadzidan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var textResult: TextView
    private lateinit var textNameNim: TextView
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        textResult = findViewById(R.id.textResult)
        textNameNim = findViewById(R.id.textNameNim)
        backButton = findViewById(R.id.backbutton)

        val result = intent.getDoubleExtra("result", 0.0)
        val nim = intent.getStringExtra("nim")
        val nama = intent.getStringExtra("nama")

        textResult.text = "Hasil: $result"
        textNameNim.text = "$nim - $nama"

        backButton.setOnClickListener {
            finish()
        }
    }
}
