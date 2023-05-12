package com.example.rolemanager.loginRegister.Register

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.drawToBitmap
import com.example.rolemanager.loginRegister.Login.LoginActivity
import com.example.rolemanager.MainActivity
import com.example.rolemanager.databinding.ActivitySignInBinding
import com.example.rolemanager.model.LocalUser
import com.example.rolemanager.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


private const val TAG = "LoginActivity"
private const val PICK_PHOTO_CODE = 1234
class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInBinding
    private lateinit var storageReference: StorageReference
    private lateinit var firestoreDb: FirebaseFirestore
    lateinit var firebaseAuth: FirebaseAuth
    private var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestoreDb = FirebaseFirestore.getInstance()

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.returnButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        firebaseAuth = FirebaseAuth.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

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
            Log.i(TAG, "Open up image picker on device")
            val imagePickerIntent = Intent(Intent.ACTION_PICK)
            imagePickerIntent.type = "image/*"
            startActivityForResult(imagePickerIntent, PICK_PHOTO_CODE)

            binding.signUp4.setOnClickListener {
                handleSubmitButtonClick()
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PHOTO_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                photoUri = data?.data
                Log.i(TAG, "photoUri $photoUri")
                binding.newPic.setImageURI(photoUri)
            } else {
                Toast.makeText(this, "Image picker action canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleSubmitButtonClick() {

        if (binding.emailLayout.text.toString().isNotEmpty() && binding.passwordLayout.text.toString().isNotEmpty()
            && binding.confirmPassEt.text.toString().isNotEmpty() && binding.DescriptionText.text.toString().isNotEmpty()
            && binding.date.dayOfMonth.toString().isNotEmpty() && binding.usernameInput.text.toString().isNotEmpty()
            && binding.newPic.toString().isNotEmpty()) {

            if (binding.passwordLayout.text.toString() == binding.confirmPassEt.text.toString()) {

                val photoUploadUri = photoUri as Uri
                val photoReference =
                    storageReference.child("images/${System.currentTimeMillis()}-photo.jpg")

                // Upload photo to Firebase Storage
                photoReference.putFile(photoUploadUri).continueWithTask { photoUploadTask ->
                    Log.i(TAG, "uploaded bytes: ${photoUploadTask.result?.bytesTransferred}")
                    // Retrieve image url of the uploaded image
                    photoReference.downloadUrl
                }.continueWithTask { downloadUrlTask ->
                    // Create a user object with the image URL and add that to the users collection
                    LocalUser.user = User(
                        binding.usernameInput.text.toString(),
                        binding.passwordLayout.text.toString(),
                        binding.confirmPassEt.text.toString(),
                        binding.emailLayout.text.toString(),
                        binding.DescriptionText.text.toString(),
                        binding.date.dayOfMonth.toString() + "/" + binding.date.month.toString() + "/" + binding.date.year.toString(),
                        downloadUrlTask.result.toString()
                    )

                    firestoreDb.collection("users").document(LocalUser.user.email)
                        .set(LocalUser.user)
                }
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}