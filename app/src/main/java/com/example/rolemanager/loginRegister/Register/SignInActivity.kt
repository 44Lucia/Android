package com.example.rolemanager.loginRegister.Register

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import com.example.rolemanager.loginRegister.Login.LoginActivity
import com.example.rolemanager.MainActivity
import com.example.rolemanager.databinding.ActivitySignInBinding
import com.example.rolemanager.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


private const val TAG = "LoginActivity"
class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInBinding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var users: User
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.returnButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView9.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val activityForResultLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { RESULT ->
                binding.newPic.setImageBitmap(RESULT)
            }

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted)
                    activityForResultLauncher.launch(null)
                else
                    Toast.makeText(this,"User has denied the permission", Toast.LENGTH_LONG).show()
            }

        binding.button6.setOnClickListener{
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

        binding.returnButton4.setOnClickListener{
            binding.First.visibility = View.VISIBLE
            binding.Second.visibility = View.INVISIBLE
        }

        binding.signUp.setOnClickListener{
            binding.First.visibility = View.INVISIBLE
            binding.Second.visibility = View.VISIBLE
        }

        binding.signUp4.setOnClickListener{
            users = User(
                binding.usernameInput.text.toString(),
                binding.passwordLayout.text.toString(),
                binding.confirmPassEt.text.toString(),
                binding.emailLayout.text.toString(),
                binding.DescriptionText.text.toString(),
                binding.date.dayOfMonth.toString() + "/" + binding.date.month.toString() + "/" + binding.date.year.toString(),
                binding.newPic.drawToBitmap().toString()
            )

            if (users.email.isNotEmpty() && users.password.isNotEmpty() && users.confirmPass.isNotEmpty()){
                if (users.password == users.confirmPass){
                    firebaseAuth.createUserWithEmailAndPassword(users.email, users.password).addOnCompleteListener{
                        if(it.isSuccessful) {
                            val db = FirebaseFirestore.getInstance()
                            val contacts = db.collection("users")
                                .document(firebaseAuth.currentUser?.uid.toString())
                                .set(
                                    hashMapOf(
                                        "username" to users.username,
                                        "email" to users.email,
                                        "biography" to users.bio,
                                        "age" to users.age,
                                        "profilePicture" to users.profilePicturePath,
                                        "id" to firebaseAuth.currentUser?.uid.toString()
                                    )
                                )

                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        }else{
                            Log.i(TAG, it.exception.toString())
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Empty Fields Are not Allowed!!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}