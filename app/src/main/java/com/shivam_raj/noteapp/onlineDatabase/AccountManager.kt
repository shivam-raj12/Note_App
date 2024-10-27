package com.shivam_raj.noteapp.onlineDatabase

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

private lateinit var auth: FirebaseAuth
suspend fun createAccount(
    email: String,
    password: String,
    name: String
): String? {
    auth = Firebase.auth
    try {
        val account = auth.createUserWithEmailAndPassword(email, password).await()
        account.user?.updateProfile(userProfileChangeRequest {
            displayName = name
        })?.await()
        writeUserToDatabase(account.user!!)
        return null
    } catch (e: Exception) {
        return e.message
    }
}

suspend fun loginToAccount(
    email: String,
    password: String
): String? {
    auth = Firebase.auth
    try {
        auth.signInWithEmailAndPassword(email, password).await()
        return null
    } catch (e: Exception) {
        return e.message
    }
}

suspend fun writeUserToDatabase(
    user: FirebaseUser
) {
    try {
        Firebase.firestore.collection("Users").document(user.uid).set(
            User(
                user.uid, (user.photoUrl ?: "").toString(), user.displayName ?: "", user.email ?: ""
            )
        ).await()
    } catch (e:Exception){
        Log.e("TAG", "writeUserToDatabase: ${e.message}")
    }

}