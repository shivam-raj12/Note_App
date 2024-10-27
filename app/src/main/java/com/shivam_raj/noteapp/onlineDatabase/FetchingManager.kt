package com.shivam_raj.noteapp.onlineDatabase

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import com.shivam_raj.noteapp.database.Note
import com.shivam_raj.noteapp.screens.addFriendsScreen.Friend
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FetchingManager {
    companion object {
        suspend fun getFriendsList(noteId: String): Pair<List<Friend>, String?> {
            val firestore = Firebase.firestore
            val currentUser =
                Firebase.auth.currentUser ?: return Pair(emptyList(), "You are not logged in.")
            val friendList = arrayListOf<Friend>()
            val userPath = firestore.collection("Users")
            val noteRef = firestore.document("Notes/${currentUser.uid}-$noteId")
            try {
                val doc = noteRef.get().await()
                val allowEditing = (doc.get("allowWritingTo") ?: emptyList<String>()) as List<*>
                val onlyRead = (doc.get("onlyReadTo") ?: emptyList<String>()) as List<*>
                allowEditing.forEach {
                    val user =
                        userPath.document(it.toString()).get().await().toObject(User::class.java)
                    if (user != null) {
                        friendList.add(Friend(user, true))
                    }
                }
                onlyRead.forEach {
                    val user =
                        userPath.document(it.toString()).get().await().toObject(User::class.java)
                    if (user != null) {
                        friendList.add(Friend(user, false))
                    }
                }
            } catch (e: Exception) {
                return Pair(friendList, e.message.toString())
            }
            return Pair(friendList, null)
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        fun getSyncedNoteList(): Flow<List<Note>> {
            return currentUser.flatMapLatest { user ->
                if (user == null) {
                    flowOf(emptyList())
                } else {
                    Firebase.firestore.collection("Notes")
                        .whereEqualTo("userId", user.uid).snapshots()
                        .map { it.toObjects(Note::class.java) }
                }
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        fun getWritingAllowedSharedNoteList(): Flow<List<Note>> {
            return currentUser.flatMapLatest { user ->
                if (user == null) {
                    flowOf(emptyList())
                } else {
                    Firebase.firestore.collection("Notes")
                        .whereArrayContains("allowWritingTo", user.uid).snapshots()
                        .map { it.toObjects(Note::class.java) }
                }
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        fun getReadOnlySharedNoteList(): Flow<List<Note>> {
            return currentUser.flatMapLatest { user ->
                if (user == null) {
                    flowOf(emptyList())
                } else {
                    Firebase.firestore.collection("Notes")
                        .whereArrayContains("onlyReadTo", user.uid).snapshots()
                        .map { it.toObjects(Note::class.java) }
                }
            }
        }

    }
}