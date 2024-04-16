package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

            val friend = Friend(
                binding.edName.text.toString(),
                binding.edTel.text.toString()
            ) // 這邊將從螢幕上獲得的使用者輸入字串先轉成 Friend class
            dbHelper?.addFriend(friend)

        }

        binding.btList.setOnClickListener {
//            dbHelper?.printAllFriends()  將之前的方法註解掉，接下來換成 用 friend類別處理的方法
            val friends = dbHelper?.getAllFriend() // friends 是 從資料庫抓出來的 friend 陣列

//            apply 是一個 scope function，這樣的function可以讓一串程式碼在物件本身運行
//            像是這邊 apply{ *程式碼* } ，裡面的程式碼僅僅用來處理 物件本身的內容物(context)
            friends?.apply {
                // 記得上面friends後面要有 ? ，這是用來防止 Null pointer exception的發生，
                // 有 ? 的話，代表 Nullable， 可以為空值的意思
                for (f in friends) {
//                    baseContext是什麼?
                    Toast.makeText(baseContext, "朋友: ${f.name}, 電話: ${f.tel}",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}