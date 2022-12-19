package com.example.rolemanager.fragments

import android.media.tv.AdRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.rolemanager.R
import com.example.rolemanager.databinding.ActivityBottomBarBinding
import com.google.firebase.auth.FirebaseAuth

class BottomBarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomBarBinding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityBottomBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val menuFragment = MenuFragment()
        val addFragment = AddFragment()
        val messagesFragment = MessagesFragment()
        val profileFragment = ProfileFragment()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId){
                R.id.menuButton -> setFragment(menuFragment)
                R.id.addButton -> setFragment(addFragment)
                R.id.messagesButton -> setFragment(messagesFragment)
                R.id.profileButton -> setFragment(profileFragment)
            }
            true
        }
        setFragment(menuFragment)
    }

    fun setFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(binding.fragmentContainer.id, fragment)
            commit()
        }
    }


    override fun onPause() {
        //Exit from firebaseAuth
        firebaseAuth.signOut()
        super.onPause()
    }
}