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
//            dbHelper?.addFriend(binding.edName.text.toString())
//            上面是原本的程式，傳入 addFriend() 裡面的參數類型是 String，
//            因為我們要透過 Friend data class 傳遞資料，所以需要修改一下

            val friend = Friend(binding.edName.text.toString()) // 這邊將從螢幕上獲得的使用者輸入字串先轉成 Friend class
            dbHelper?.addFriend(friend)

        }

        binding.btList.setOnClickListener {
            dbHelper?.printAllFriends()
        }
    }
}