package com.shivam_raj.noteapp.onlineDatabase

import androidx.compose.runtime.Immutable

@Immutable
data class User(
    val uid: String = "",
    val profile: String = "",
    val name: String = "",
    val email: String = ""
)