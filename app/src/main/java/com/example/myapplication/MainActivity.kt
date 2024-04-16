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
        // 產生一個 object，而透過 getInstance() 方法創建的物件就只會 有一個 instance
        // 之後每次使用物件，都會指到這個物件

        binding.btSave.setOnClickListener {
//            dbHelper?.addFriend(binding.edName.text.toString())
//            上面是原本的程式，傳入 addFriend() 裡面的參數類型是 String，
//            因為我們要透過 Friend data class 傳遞資料，所以需要修改一下
// 這邊將從螢幕上獲得的使用者輸入字串先轉成 Friend class
            val friend = Friend(binding.edName.text.toString())
//            dbHelper 是我們創建的FriendDbHelper的
            dbHelper?.addFriend(friend) // 這邊就是呼叫 dbHelper物件的 addFriend()方法

        }

        binding.btList.setOnClickListener {
//            dbHelper?.printAllFriends()  原本的方法: 使用 printAllFriends
            // 新的方法透過 Friend 類別傳遞資料
            val friendList = dbHelper?.getAllFriend() // 這邊的 friend 儲存的會是 回傳的 Friend 陣列
            friendList?.apply{
                // 這邊透過 apply{} 在大括號內的程式碼只會針對目前的物件內容作用
                // 要記得加? ， 避免friendList 出現 Null pointer exception
                for (f in friendList){
//                    Toast 類別中為其所定義的 LENGTH_LONG 或 LENGTH_SHORT 代表長與短時間的常數
                    // 參數1: Context, 參數2: 想要輸出的字串, 參數3:持續時間，這邊使用Toast定義的常數
                    Toast.makeText(baseContext, "朋友你好: ${f.name}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}