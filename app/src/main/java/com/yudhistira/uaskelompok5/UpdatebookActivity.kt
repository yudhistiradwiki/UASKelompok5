package com.yudhistira.uaskelompok5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class UpdatebookActivity : AppCompatActivity() {
    companion object{
        const val Nama = "nama"
        const val Penulis = "penulis"
        const val Penerbit = "penerbit"
        const val Tahun = "tahun"
        const val Sinopsis = "sinopsis"
        const val Photo = "photo"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updatebook)
        val btnUpd: Button = findViewById(R.id.btnEdit)
        val tiName: TextInputLayout = findViewById(R.id.tiName)
        val tiPenulis: TextInputLayout = findViewById(R.id.tiPenulis)
        val tiPenerbit: TextInputLayout = findViewById(R.id.tiPenerbit)
        val tiTahun: TextInputLayout = findViewById(R.id.tiTahun)
        val tiSinopsis: TextInputLayout = findViewById(R.id.tiSinopsis)
        var name = tiName.editText?.text.toString()
        var age = tiPenulis.editText?.text.toString()
        var pass = tiPenerbit.editText?.text.toString()

        val db = DBHelper(this, null)

        val nama = intent.getStringExtra(UpdatebookActivity.Nama);
        tiName.editText?.setText(nama)
        val penulis = intent.getStringExtra(UpdatebookActivity.Penulis);
        tiPenulis.editText?.setText(penulis)
        val penerbit = intent.getStringExtra(UpdatebookActivity.Penerbit);
        tiPenerbit.editText?.setText(penerbit)
        val tahun = intent.getStringExtra(UpdatebookActivity.Tahun);
        tiTahun.editText?.setText(tahun)
        val sinopsis = intent.getStringExtra(UpdatebookActivity.Sinopsis);
        tiSinopsis.editText?.setText(sinopsis)


        btnUpd.setOnClickListener {
            if (tiName.editText?.text?.isNotEmpty() == true && tiPenulis.editText?.text?.isNotEmpty() == true && tiPenerbit.editText?.text?.isNotEmpty() == true && tiTahun.editText?.text?.isNotEmpty() == true
                && tiSinopsis.editText?.text?.isNotEmpty() == true) {
                if (db.UpdateDatasBook(tiName.editText?.text?.toString(),
                        tiPenulis.editText?.text?.toString(),
                        tiPenerbit.editText?.text?.toString(),
                        tiTahun.editText?.text?.toString(),
                        tiSinopsis.editText?.text?.toString(),
                        "cover")) {
                    Toast.makeText(this, "Data berhasil diubah ", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this@UpdatebookActivity, BooksActivity::class.java)
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