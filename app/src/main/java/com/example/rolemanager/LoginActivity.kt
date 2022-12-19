package com.example.rolemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firstapp.messenger.contacts.ContactsActivity
import com.example.rolemanager.databinding.ActivityLoginBinding
import com.example.rolemanager.fragments.BottomBarActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var firebaseAuth: FirebaseAuth

    companion object {
        val currentUser: String
            get() = FirebaseAuth.getInstance().currentUser?.email ?: "Anonymous"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser != null) {
            Toast.makeText(
                this,
                "Logged in as ${firebaseAuth.currentUser?.email}",
                Toast.LENGTH_SHORT
            ).show()
            login()
        }

        binding.register.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        binding.loginButton.setOnClickListener{
            val email = binding.emailLayout.text.toString()
            val pass = binding.passwordLayout.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() ){
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener{
                    if(it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                         Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Empty Fields Are not Allowed!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login() {
        val intent = Intent(this@LoginActivity, ContactsActivity::class.java)
        startActivity(intent)

        finish()
    }
}