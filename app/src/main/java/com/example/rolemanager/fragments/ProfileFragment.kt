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
import com.bumptech.glide.Glide
import com.example.rolemanager.databinding.FragmentProfileBinding
import com.example.rolemanager.loginRegister.Login.LoginActivity
import com.example.rolemanager.loginRegister.Register.SignInActivity
import com.example.rolemanager.model.LocalUser
import com.example.rolemanager.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.item_posts.view.*
import kotlin.math.sign

private const val TAG = "ProfileFragment"
class ProfileFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var firestoreDb: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        firestoreDb = FirebaseFirestore.getInstance()

        val activityForResultLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { RESULT ->
                binding.imageToChange.setImageBitmap(RESULT)
            }

        this.context?.let { Glide.with(it).load(LocalUser.user.profilePicturePath).into(binding.imageToChange) }
        binding.editTextName.text = LocalUser.user.username
        binding.editTextBio.text = LocalUser.user.bio

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