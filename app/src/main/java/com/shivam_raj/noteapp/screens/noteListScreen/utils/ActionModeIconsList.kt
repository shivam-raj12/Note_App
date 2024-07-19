package com.shivam_raj.noteapp.screens.noteListScreen.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.shivam_raj.noteapp.R

/**
 * Icons which are shown in top bar when action mode enters
 * @param showPinIcon Whether to show pin icon or unpin icon
 */
@Composable
fun ActionModeIconsList(
    showPinIcon: Boolean,
    onSelectAllIconClick:()->Unit,
    onPinUnpinIconClick:()->Unit,
    onLockIconClick:()->Unit,
    onDeleteIconClick:()->Unit,
) {
    IconButton(onClick = onSelectAllIconClick) {
        Icon(
            painter = painterResource(id = R.drawable.select_all),
            contentDescription = "Select all"
        )
    }
    IconButton(onClick = onPinUnpinIconClick) {
        Icon(
            painter = painterResource(id = if (showPinIcon) R.drawable.pin else R.drawable.pin_off),
            contentDescription = "Pin/Unpin"
        )
    }
    IconButton(onClick = onLockIconClick) {
        Icon(
            imageVector = Icons.Outlined.Lock,
            contentDescription = "Lock"
        )
    }
    IconButton(onClick = onDeleteIconClick) {
        Icon(
            imageVector = Icons.Outlined.Delete,
            contentDescription = "Delete",
        )
    }
}