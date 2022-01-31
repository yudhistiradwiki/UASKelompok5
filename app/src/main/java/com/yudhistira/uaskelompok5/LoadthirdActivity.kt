package com.yudhistira.uaskelompok5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoadthirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loadthird)
        supportActionBar?.hide()

        val btnNext: Button = findViewById(R.id.next)
        val btnPref: Button = findViewById(R.id.prev)

        btnNext.setOnClickListener {
            var intent = Intent(this@LoadthirdActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        btnPref.setOnClickListener {
            var intent = Intent(this@LoadthirdActivity, LoadsecondActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}