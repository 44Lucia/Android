package com.example.rolemanager.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.rolemanager.databinding.FragmentAddBinding
import com.example.rolemanager.model.Post
import com.example.rolemanager.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


private const val TAG = "AddFragment"
private const val PICK_PHOTO_CODE = 1234
class AddFragment: Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var storageReference: StorageReference
    private lateinit var firestoreDb: FirebaseFirestore
    private var photoUri: Uri? = null
    private var signedInUser: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater)
        firestoreDb = FirebaseFirestore.getInstance()

        firestoreDb.collection("users").document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get().addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                Log.i(TAG, "signed in user: $signedInUser")
            }.addOnFailureListener{ exception ->
                Log.i(TAG, "Failure getching signed in user", exception)
            }

        val mDefault = requireActivity().getPackageManager();

        binding.btnPickImage.setOnClickListener {
            Log.i(TAG, "Open up image picker on device")
            val imagePickerIntent = Intent(Intent.ACTION_GET_CONTENT)
            imagePickerIntent.type = "image/*"
            if (imagePickerIntent.resolveActivity(mDefault) != null){
                requireActivity().startActivityForResult(imagePickerIntent, PICK_PHOTO_CODE)
            }

            binding.btnSubmit.setOnClickListener {
                handleSubmitButtonClick()
            }
        }

        storageReference = FirebaseStorage.getInstance().reference

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PHOTO_CODE){
            if (resultCode == Activity.RESULT_OK){
                photoUri = data?.data
                Log.i(TAG, "photoUri $photoUri")
                binding.imageView.setImageURI(photoUri)
            }else{
                Toast.makeText(this.context, "Image picker action canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleSubmitButtonClick() {
        if (photoUri == null){
            Toast.makeText(this.context, "No photo selected", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.etDescription.text.isBlank()){
            Toast.makeText(this.context, "Description cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if (signedInUser == null){
            Toast.makeText(this.context, "No signed in user, please wait", Toast.LENGTH_SHORT).show()
            return
        }

        binding.btnSubmit.isEnabled = false
        val photoUploadUri = photoUri as Uri
        val photoReference = storageReference.child("images/${System.currentTimeMillis()}-photo.jpg")

        // Upload photo to Firebase Storage
        photoReference.putFile(photoUploadUri).continueWithTask { photoUploadTask ->
            Log.i(TAG, "uploaded bytes: ${photoUploadTask.result?.bytesTransferred}")
            // Retrieve image url of the uploaded image
            photoReference.downloadUrl
        }.continueWithTask{ downloadUrlTask ->
            // Create a post object with the image URL and add that to the posts collection
            val post = Post(
                binding.etDescription.text.toString(),
                downloadUrlTask.result.toString(),
                System.currentTimeMillis(),
                signedInUser)
            firestoreDb.collection("posts").add(post)
        }.addOnCompleteListener{ postCreationTask ->
            binding.btnSubmit.isEnabled = true
            if (!postCreationTask.isSuccessful){
                Log.e(TAG, "Exception during Firebase operations", postCreationTask.exception)
                Toast.makeText(this.context, "Failed to save post", Toast.LENGTH_SHORT).show()
            }
            binding.etDescription.text.clear()
            binding.imageView.setImageResource(0)
            Toast.makeText(this.context, "Succes", Toast.LENGTH_SHORT).show()
        }
    }
}