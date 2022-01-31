package com.yudhistira.uaskelompok5

import DBAdapter
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
import android.view.Display
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout

class UserActivity : AppCompatActivity() {
    lateinit var notifManager: NotificationManager
    lateinit var notifChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelID = "123456789"
    private val desc = "Master User Notification"

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val btnAdd:Button = findViewById(R.id.btnAdd)
        val btnPrint:Button = findViewById(R.id.btnPrint)
        val tiName: TextInputLayout = findViewById(R.id.tiName)
        val tiAge: TextInputLayout = findViewById(R.id.tiAge)
        val tiPass: TextInputLayout = findViewById(R.id.tiPassword)
        var name = tiName.editText?.text.toString()
        var age = tiAge.editText?.text.toString()

        val listView: RecyclerView = findViewById(R.id.listView)
        val db = DBHelper(this, null)

        var arrayList:ArrayList<DBModel> = arrayListOf()
        listView.setHasFixedSize(true)
        arrayList.addAll(db.getAllData())
        listView.layoutManager = LinearLayoutManager(this)
        var CardData = DBAdapter(arrayList)
        listView.adapter = CardData

        notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        fun notif(){
            val intent = Intent(this, NotifActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                notifChannel = NotificationChannel(channelID, desc,NotificationManager.IMPORTANCE_HIGH)
                notifChannel.enableLights(true)
                notifChannel.lightColor = Color.CYAN
                notifChannel.enableVibration(true)
                notifManager.createNotificationChannel(notifChannel)

                builder = Notification.Builder(this, channelID)
                    .setContentTitle("BacaBuku")
                    .setContentText("Data pengguna baru berhasil ditambahkan : ${tiName.editText?.text}")
                    .setSmallIcon(R.drawable.ic_book)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_book))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }
            else{
                builder = Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_book)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_book))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }
            notifManager.notify(123456789,builder.build())
        }

        fun Display(){
            arrayList.clear()
            arrayList.addAll(db.getAllData())

            listView.invalidate()
            listView.refreshDrawableState()
            listView.adapter = CardData
            Toast.makeText(this, "Menampilkan data", Toast.LENGTH_SHORT).show()
        }

        fun showSelectedData(data: DBModel) {
            //Toast.makeText(this, "Kamu memilih " + data.Nama, Toast.LENGTH_SHORT).show()
            var params: String = data.Nama
            db.SearchData(params)
            val context = this;
            MaterialAlertDialogBuilder(context).apply{
                setTitle("Data Pengguna: $params")
                setIcon(R.drawable.ic_user)
                setMessage("Apa yang ingin anda lakukan?")
                setPositiveButton("Hapus"){_,_ ->

                    db.deleteData(params)
                    Toast.makeText(this@UserActivity, "Data pengguna berhasil dihapus!", Toast.LENGTH_SHORT).show()
                    Display()
                }
                setNegativeButton("Ubah"){_,_ ->

                    val moveIntent = Intent(this@UserActivity, UpdateuserActivity::class.java).apply {
                        putExtra(UpdateuserActivity.Nama, params)
                        putExtra(UpdateuserActivity.Age, DBHelper.getAge)
                        putExtra(UpdateuserActivity.Password, DBHelper.getPassword)
                    }

                    //moveIntent.putExtra(UpdateActivity.Age, tiAge.editText?.text?.toString())
                    startActivity(moveIntent)
                    finish()
                }
                setNeutralButton("Kembali"){_,_ ->

                }
            }.create().show()


        }

        CardData.setOnItemClickCallback(object : DBAdapter.OnItemClickCallback{
            override fun onItemClicked(data: DBModel) {
                showSelectedData(data)
            }
        })


        tiName.editText!!.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(tiName.editText?.text?.isBlank() == true){
                    Display()
                }
                if(tiName.editText?.text?.isNotBlank() == true)
                {
                    val arrlist:ArrayList<String> = db.SearchDataByName(tiName.editText?.text?.toString().toString()) as ArrayList<String>
                    val arrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(this@UserActivity,
                        android.R.layout.simple_list_item_1, arrlist as List<Any?>)
                    arrayList.clear()
                    arrayList.addAll(db.SearchDataByName(tiName.editText?.text?.toString().toString()))
                    arrayAdapter.notifyDataSetChanged()
                    listView.invalidate()
                    listView.refreshDrawableState()
                    listView.adapter = CardData
                    //Toast.makeText(this@MainActivity, "Data found", Toast.LENGTH_SHORT).show()
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        btnPrint.setOnClickListener {
            Display()
        }
        btnAdd.setOnClickListener {
            if(tiName.editText?.text?.isNotEmpty() == true && tiAge.editText?.text?.isNotEmpty() == true && tiPass.editText?.text?.isNotEmpty() == true){
                if(db.addData(
                        tiName.editText?.text?.toString(),
                        tiAge.editText?.text?.toString(),
                        tiPass.editText?.text?.toString()
                    )){
                    Toast.makeText(this, "Data pengguna berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                    Display()
                    notif()
                }
                else
                {
                    Toast.makeText(this, "Data pengguna gagal ditambahkan", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }

    }

}