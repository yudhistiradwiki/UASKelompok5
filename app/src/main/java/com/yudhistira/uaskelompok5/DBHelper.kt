package com.yudhistira.uaskelompok5


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf


public class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    factory,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "dbData.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "tbUser"
        private const val TABLE_NAME2 = "tbBuku"
        public const val ID_COL = "id"
        public const val NAME_COL = "name"
        public const val AGE_COL = "age"
        public const val PASS_COL = "pass"
        public const val NAMED_COL = "name"
        public const val PENULIS_COL = "penulis"
        public const val PENERBIT_COL = "penerbit"
        public const val TAHUN_COL = "tahun"
        public const val SINOPSIS_COL = "sinopsis"
        public const val PHOTO_COL = "photo"
        public var getAge = "getage"
        public var getPassword = "getpassword"
        public var getPenulis = "getpenulis"
        public var getPenerbit = "getpenerbit"
        public var getTahun = "gettahun"
        public var getSinopsis = "getsinopsis"
        public var getPhoto = "getphoto"

        public var IDRow: Long = 0

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + TABLE_NAME + "(" + NAME_COL + " TEXT PRIMARY KEY," +
                PASS_COL + " TEXT," +
                AGE_COL + " TEXT" + ")")
        val query2 = ("CREATE TABLE " + TABLE_NAME2 + "(" + NAMED_COL + " TEXT PRIMARY KEY," +
                PENULIS_COL + " TEXT," +
                PENERBIT_COL + " TEXT," +
                TAHUN_COL + " TEXT," +
                SINOPSIS_COL + " TEXT," +
                PHOTO_COL + " TEXT" +")")
        db!!.execSQL(query)
        db!!.execSQL(query2)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2)
        onCreate(db)
    }

    fun addData(name: String, pass: String, age: String) {
        val values = ContentValues()
        values.put(NAME_COL, name)
        values.put(PASS_COL, pass)
        values.put(AGE_COL, age)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)

    }

    fun addDatas(name: String?, pass: String?, age: String?): Boolean {
        val values = ContentValues()
        values.put(NAME_COL, name)
        values.put(PASS_COL, pass)
        values.put(AGE_COL, age)
        val db = this.writableDatabase

        var cursor: Cursor =
            db.rawQuery("Select * from " + TABLE_NAME + " where name = '$name'", null)
        if (cursor.moveToFirst()) {
            return false
        } else {
            IDRow = db.insert(TABLE_NAME, null, values)
            return true

        }

    }

    fun addDatasBooks(name: String?, penulis: String?, penerbit: String?, tahun: String?, sinopsis: String?, photo: String?): Boolean {
        val values = ContentValues()
        values.put(NAMED_COL, name)
        values.put(PENULIS_COL, penulis)
        values.put(PENERBIT_COL, penerbit)
        values.put(TAHUN_COL, tahun)
        values.put(SINOPSIS_COL, sinopsis)
        values.put(PHOTO_COL, photo)
        val db = this.writableDatabase
        var cursor: Cursor =
            db.rawQuery("Select * from " + TABLE_NAME2 + " where name = '$name'", null)
        if (cursor.moveToFirst()) {
            return false
        } else {
            IDRow = db.insert(TABLE_NAME2, null, values)
            return true
        }
    }

    fun addData(name: String?, age: String?, pass: String?) : Boolean{
        val values = ContentValues()
        values.put(NAME_COL, name)
        values.put(AGE_COL, age)
        values.put(PASS_COL, pass)
        val db = this.writableDatabase
        var cursor: Cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME+
                " WHERE NAME = '$name'", null)
        if(cursor.moveToFirst()){
            return false
        }
        else{
            db.insert(TABLE_NAME,null, values)
            return true
        }
    }

    fun addDataBook(name: String?, penulis: String?, penerbit: String?, tahun: String?, sinopsis: String?, photo: String?) : Boolean{
        val values = ContentValues()
        values.put(NAME_COL, name)
        values.put(AGE_COL, penulis)
        values.put(PASS_COL, penerbit)
        values.put(PASS_COL, tahun)
        values.put(PASS_COL, sinopsis)
        values.put(PASS_COL, photo)

        val db = this.writableDatabase
        var cursor: Cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME2+
                " WHERE NAME = '$name'", null)
        if(cursor.moveToFirst()){
            return false
        }
        else{
            db.insert(TABLE_NAME2,null, values)
            return true
        }
    }

    @SuppressLint("Range")
    fun getAllData(): ArrayList<DBModel>{
        val db: SQLiteDatabase = this.readableDatabase
        //val arrayList = ArrayList<String>()
        val arrayList = arrayListOf<DBModel>()
        var Nama: String
        val res: Cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
        if (res.moveToFirst()){
            do {

                Nama = res.getString(res.getColumnIndex("name"))
                getAge = res.getString(res.getColumnIndex("age"))
                getPassword = res.getString(res.getColumnIndex("pass"))
                arrayList.add(DBModel(Nama, getAge, getPassword))
            }while(res.moveToNext())

        }
        return arrayList
    }

    @SuppressLint("Range")
    fun getAllDataBook(): ArrayList<DBModelBook>{
        val db: SQLiteDatabase = this.readableDatabase
        //val arrayList = ArrayList<String>()
        val arrayList = arrayListOf<DBModelBook>()
        var Nama: String
        val res: Cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME2, null)
        if (res.moveToFirst()){
            do {

                Nama = res.getString(res.getColumnIndex("name"))
                getPenulis = res.getString(res.getColumnIndex("penulis"))
                getPenerbit = res.getString(res.getColumnIndex("penerbit"))
                getTahun = res.getString(res.getColumnIndex("tahun"))
                getSinopsis = res.getString(res.getColumnIndex("sinopsis"))
                getPhoto = res.getString(res.getColumnIndex("photo"))
                arrayList.add(DBModelBook(Nama, getPenulis, getPenerbit, getTahun, getSinopsis, getPhoto))
            }while(res.moveToNext())

        }
        return arrayList
    }

    fun getDataBooks(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + TABLE_NAME2, null)
    }


    @SuppressLint("Range")
    fun getAllDataUser(): Collection<String> {
        val db: SQLiteDatabase = this.readableDatabase
        val arrayList = ArrayList<String>()
        val res: Cursor = db.rawQuery("Select * from " + TABLE_NAME, null)

        res.moveToFirst()

        while (!res.isAfterLast) {
            arrayList.add(res.getString(res.getColumnIndex("name")))
            //arrayList.add(res.getString(res.getColumnIndex("id")))
            res.moveToNext()
        }
        return arrayList
    }

    fun deleteData(Name: String) {
        val db: SQLiteDatabase = this.writableDatabase
        db.execSQL("Delete from " + TABLE_NAME + " where name = '$Name'")
        db.close()
    }

    fun deleteDataBooks(Name: String) {
        val db: SQLiteDatabase = this.writableDatabase
        db.execSQL("Delete from " + TABLE_NAME2 + " where name = '$Name'")
        db.close()
    }

    fun UpdateDatasBook(name: String?, penulis: String?, penerbit: String?, tahun: String?, sinopsis: String?, photo: String?): Boolean {
        val values = ContentValues()
        values.put(NAME_COL, name)
        values.put(PENULIS_COL, penulis)
        values.put(PENERBIT_COL, penerbit)
        values.put(TAHUN_COL, tahun)
        values.put(SINOPSIS_COL, sinopsis)
        values.put(PHOTO_COL, photo)
        val db = this.writableDatabase

        var cursor: Cursor = db.rawQuery("Update " + TABLE_NAME2 + " Set penulis = '$penulis', " +
                "penerbit = '$penerbit'," + "tahun = '$tahun'," + "sinopsis = '$sinopsis'," + " photo = '$photo' where name = '$name'", null)
        if (cursor.moveToFirst()) {
            return false
        } else {
            IDRow = db.update(TABLE_NAME2, values, "name= '$name'", null).toLong()
            return true
        }

    }
    fun UpdateDatas(name: String?, pass: String?, age: String?): Boolean {
        val values = ContentValues()
        values.put(NAME_COL, name)
        values.put(PASS_COL, pass)
        values.put(AGE_COL, age)
        val db = this.writableDatabase

        var cursor: Cursor = db.rawQuery("Update " + TABLE_NAME + " Set pass = '$pass', age = '$age' where name = '$name'", null)
        if (cursor.moveToFirst()) {
            return false
        } else {
            IDRow = db.update(TABLE_NAME, values, "name= '$name'", null).toLong()
            return true
        }

    }
    fun LoginDatas(name: String?, pass: String?): Boolean {
        val values = ContentValues()
        values.put(NAME_COL, name)
        values.put(PASS_COL, pass)
        val db = this.writableDatabase

        var cursor: Cursor = db.rawQuery(
            "Select * from " + TABLE_NAME + " where name = '$name' and pass = '$pass'",
            null
        )
        if (cursor.count>0) {
            return true;
        } else {
            return false;
        }

    }
    @SuppressLint("Range")
    fun SearchData(Name:String): ArrayList<DBModel>{
        val db: SQLiteDatabase = this.readableDatabase
        val arrayList = arrayListOf<DBModel>()
        val res: Cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE " + NAME_COL + "= '$Name'" , null)
        if (res.moveToFirst()){
            do {
                getAge = res.getString(res.getColumnIndex("age"))
                getPassword = res.getString(res.getColumnIndex("pass"))
            }while(res.moveToNext())

        }
        return arrayList
    }

    @SuppressLint("Range")
    fun SearchDataBook(Name:String): ArrayList<DBModelBook>{
        val db: SQLiteDatabase = this.readableDatabase
        val arrayList = arrayListOf<DBModelBook>()
        val res: Cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME2 +" WHERE " + NAMED_COL + "= '$Name'" , null)
        if (res.moveToFirst()){
            do {
                getPenulis = res.getString(res.getColumnIndex("penulis"))
                getPenerbit = res.getString(res.getColumnIndex("penerbit"))
                getTahun = res.getString(res.getColumnIndex("tahun"))
                getSinopsis = res.getString(res.getColumnIndex("sinopsis"))
                getPhoto = res.getString(res.getColumnIndex("photo"))
            }while(res.moveToNext())

        }
        return arrayList
    }

    @SuppressLint("Range")
    fun SearchDataByName(Name:String): ArrayList<DBModel>{
        val db: SQLiteDatabase = this.readableDatabase
        val arrayList = arrayListOf<DBModel>()
        var Nama: String
        var Usia: String
        var Pass: String
        val res: Cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE " + NAME_COL + " like '%$Name%'" , null)
        if (res.moveToFirst()){
            do {
                Nama = res.getString(res.getColumnIndex("name"))
                Usia = res.getString(res.getColumnIndex("age"))
                Pass = res.getString(res.getColumnIndex("pass"))
                arrayList.add(DBModel(Nama, Usia, Pass))

            }while(res.moveToNext())

        }
        return arrayList
    }

    @SuppressLint("Range")
    fun SearchDataByNameBook(Name:String): ArrayList<DBModelBook>{
        val db: SQLiteDatabase = this.readableDatabase
        val arrayList = arrayListOf<DBModelBook>()
        var Nama: String
        var Penulis: String
        var Penerbit: String
        var Tahun: String
        var Sinopsis: String
        var Photo: String

        val res: Cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME2 +" WHERE " + NAMED_COL + " like '%$Name%'" , null)
        if (res.moveToFirst()){
            do {
                Nama = res.getString(res.getColumnIndex("name"))
                getPenulis = res.getString(res.getColumnIndex("penulis"))
                getPenerbit = res.getString(res.getColumnIndex("penerbit"))
                getTahun = res.getString(res.getColumnIndex("tahun"))
                getSinopsis = res.getString(res.getColumnIndex("sinopsis"))
                getPhoto = res.getString(res.getColumnIndex("photo"))
                arrayList.add(DBModelBook(Nama, getPenulis, getPenerbit, getTahun, getSinopsis, getPhoto))

            }while(res.moveToNext())

        }
        return arrayList
    }

}