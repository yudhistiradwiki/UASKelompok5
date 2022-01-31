package com.yudhistira.uaskelompok5

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout

class ReadActivity : AppCompatActivity() {
    lateinit var notifManager: NotificationManager
    lateinit var notifChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelID = "123456789"
    private val desc = "Master User Notification"
    private lateinit var rvBooks: RecyclerView

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)


        val listView: RecyclerView = findViewById(R.id.rv_books)
        val db = DBHelper(this, null)

        var arrayList: ArrayList<DBModelBook> = arrayListOf()
        listView.setHasFixedSize(true)
        arrayList.addAll(db.getAllDataBook())
        listView.layoutManager = LinearLayoutManager(this)
        var CardData = DBAdapterbooks(arrayList)
        listView.adapter = CardData

        notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        fun notif() {
            val intent = Intent(this, NotifActivity::class.java)
            val pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notifChannel =
                    NotificationChannel(channelID, desc, NotificationManager.IMPORTANCE_HIGH)
                notifChannel.enableLights(true)
                notifChannel.lightColor = Color.CYAN
                notifChannel.enableVibration(true)
                notifManager.createNotificationChannel(notifChannel)

                builder = Notification.Builder(this, channelID)
                    .setContentTitle("BacaBuku")
                    .setContentText("tes")
                    .setSmallIcon(R.drawable.ic_user)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_user))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            } else {
                builder = Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_user)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_user))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }
            notifManager.notify(123456789, builder.build())
        }

        fun Display() {
            arrayList.clear()
            arrayList.addAll(db.getAllDataBook())

            listView.invalidate()
            listView.refreshDrawableState()
            listView.adapter = CardData
            Toast.makeText(this, "Data displayed", Toast.LENGTH_SHORT).show()
        }

        fun showSelectedData(data: DBModelBook) {
            var params: String = data.Nama
            db.SearchData(params)
            val context = this;
            val moveIntent =
                Intent(this@ReadActivity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.Nama, params)
                    putExtra(DetailActivity.Penulis, DBHelper.getPenulis)
                    putExtra(DetailActivity.Penerbit, DBHelper.getPenerbit)
                    putExtra(DetailActivity.Tahun, DBHelper.getTahun)
                    putExtra(DetailActivity.Sinopsis, DBHelper.getSinopsis)
                    putExtra(DetailActivity.Photo, DBHelper.getPhoto)
                }

            //moveIntent.putExtra(UpdateActivity.Age, tiAge.editText?.text?.toString())
            startActivity(moveIntent)
            finish()
        }

        CardData.setOnItemClickCallback(object : DBAdapterbooks.OnItemClickCallback {
            override fun onItemClicked(data: DBModelBook) {
                showSelectedData(data)
            }
        })
    }}