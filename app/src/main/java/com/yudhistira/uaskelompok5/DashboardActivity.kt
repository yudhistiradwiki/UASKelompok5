package com.yudhistira.uaskelompok5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView

class DashboardActivity : AppCompatActivity() {
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        supportActionBar?.hide()
        val btnBooks: RelativeLayout = findViewById(R.id.imageView3)
        val btnOut: RelativeLayout = findViewById(R.id.iv4)
        val btnUser: RelativeLayout = findViewById(R.id.iv3)
        val btnRead: RelativeLayout = findViewById(R.id.iv5)
        var username: TextView = findViewById(R.id.textView2)

        prefManager = PrefManager(this)
        username.text = prefManager.getUsername().toString()

        checkLogin()

        btnBooks.setOnClickListener {
            var intent = Intent(this@DashboardActivity, BooksActivity::class.java)
            startActivity(intent)
        }

        btnUser.setOnClickListener {
            var intent = Intent(this@DashboardActivity, UserActivity::class.java)
            startActivity(intent)
        }
        btnRead.setOnClickListener {
            var intent = Intent(this@DashboardActivity, ReadActivity::class.java)
            startActivity(intent)
        }
        btnOut.setOnClickListener {
            prefManager.removeData()
            var intent = Intent(this@DashboardActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun checkLogin(){
        if (prefManager.isLogin() == false){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}