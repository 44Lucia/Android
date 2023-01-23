package com.example.rolemanager.LoginRegister.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rolemanager.LoginRegister.Register.SignInActivity
import com.example.rolemanager.databinding.ActivityLoginBinding
import com.example.rolemanager.fragments.BottomBarActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    var loginViewModel = LoginViewModel()

    companion object {
        val currentUser: String
            get() = FirebaseAuth.getInstance().currentUser?.email ?: "Anonymous"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel.isLoggedIn.observe(this){
            if (it == true){
                 Toast.makeText(
                     this,
                     "Logged in as" + loginViewModel.getCurrentUser(),
                     Toast.LENGTH_SHORT
                  ).show()
                goToMainActivity()
            }else{
                Toast.makeText(this,"Incorrect password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.register.setOnClickListener {
            goToRegisterActivity()
        }

        binding.loginButton.setOnClickListener{
            val email = binding.emailLayout.text.toString()
            val pass = binding.passwordLayout.text.toString()

           loginViewModel.loginWithEmail(email, pass)
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this@LoginActivity, BottomBarActivity::class.java)
        startActivity(intent)

        finish()
    }

    private fun goToRegisterActivity(){
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }
}