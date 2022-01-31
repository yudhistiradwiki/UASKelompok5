package com.yudhistira.uaskelompok5

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class UpdateuserActivity : Activity() {
    companion object{
        const val Nama = "nama"
        const val Age = "age"
        const val Password = "pass"
    }
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updateuser)
        val btnUpd: Button = findViewById(R.id.btnEdit)
        val tiName: TextInputLayout = findViewById(R.id.tiName)
        val tiAge: TextInputLayout = findViewById(R.id.tiAge)
        val tiPass: TextInputLayout = findViewById(R.id.tiPassword)


        val db = DBHelper(this, null)

        val nama = intent.getStringExtra(Nama);
        tiName.editText?.setText(nama)
        val age = intent.getStringExtra(Age);
        tiAge.editText?.setText(age)
        val pass = intent.getStringExtra(Password);
        tiPass.editText?.setText(pass)


        btnUpd.setOnClickListener {
            if (tiName.editText?.text?.isNotEmpty() == true && tiPass.editText?.text?.isNotEmpty() == true && tiAge.editText?.text?.isNotEmpty() == true) {
                if (db.UpdateDatas(tiName.editText?.text.toString(),tiPass.editText?.text.toString(), tiAge.editText?.text.toString())) {
                    Toast.makeText(this, "Data berhasil diubah ", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this@UpdateuserActivity, UserActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Data gagal diubah", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }

        }
    }
}