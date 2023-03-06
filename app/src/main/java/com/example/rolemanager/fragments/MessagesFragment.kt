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
    private lateinit var adapter: ContactsRecyclerViewAdapter

    private val contactsViewModel: ContactsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessagesBinding.inflate(inflater)
        requireActivity().setContentView(binding.root)

        adapter = ContactsRecyclerViewAdapter(requireContext() as Activity, contactsViewModel)
        binding.contactsRecyclerView.adapter = adapter

        binding.addContactButton2.setOnClickListener{
            val newContact = binding.newContact.text.toString()
            binding.newContact.text?.clear()
            contactsViewModel.addContact(Contact(newContact, newContact))
        }

        MobileAds.initialize(requireContext() as Activity)

        contactsViewModel.contacts.observe(viewLifecycleOwner){
            adapter.updateContacts(it)
        }

        contactsViewModel.readData(requireContext() as Activity)

        contactsViewModel.getContactsFromDatabase {
            adapter.updateContacts(it)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val request = AdRequest.Builder().build()
        binding.adView2.loadAd(request)

        adapter.loadAd()
    }

    override fun onPause() {
        super.onPause()
        contactsViewModel.saveData(requireContext() as Activity)
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        requireActivity().menuInflater.inflate(R.menu.menu_contacts, menu)
        return true
    }*/
}