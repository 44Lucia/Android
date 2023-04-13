package com.example.rolemanager.LoginRegister.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rolemanager.databinding.ActivitySignIn2Binding
import com.google.firebase.auth.FirebaseAuth

class SignIn2Activity : AppCompatActivity() {

    lateinit var binding: ActivitySignIn2Binding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val username: String
        val email: String
        val password: String
        val age: String
        val bio: String
        val profilePic: String?

        binding = ActivitySignIn2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.returnButton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        firebaseAuth = FirebaseAuth.getInstance()
    }
}