package com.shivam_raj.noteapp.screens.detailNoteScreen

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.shivam_raj.noteapp.R
import com.shivam_raj.noteapp.database.Note
import com.shivam_raj.noteapp.getSimpleFormattedDate
import com.shivam_raj.noteapp.onlineDatabase.uploadNote
import kotlinx.coroutines.launch

@Composable
fun TopBarActionButtons(
    note: Note,
    isSharedNote: Boolean,
    snackBarHostState: SnackbarHostState,
    moveToEditNoteScreen: () -> Unit,
    onDeleteNoteClicked: () -> Unit,
    moveToAddFriendsScreen: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var showDropDown by remember {
        mutableStateOf(false)
    }
    val dropdownMenuOptions = listOf("Cloud sync", "Add friends", "Copy", "Delete")
    val dropdownMenuOptionsIcon =
        listOf(R.drawable.upload, R.drawable.add_friends, R.drawable.copy, Icons.Filled.Delete)
    val clipboardManager = LocalClipboardManager.current

    IconButton(onClick = moveToEditNoteScreen) {
        Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit note")
    }

    IconButton(onClick = { showDropDown = !showDropDown }) {
        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
    }
    DropdownMenu(
        expanded = showDropDown,
        onDismissRequest = { showDropDown = false }
    ) {
        dropdownMenuOptions.forEachIndexed { index, s ->
            val icon = dropdownMenuOptionsIcon[index]
            DropdownMenuItem(
                text = { Text(text = s) },
                leadingIcon = {
                    if (icon is Int) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = s
                        )
                    } else {
                        Icon(
                            imageVector = icon as ImageVector,
                            contentDescription = s
                        )
                    }
                },
                onClick = {
                    showDropDown = false
                    when (index) {
                        0 -> {
                            coroutineScope.launch {
                                val message = uploadNote(note)
                                if (message != null) {
                                    snackBarHostState.showSnackbar(
                                        message = message,
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        }

                        1 -> {
                            moveToAddFriendsScreen()
                        }

                        2 -> {
                            clipboardManager.setText(
                                buildAnnotatedString {
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Title: ")
                                    }
                                    append(note.noteTitle)
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("\nDescription: ")
                                    }
                                    append(note.noteDescription)
                                    withStyle(SpanStyle(fontWeight = FontWeight.Light)) {
                                        append("\nCreated at ${getSimpleFormattedDate(note.dateAdded)}")
                                    }
                                }
                            )
                            Toast.makeText(context, "Note copied", Toast.LENGTH_SHORT).show()
                        }

                        3 -> {
                            onDeleteNoteClicked()
                        }
                    }
                },
                enabled = if (!isSharedNote) true else index==2 || index==3
            )
        }
    }

}