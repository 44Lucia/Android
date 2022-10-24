package com.example.rolemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rolemanager.databinding.ActivityLoginBinding
import com.example.rolemanager.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    val CORRECT_USERNAME = "Pipo"
    val CORRECT_PASSWORD = "pipo"

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener{
            val username = binding.userInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if (username == CORRECT_USERNAME && password == CORRECT_PASSWORD) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Incorrect user or password.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}