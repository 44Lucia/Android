package com.example.rolemanager.fragments

import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.rolemanager.R
import com.example.rolemanager.databinding.FragmentMessagesBinding
import com.example.rolemanager.messenger.contacts.ContactsRecyclerViewAdapter
import com.example.rolemanager.messenger.contacts.ContactsViewModel
import com.example.rolemanager.messenger.contacts.model.Contact
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

class MessagesFragment: Fragment() {

    private lateinit var binding: FragmentMessagesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMessagesBinding.inflate(inflater)

        return binding.root
    }
}