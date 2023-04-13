package com.example.rolemanager.model

data class User(var username: String,
                val password: String,
                val email: String,
                val bio:  String,
                val age: String,
                val profilePicturePath: String?){
    constructor(): this( "", "","", "","",null)

}