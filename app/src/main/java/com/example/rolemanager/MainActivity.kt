package com.example.rolemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rolemanager.LoginRegister.Login.LoginActivity
import com.example.rolemanager.LoginRegister.Register.SignInActivity
import com.example.rolemanager.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signin.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
        binding.login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}