package com.example.rolemanager.messenger.contacts

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.rolemanager.R
import com.example.rolemanager.databinding.ActivityContactsBinding
import com.example.rolemanager.messenger.contacts.model.Contact
import com.google.android.gms.ads.*
import com.google.firebase.auth.FirebaseAuth
import androidx.activity.viewModels
import com.example.rolemanager.messenger.contacts.ContactsRecyclerViewAdapter
import com.example.rolemanager.LoginActivity


class ContactsActivity : AppCompatActivity() {

    lateinit var binding: ActivityContactsBinding
    private lateinit var adapter: ContactsRecyclerViewAdapter

    private val contactsViewModel: ContactsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ContactsRecyclerViewAdapter(this, contactsViewModel)
        binding.contactsRecyclerView.adapter = adapter

        binding.addContactButton.setOnClickListener {
            val newContact = binding.newContact.text.toString()
            binding.newContact.text?.clear()
            contactsViewModel.addContact(Contact(newContact, newContact))
        }

        MobileAds.initialize(this)

        contactsViewModel.contacts.observe(this) {
            adapter.updateContacts(it)
        }
        contactsViewModel.readData(this)
    }

    override fun onResume() {
        super.onResume()

        val request = AdRequest.Builder().build()
        binding.adView.loadAd(request)

        adapter.loadAd()
    }

    override fun onPause() {
        super.onPause()
        contactsViewModel.saveData(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_contacts, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_logout -> logout()
        }
        return true
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut() // Instant operation

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        finish()
    }
}