package com.example.rolemanager.fragments

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.content.Context
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.rolemanager.R
import com.example.rolemanager.databinding.ActivityContactsBinding
import com.example.rolemanager.databinding.FragmentMessagesBinding
import com.example.rolemanager.messenger.contacts.ContactsRecyclerViewAdapter
import com.example.rolemanager.messenger.contacts.ContactsViewModel
import com.example.rolemanager.messenger.contacts.model.Contact
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

class MessagesFragment: Fragment() {

    private lateinit var binding: ActivityContactsBinding
    private val contactsViewModel: ContactsViewModel by viewModels()
    private lateinit var adapter: ContactsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityContactsBinding.inflate(layoutInflater)
        adapter = ContactsRecyclerViewAdapter(requireContext() as Activity, contactsViewModel)
        binding.contactsRecyclerView.adapter = adapter

        MobileAds.initialize(requireContext() as Activity)

        /*contactsViewModel.contacts.observe(this) {
            adapter.updateContacts(it)
        }*/

        contactsViewModel.readData(requireContext() as Activity)

        contactsViewModel.getContactsFromDatabase {
            adapter.updateContacts(it)
            println(contactsViewModel.contactList.size)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val request = AdRequest.Builder().build()
        binding.adView.loadAd(request)

        adapter.loadAd()
    }

    override fun onPause() {
        super.onPause()
        contactsViewModel.saveData(requireContext() as Activity)
    }
}