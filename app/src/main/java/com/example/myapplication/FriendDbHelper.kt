package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class FriendDbHelper private constructor(val context: Context) :
    SQLiteOpenHelper(context, DB_FILE, null, 1) {

    companion object {
        private const val DB_FILE = "friends.db"
        private const val TABLE = "friends"
        private const val ID = "id"
        private const val NAME = "name"
        private const val TEL = "tel"

        private var friendDbHelper: FriendDbHelper? = null

        fun init(context: Context) {
            if (friendDbHelper == null)
                friendDbHelper = FriendDbHelper(context)
        }

        fun getInstance(): FriendDbHelper? {
            return friendDbHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCreateBookTable = "create table $TABLE ($ID integer primary key, " +
                "$NAME nvarchar(30), $TEL navchar(20));" // 新增TEL欄位

        db?.execSQL(sqlCreateBookTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            db?.execSQL("drop table if exists $TABLE")
            onCreate(db)
        }
    }

//    fun addFriend(name: String) {
//        val cv = ContentValues()
//        cv.put(NAME, name)
//
//        writableDatabase.insert(TABLE, null, cv)
//        writableDatabase.close()
//    } 上面是原本的 funtion，下面是將參數改為用 新建立的 Friend class 傳遞資料

    fun addFriend(friend: Friend) {
        val cv = ContentValues() // cv 是一個 set ，所以下面可以看到這個set包含 NAME, TEL
        cv.put(NAME, friend.name)
        cv.put(TEL, friend.tel) // 新增tel 欄位

        writableDatabase.insert(TABLE, null, cv)
        writableDatabase.close()
    }

    fun printAllFriends() {
        val c = readableDatabase.query(TABLE, arrayOf(NAME),
            null, null, null, null, null)
        if (c.count != 0) {
            c.moveToFirst()
            do {
                Toast.makeText(context, "Friend: ${c.getString(0)}",
                    Toast.LENGTH_SHORT).show()
            } while (c.moveToNext())
        }
    }

//    透過 Friend類別傳送資料
    fun getAllFriend():ArrayList<Friend> { // 這邊透過回傳 Friend 陣列
        val friends =  arrayListOf<Friend>() // 回傳 儲存 Friend類別的 空陣列給 friends

        val c = readableDatabase.query(TABLE, arrayOf(NAME, TEL),
            null, null, null, null, null)
        if (c.count != 0) {
            c.moveToFirst()
            do {
                friends.add(Friend(c.getString(0), c.getString(1))) // 將column 中的 內容提取出來, 並轉換成 Friend類別，儲存到 friends 陣列當中
            } while (c.moveToNext())
        }
    // 最後將這個含有 姓名的 friends return
        return friends
    }
}