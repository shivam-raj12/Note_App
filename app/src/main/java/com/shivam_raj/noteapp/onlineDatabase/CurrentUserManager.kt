package com.shivam_raj.noteapp.onlineDatabase

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

suspend fun updateCurrentUserProfile(
    uri: String
) {
    val currentUser = Firebase.auth.currentUser ?: return
    try {
        val uploadTask = Firebase.storage.reference.child("User Profile/${currentUser.uid}")
            .putFile(Uri.parse(uri)).await()
        val url: Uri = uploadTask.storage.downloadUrl.await()
        currentUser.updateProfile(
            userProfileChangeRequest {
                photoUri = url
            }
        ).await()
        Firebase.firestore.document("Users/${currentUser.uid}").update("profile", url.toString())
            .await()
    } catch (e: Exception) {
        Log.d("TAG", "updateCurrentUserProfile: ${e.message}")
    }
}

suspend fun updateCurrentUserProfile(
    bitmap: Bitmap
) {
    val currentUser = Firebase.auth.currentUser ?: return
    val byteArray = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArray)
    try {
        val uploadTask = Firebase.storage.reference.child("User Profile/${currentUser.uid}")
            .putBytes(byteArray.toByteArray()).await()
        val url: Uri = uploadTask.storage.downloadUrl.await()
        currentUser.updateProfile(
            userProfileChangeRequest {
                photoUri = url
            }
        ).await()
        Firebase.firestore.document("Users/${currentUser.uid}").update("profile", url.toString())
            .await()
    } catch (e: Exception) {
        Log.d("TAG", "updateCurrentUserProfile: ${e.message}")
    }
}

suspend fun deleteCurrentUserProfile() {
    val currentUser = Firebase.auth.currentUser ?: return
    try {
        Firebase.storage.reference.child("User Profile/${currentUser.uid}").delete().await()
        Firebase.firestore.document("Users/${currentUser.uid}").update("profile", null).await()
        currentUser.updateProfile(
            userProfileChangeRequest {
                photoUri = null
            }
        ).await()
        currentUser.reload().await()
    } catch (e: Exception) {
        Log.d("TAG", "deleteCurrentUserProfile: ${e.message}")

    }
}