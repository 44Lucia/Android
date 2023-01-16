package com.example.rolemanager.messenger.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rolemanager.messenger.chat.ChatRecyclerViewAdapter
import com.example.rolemanager.databinding.ActivityChatBinding
import com.example.rolemanager.messenger.chat.model.Chat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: ChatRecyclerViewAdapter

    private lateinit var database: DatabaseReference

    companion object {
        const val EXTRA_USER_ID = "EXTRA_USER_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ChatRecyclerViewAdapter(binding.chatView.layoutManager)
        binding.chatView.adapter = adapter

        val user = intent.extras?.getString(EXTRA_USER_ID) ?: return
        supportActionBar?.title = user

        database = Firebase.database("https://be-my-hands-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("chats")

        binding.messageSendButton.setOnClickListener{
            
        }

    }
}