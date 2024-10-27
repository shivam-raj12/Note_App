package com.shivam_raj.noteapp.onlineDatabase

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.shivam_raj.noteapp.database.Note
import com.shivam_raj.noteapp.screens.addFriendsScreen.Friend
import kotlinx.coroutines.tasks.await

suspend fun uploadNote(note: Note): String? {
    val firestore = Firebase.firestore
    val currentUser =
        Firebase.auth.currentUser ?: return "Please first login to your account and then try again."
    try {
        val path = firestore.document("Notes/${currentUser.uid}-${note.id}")
        val doc = path.get().await()
        if (!doc.exists()) {
            path.set(note.copy(userId = currentUser.uid)).await()
            return "Synced your note successfully."
        } else {
            val syncedNote = doc.toObject(Note::class.java)
            if (syncedNote.toString() != note.toString()) {
                path.set(note, SetOptions.merge()).await()
                return "Updated your synced note successfully."
            }
            return "Your note is already synced."
        }
    } catch (e: Exception) {
        return e.message
    }
}

suspend fun addFriendToNote(note: Note, friend: Friend): String? {
    val firestore = Firebase.firestore
    val currentUser =
        Firebase.auth.currentUser ?: return "You are not logged in. Please login and try again."
    try {
        val path = firestore.document("Notes/${currentUser.uid}-${note.id}")
        if (!path.get().await().exists()) {
            path.set(note.copy(userId = currentUser.uid)).await()
        }
        if (friend.allowEditing) {
            path.update("allowWritingTo", FieldValue.arrayUnion(friend.user.uid)).await()
        } else {
            path.update("onlyReadTo", FieldValue.arrayUnion(friend.user.uid)).await()
        }
        return null
    } catch (e: Exception) {
        return e.message.toString()
    }
}

fun removeFriendFromNote(noteId: String, friendUid: String): String? {
    val firestore = Firebase.firestore
    val currentUser =
        Firebase.auth.currentUser ?: return "You are not logged in. Please login and try again."

    try {
        val path = firestore.document("Notes/${currentUser.uid}-${noteId}")
        path.update("allowWritingTo", FieldValue.arrayRemove(friendUid))
        path.update("onlyReadTo", FieldValue.arrayRemove(friendUid)).addOnSuccessListener {
            Log.d("TAG", "removeFriendFromNote: $it")
        }
        return null
    }catch (e:Exception){
        Log.d("TAG", "removeFriendFromNote: ${e.message}")
        return e.message.toString()
    }
}