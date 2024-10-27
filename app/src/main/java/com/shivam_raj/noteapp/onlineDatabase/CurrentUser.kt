package com.shivam_raj.noteapp.onlineDatabase

import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

val currentUser: Flow<FirebaseUser?> = callbackFlow {
    val authStateListener = FirebaseAuth.AuthStateListener { auth ->
        trySend(auth.currentUser)
    }
    Firebase.auth.addAuthStateListener(authStateListener)
    awaitClose {
        Firebase.auth.removeAuthStateListener(authStateListener)
    }
}.asLiveData().asFlow()