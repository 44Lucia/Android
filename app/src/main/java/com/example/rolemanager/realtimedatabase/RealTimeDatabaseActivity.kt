package com.example.rolemanager.realtimedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.rolemanager.databinding.ActivityRealTimeDatabaseBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RealTimeDatabaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRealTimeDatabaseBinding
    private val chats: ArrayList<Chat> = arrayListOf()

    private val chatViewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRealTimeDatabaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val chat = Chat("nouChat")
        chat.chat.add("Hey soc un missatge nou")
        chats.add(chat)

        chatViewModel.writeChat(chat)
        chatViewModel.subscribeAt(chat)

        chatViewModel.currentChat.observe(this){
            Toast.makeText(this, "${it?.chat}", Toast.LENGTH_SHORT).show()
        }
    }
}