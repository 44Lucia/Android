package com.example.rolemanager.loginRegister.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rolemanager.loginRegister.Register.SignInActivity
import com.example.rolemanager.databinding.ActivityLoginBinding
import com.example.rolemanager.fragments.BottomBarActivity
import com.example.rolemanager.model.LocalUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    var loginViewModel = LoginViewModel()
    lateinit var firestoreDb: FirebaseFirestore

    companion object {
        val currentUser: String
            get() = FirebaseAuth.getInstance().currentUser?.email ?: "Anonymous"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestoreDb = FirebaseFirestore.getInstance()

        loginViewModel.isLoggedIn.observe(this){
            if (it == true){
                 Toast.makeText(
                     this,
                     "Logged in as" + loginViewModel.getCurrentUser(),
                     Toast.LENGTH_SHORT
                  ).show()
                LocalUser.updateLocalDbWithRemoteData(FirebaseAuth.getInstance().currentUser?.email ?: "Anonymous", firestoreDb.collection("users"), this)
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
            val collection = firestoreDb.collection("users")

            LocalUser.updateLocalDbWithRemoteData(email, collection, this)
            loginViewModel.loginWithEmail(email,pass)
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