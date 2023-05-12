package com.example.rolemanager.model

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.CollectionReference

object LocalUser {
    var user = User()

    fun updateLocalDbWithRemoteData(
        usernameEmail: String,
        collection: CollectionReference,
        context: Context?
    ){
        collection.document(usernameEmail).get()
            .addOnSuccessListener {
                user = it.toObject(User::class.java) ?: return@addOnSuccessListener
                Toast.makeText(context, "Data loaded correctly", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(context, "Error loading data", Toast.LENGTH_SHORT).show()
            }

    }
}