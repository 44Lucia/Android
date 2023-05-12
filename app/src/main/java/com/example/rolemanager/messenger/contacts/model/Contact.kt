package com.example.rolemanager.messenger.contacts.model

import android.net.Uri
import java.io.Serializable
import java.net.URI

data class Contact(
    val name: String,
    val email: String,
    val bio: String,
    val age: String,
    val userId: String,
    val imageFile: Uri?=null
): Serializable