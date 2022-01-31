package com.yudhistira.uaskelompok5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.yudhistira.uaskelompok5.PrefManager

class LoginActivity : AppCompatActivity() {
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        val btnLog: Button = findViewById(R.id.masuk)
        val btnReg: Button = findViewById(R.id.cancel)
        val tiName: TextInputLayout = findViewById(R.id.tiName)
        val tiPass: TextInputLayout = findViewById(R.id.tiPassword)
        var name = tiName.editText?.text.toString()
        var pass = tiPass.editText?.text.toString()
        var age = 0

        init()
        checkLogin()

        val db = DBHelper(this, null)

        btnLog.setOnClickListener {
            if (tiName.editText?.text?.isNotEmpty() == false && tiPass.editText?.text?.isNotEmpty() == false)
                Toast.makeText(this, "Silahkan masukkan nama pengguna atau kata sandi anda!", Toast.LENGTH_SHORT).show()
            else{
                var logindt:Boolean = db.LoginDatas(tiName.editText?.text.toString(),tiPass.editText?.text.toString());
                if (logindt==true){
                    prefManager.setLoggin(true)
                    prefManager.setUsername(tiName.editText?.text.toString())
                    Toast.makeText(this, "Login Berhasi!", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this, "Mohon maaf nama pengguna atau kata sandi anda salah!", Toast.LENGTH_SHORT).show()
                }
            }

        }
        btnReg.setOnClickListener {
            if(tiName.editText?.text?.isNotEmpty() == true  && tiPass.editText?.text?.isNotEmpty() == true){
                if(db.addData(
                        tiName.editText?.text?.toString(),
                        "0",
                        tiPass.editText?.text?.toString()
                    )){
                    Toast.makeText(this, "Pengguna berhasil didaftarkan, silahkan klik masuk!", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this, "Maaf, nama pengguna yang anda masukkan sudah ada!", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Silahkan masukkan nama pengguna atau kata sandi anda!", Toast.LENGTH_SHORT).show()
            }
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