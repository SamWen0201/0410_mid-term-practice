package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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
}