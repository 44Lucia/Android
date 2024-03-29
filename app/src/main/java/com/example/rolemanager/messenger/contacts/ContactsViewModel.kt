package com.example.rolemanager.messenger.contacts

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rolemanager.messenger.chat.ChatActivity
import com.example.rolemanager.messenger.contacts.model.Contact
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class ContactsViewModel : ViewModel() {

    val contacts = MutableLiveData<ArrayList<Contact>>()
    private val FILENAME = "contacts.dat"
    val contactList : ArrayList<Contact> = ArrayList()

    fun saveData(context: Context) {
        context.openFileOutput(FILENAME, Context.MODE_PRIVATE).use {
            val oos = ObjectOutputStream(it)
            oos.writeObject(contacts.value ?: return)
        }
    }

    fun readData(context: Context) {
        try {
            context.openFileInput(FILENAME).use {
                val iin = ObjectInputStream(it)
                val data = iin.readObject()
                if (data is ArrayList<*>) {
                    val cs = data.filterIsInstance<Contact>() as ArrayList
                    contacts.value = cs
                }
            }
        } catch (e: IOException) {
            // pass
        }

        if (contacts.value == null) {
            contacts.postValue(arrayListOf())
        }
    }

    fun addContact(contact: Contact) {
        contacts.value?.add(contact)
        contacts.postValue(contacts.value)
    }

    fun removeContact(contact: Contact) {
        contacts.value?.remove(contact)
        contacts.postValue(contacts.value)
    }

    fun getContactsFromDatabase(onSucces: (ArrayList<Contact>) -> Unit){
        val db = FirebaseFirestore.getInstance()

        val contacts = db.collection("users").get()
            .addOnSuccessListener { documents ->
                documents.forEach{
                    val contact = Contact(it.get("username").toString(),it.get("email").toString() ,it.get("bio").toString(),it.get("age").toString() ,
                        it.get("id").toString(),null)
                    contactList.add(contact)
                }
                onSucces(contactList)
            }
    }
}