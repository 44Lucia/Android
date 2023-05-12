package com.example.rolemanager.model

data class User(var username: String,
                var password: String,
                var confirmPass: String,
                var email: String,
                val bio:  String,
                val age: String,
                val profilePicturePath: String? = "",
                var idLastPost: Int = 0){
    constructor(): this( "","", "","", "","",null, 0)
}