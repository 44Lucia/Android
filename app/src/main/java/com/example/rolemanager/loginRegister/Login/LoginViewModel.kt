package com.example.rolemanager.loginRegister.Login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rolemanager.model.LocalUser
import com.example.rolemanager.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel: ViewModel() {

    var isLoggedIn = MutableLiveData<Boolean>()
    var firebaseAuth: FirebaseAuth


    init {
        firebaseAuth = FirebaseAuth.getInstance()


        if (firebaseAuth.currentUser != null) {
            isLoggedIn.postValue(true)
        }
    }

    fun loginWithEmail(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                isLoggedIn.postValue(true)
            }.addOnFailureListener{
                isLoggedIn.postValue(false)
            }
    }

    fun getCurrentUser(): String? { return firebaseAuth.currentUser?.email }

    fun signOut() {
        firebaseAuth.signOut()
        isLoggedIn.postValue(false)
    }
}