package com.example.rolemanager.model

import com.google.firebase.firestore.PropertyName
import com.google.gson.annotations.SerializedName

data class Post(
    var id: Int = 0,
    var emailUser: String = "",
    var description: String = "",
    var location: String = "",
    @get:PropertyName("image_url") @set:PropertyName("image_url") var imageUrl: String = "",
    @get:PropertyName("creation_time_ms") @set:PropertyName("creation_time_ms") var creationTimeMs: Long = 0,
    var user: User? = null
)