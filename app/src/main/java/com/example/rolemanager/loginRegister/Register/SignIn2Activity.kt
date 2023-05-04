package com.example.rolemanager.loginRegister.Register

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.rolemanager.databinding.ActivitySignIn2Binding
import com.example.rolemanager.loginRegister.Login.LoginActivity
import com.example.rolemanager.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in_2.view.*

class SignIn2Activity : AppCompatActivity() {

    lateinit var binding: ActivitySignIn2Binding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignIn2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.returnButton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        firebaseAuth = FirebaseAuth.getInstance()

        val activityForResultLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { RESULT ->
                binding.imageView4.setImageBitmap(RESULT)
            }

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted)
                    activityForResultLauncher.launch(null)
                else
                    Toast.makeText(this,"User has denied the permission", Toast.LENGTH_LONG).show()
            }

        binding.button2.setOnClickListener{
            this.let {
                if (ContextCompat.checkSelfPermission(it, Manifest.permission.CAMERA )
                    == PackageManager.PERMISSION_GRANTED) {
                    // Ja tinc el permís
                    activityForResultLauncher.launch(null)
                } else {
                    // No tinc el permís i l'he de sol·licitar
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }
        }
    }
}