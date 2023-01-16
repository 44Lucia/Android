package com.example.rolemanager.realtimedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rolemanager.databinding.ActivityRealTimeDatabaseBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RealTimeDatabaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRealTimeDatabaseBinding
    private lateinit var database: DatabaseReference

    private val chats: ArrayList<Chat> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRealTimeDatabaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database("https://be-my-hands-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("chats")

        val chat = Chat("nouChat")
        chat.chat.add("Hey soc un missatge nou")
        chats.add(chat)

        database.child(chat.id).setValue(chat)

        database.child(chat.id).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val c = snapshot.getValue(Chat::class.java)
                Toast.makeText(this@RealTimeDatabaseActivity, "$c", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) = Unit

        })
    }
}