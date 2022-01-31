package com.yudhistira.uaskelompok5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    companion object {
        const val Nama = "nama"
        const val Penulis = "penulis"
        const val Penerbit = "penerbit"
        const val Tahun = "tahun"
        const val Sinopsis = "sinopsis"
        const val Photo = "photo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val nama = intent.getStringExtra(Nama)
        val photo = intent.getStringExtra(Photo)
        val penulis = intent.getStringExtra(Penulis)
        val penerbit = intent.getStringExtra(Penerbit)
        val tahun = intent.getStringExtra(Tahun)
        val sinopsis = intent.getStringExtra(Sinopsis)

        val gambar: ImageView = findViewById(R.id.cover)
        val judul: TextView = findViewById(R.id.tvjudul)
        val writer: TextView = findViewById(R.id.tvwriter)
        val writer2: TextView = findViewById(R.id.tvwriter2)
        val publish: TextView = findViewById(R.id.tvpublish)
        val year: TextView = findViewById(R.id.tvtahun)
        val synopsis: TextView = findViewById(R.id.tvsinopsis)

        judul.setText(nama)
        writer.setText(penulis)
        writer2.setText(penulis)
        publish.setText(penerbit)
        year.setText(tahun)
        synopsis.setText(sinopsis)

    }
}
