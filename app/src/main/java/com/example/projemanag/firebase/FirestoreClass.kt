package com.example.projemanag.firebase

import com.example.projemanag.activity.SignUpActivity
import com.example.projemanag.models.User
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreClass {
    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActivity, userInfo: User){

    }
}