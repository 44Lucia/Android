package com.example.rolemanager.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.rolemanager.databinding.FragmentProfileBinding
import com.example.rolemanager.loginRegister.Login.LoginActivity
import com.example.rolemanager.loginRegister.Register.SignInActivity
import com.example.rolemanager.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private const val TAG = "ProfileFragment"
class ProfileFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private var signedInUser: User? = null
    private lateinit var firestoreDb: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        firestoreDb = FirebaseFirestore.getInstance()

        (FirebaseAuth.getInstance().currentUser?.uid as? String)?.let {
            firestoreDb.collection("users").document(it)
                .get().addOnSuccessListener { userSnapshot ->
                    signedInUser = userSnapshot.toObject(User::class.java)
                    Log.i(TAG, "signed in user: $signedInUser")
                }.addOnFailureListener{ exception ->
                    Log.i(TAG, "Failure getching signed in user", exception)
                }
        }

        val activityForResultLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { RESULT ->
                binding.imageToChange.setImageBitmap(RESULT)
            }

        //binding.imageToChange.setImageBitmap(signedInUser?.profilePicturePath)
        binding.editTextName.text = signedInUser?.username.toString()
        binding.editTextBio.text = signedInUser?.bio.toString()

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted)
                    activityForResultLauncher.launch(null)
                else
                    Toast.makeText(this.context,"User has denied the permission", Toast.LENGTH_LONG).show()
            }

        binding.SignOut.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent (getActivity(), LoginActivity::class.java)
            getActivity()?.startActivity(intent)
        }

        binding.addPhoto.setOnClickListener {
            context?.let {
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

        return binding.root
    }
}