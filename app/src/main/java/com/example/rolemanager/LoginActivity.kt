package com.example.rolemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rolemanager.databinding.ActivityLoginBinding
import com.example.rolemanager.fragments.BottomBarActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener{
            val username = binding.userInput.text.toString()
            val password = binding.passwordInput.text.toString()

            firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signInWithEmailAndPassword(username, password).addOnSuccessListener{
                val intent = Intent(this, BottomBarActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener{
                Toast.makeText(this, "Incorrect user or password.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.returnButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.register.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}