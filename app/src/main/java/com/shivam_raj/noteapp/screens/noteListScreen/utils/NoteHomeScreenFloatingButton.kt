package com.shivam_raj.noteapp.screens.noteListScreen.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun NoteHomeScreenFloatingButton(
    visibility: Boolean,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = visibility,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        FloatingActionButton(onClick = onClick) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add note")
        }
    }
}