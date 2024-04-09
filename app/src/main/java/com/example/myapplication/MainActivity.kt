package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        FriendDbHelper.init(this)
        val dbHelper = FriendDbHelper.getInstance()

        binding.btSave.setOnClickListener {
            dbHelper?.addFriend(binding.edName.text.toString())
        }

        binding.btList.setOnClickListener {
            dbHelper?.printAllFriends()
        }
    }
}