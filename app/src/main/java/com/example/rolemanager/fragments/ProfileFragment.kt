package com.example.rolemanager.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.rolemanager.databinding.FragmentProfileBinding
import io.grpc.Context

class ProfileFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)

        val activityForResultLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { RESULT ->
                binding.imageToChange.setImageBitmap(RESULT)
            }

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted)
                    activityForResultLauncher.launch(null)
                else
                    Toast.makeText(this.context,"User has denied the permission", Toast.LENGTH_LONG).show()
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