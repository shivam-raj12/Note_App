package com.shivam_raj.noteapp.screens.addFriendsScreen

import androidx.compose.runtime.Immutable
import com.shivam_raj.noteapp.onlineDatabase.User

@Immutable
data class Friend(
    val user:User = User(),
    val allowEditing: Boolean = false
)
