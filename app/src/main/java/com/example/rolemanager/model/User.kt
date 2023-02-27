package com.example.rolemanager.model

data class User(var name: String,
                val bio:  String,
                val age: String,
                val profilePicturePath: String?){
    constructor(): this( "", "","", null)
}