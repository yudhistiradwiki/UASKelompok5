package com.yudhistira.uaskelompok5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoadfirstActivity : AppCompatActivity() {

    private lateinit var prefManager: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loadfirst)
        supportActionBar?.hide()

        val btnNext: Button = findViewById(R.id.next)

        init()
        checkLogin()


        btnNext.setOnClickListener {
            var intent = Intent(this@LoadfirstActivity, LoadsecondActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun init(){
        prefManager = PrefManager(this)
    }
    private fun checkLogin(){
        if (prefManager.isLogin()!!){
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}