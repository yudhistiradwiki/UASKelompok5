package com.yudhistira.uaskelompok5

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class BooksActivity : AppCompatActivity() {
    lateinit var notifManager: NotificationManager
    lateinit var notifChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelID = "123456789"
    private val desc = "Master User Notification"
    private val selectedImagePath: String? = null
    var uri: Uri? = null
    var img: ImageView? = null
    var photo: String? = null


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)


        val btnAdd: Button = findViewById(R.id.btnAdd)
        val btnPrint: Button = findViewById(R.id.btnPrint)
        val btnPhoto: Button = findViewById(R.id.btnPhoto)
        val tiName: TextInputLayout = findViewById(R.id.tiName)
        val tiPenulis: TextInputLayout = findViewById(R.id.tiPenulis)
        val tiPenerbit: TextInputLayout = findViewById(R.id.tiPenerbit)
        val tiTahun: TextInputLayout = findViewById(R.id.tiTahun)
        val tiSinopsis: TextInputLayout = findViewById(R.id.tiSinopsis)
        var name = tiName.editText?.text.toString()
        var age = tiPenulis.editText?.text.toString()
        var pass = tiPenerbit.editText?.text.toString()

        val listView: RecyclerView = findViewById(R.id.listView2)
        val db = DBHelper(this, null)

        var arrayList: ArrayList<DBModelBook> = arrayListOf()
        listView.setHasFixedSize(true)
        arrayList.addAll(db.getAllDataBook())
        listView.layoutManager = LinearLayoutManager(this)
        var CardData = DBAdapterbook(arrayList)
        listView.adapter = CardData

        notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        btnPhoto.setOnClickListener(View.OnClickListener { CropImage.startPickImageActivity(this@BooksActivity) })


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
                    .setContentText("Data buku baru berhasil ditambahkan : ${tiName.editText?.text}")
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
            arrayList.addAll(db.getAllDataBook())

            listView.invalidate()
            listView.refreshDrawableState()
            listView.adapter = CardData
            Toast.makeText(this, "Menampilkan data buku", Toast.LENGTH_SHORT).show()
        }

        fun showSelectedData(data: DBModelBook) {
            var params: String = data.Nama
            db.SearchData(params)
            val context = this;
            MaterialAlertDialogBuilder(context).apply{
                setTitle("Judul buku : $params, dan penulis : ${DBHelper.getPenulis}")
                setIcon(R.drawable.ic_user)
                setMessage("Apa yang ingin anda lakukan?")
                setPositiveButton("Hapus"){_,_ ->

                    db.deleteDataBooks(params)
                    Toast.makeText(this@BooksActivity, "Data buku $params berhasil di hapus", Toast.LENGTH_SHORT).show()
                    Display()
                }
                setNegativeButton("Ubah"){_,_ ->

                    val moveIntent = Intent(this@BooksActivity, UpdatebookActivity::class.java).apply {
                        putExtra(UpdatebookActivity.Nama, params)
                        putExtra(UpdatebookActivity.Penulis, DBHelper.getPenulis)
                        putExtra(UpdatebookActivity.Penerbit, DBHelper.getPenerbit)
                        putExtra(UpdatebookActivity.Tahun, DBHelper.getTahun)
                        putExtra(UpdatebookActivity.Sinopsis, DBHelper.getSinopsis)
                        putExtra(UpdatebookActivity.Photo, DBHelper.getPhoto)
                    }
                    startActivity(moveIntent)
                    finish()
                }
                setNeutralButton("Kembali"){_,_ ->

                }
            }.create().show()
        }

        CardData.setOnItemClickCallback(object : DBAdapterbook.OnItemClickCallback{
            override fun onItemClicked(data: DBModelBook) {
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
                    val arrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(this@BooksActivity,
                        android.R.layout.simple_list_item_1, arrlist as List<Any?>)
                    arrayList.clear()
                    arrayList.addAll(db.SearchDataByNameBook(tiName.editText?.text?.toString().toString()))
                    arrayAdapter.notifyDataSetChanged()
                    listView.invalidate()
                    listView.refreshDrawableState()
                    listView.adapter = CardData
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        btnPrint.setOnClickListener {
            Display()
        }
        btnAdd.setOnClickListener {
            if(tiName.editText?.text?.isNotEmpty() == true && tiPenulis.editText?.text?.isNotEmpty() == true && tiPenerbit.editText?.text?.isNotEmpty() == true && tiTahun.editText?.text?.isNotEmpty() == true
                && tiSinopsis.editText?.text?.isNotEmpty() == true){
                if(db.addDatasBooks(
                        tiName.editText?.text?.toString(),
                        tiPenulis.editText?.text?.toString(),
                        tiPenerbit.editText?.text?.toString(),
                        tiTahun.editText?.text?.toString(),
                        tiSinopsis.editText?.text?.toString(),
                        "cover"
                    )){
                    Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                    Display()
                    notif()
                }
                else
                {
                    Toast.makeText(this, "Data gagal ditambahkan", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Silahkan isikan semua data diatas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE
            && resultCode == RESULT_OK
        ) {
            val imageuri = CropImage.getPickImageResultUri(this, data)
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageuri)) {
                uri = imageuri
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 0)
            } else {
                startCrop(imageuri)
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                img?.setImageURI(result.uri)
                uri = result.uri
            }
        }
    }
    fun startCrop(imageuri: Uri?) {
        CropImage.activity(imageuri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .start(this)
        uri = imageuri
    }

}