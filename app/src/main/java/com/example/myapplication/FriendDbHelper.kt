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

        private const val TEL = "tel" // 新增加的 電話欄位

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

//        val sqlCreateBookTable = "create table $TABLE ($ID integer primary key, " +
//                "$NAME nvarchar(30));"

        // 這邊是資料庫的操作，因為friend類別增加了新的欄位，所以這邊 建立TABLE的時候也需要加入 電話欄位
        val sqlCreateBookTable = "create table $TABLE ($ID integer primary key, " +
                "$NAME nvarchar(30), $TEL nvarchar(20));"

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
        val cv = ContentValues()
        cv.put(NAME, friend.name)
        cv.put(TEL, friend.tel) // 同樣因為 friend新增了新的欄位，所以需要將TEL也put 進去

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
//    上面的 printAllFriends() 是原本用來顯示朋友名單的 function，下面要實作
//    透過 Friend類別 傳遞資料的 方法 getAllFriends

    fun getAllFriend():ArrayList<Friend> { // getAllFriend() 方法的回傳值 type 要是 Friend 類別的 動態陣列?
        val friends = arrayListOf<Friend>() // 回傳一個空的陣列

//        因為新增了 TEL欄位
        val c = readableDatabase.query(TABLE, arrayOf(NAME, TEL),
            null, null, null, null, null)
        if (c.count != 0) {
            c.moveToFirst()
            do {
//              因為新增了 TEL欄位，需要再多拿TEL
                friends.add(Friend(c.getString(0), c.getString(1))) // 將資料庫讀取到的字串 轉成 Friend類別，再加入到 friends 中
            } while (c.moveToNext())
        }
        return friends // 最後回傳這個 friends 陣列，之後到 MainActivity 調整方法
    }

}