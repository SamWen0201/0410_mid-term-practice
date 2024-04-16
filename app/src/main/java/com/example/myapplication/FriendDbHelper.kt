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
                "$NAME nvarchar(30));"

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
}