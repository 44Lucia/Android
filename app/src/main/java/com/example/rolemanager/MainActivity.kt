package com.example.rolemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rolemanager.databinding.ActivityMainBinding
import com.example.rolemanager.fragments.BottomBarActivity
import com.example.rolemanager.fragments.MenuFragment
import com.example.rolemanager.particlesList.PepoLista

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