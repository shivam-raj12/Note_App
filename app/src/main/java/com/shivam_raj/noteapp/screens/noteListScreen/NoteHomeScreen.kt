package com.shivam_raj.noteapp.screens.noteListScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.shivam_raj.noteapp.Application
import com.shivam_raj.noteapp.database.Note
import com.shivam_raj.noteapp.navigationGraph.Screens
import com.shivam_raj.noteapp.onlineDatabase.currentUser
import com.shivam_raj.noteapp.screens.noteListScreen.extensionFunctions.applySearchFilter
import com.shivam_raj.noteapp.screens.noteListScreen.extensionFunctions.shortedNote
import com.shivam_raj.noteapp.screens.noteListScreen.utils.ActionModeIconsList
import com.shivam_raj.noteapp.screens.noteListScreen.utils.DeleteConfirmationDialog
import com.shivam_raj.noteapp.screens.noteListScreen.utils.EmptyNoteList
import com.shivam_raj.noteapp.screens.noteListScreen.utils.LogoutConfirmationDialog
import com.shivam_raj.noteapp.screens.noteListScreen.utils.NoteHomeScreenFloatingButton
import com.shivam_raj.noteapp.screens.noteListScreen.utils.SetPasswordDialog
import com.shivam_raj.noteapp.screens.noteListScreen.viewModel.NoteListScreenViewModel

/**
 * A composable destination function and also a starting destination for the app.
 *
 * This app calls various other composable function depends on if the user saved any note or not. Below are the conditions:
 *
 *  - If the note list returns null, it means the device is still loading the saved data from the room database. In this case a CircularProgressBar is shown.
 *  - If the note list is an empty list, it means the user haven't saved any notes yet. In this case [EmptyNoteList] composable is shown.
 *  - If the note list is not empty, it means the user have some saved notes. In this case all the notes with [NoteList] composable is shown.
 *
 * The top bar of this composable [NoteListTopBar] contains a search field. This search field also creates two possibilities:
 *  - If the search field is empty, it means the user haven't searched for any note. In this case we are showing all the saved notes of the user.
 *  - If the search field is not empty, it means the user have searched for any specific note. In this case we filer our list in following ways:
 *      - We filter our offlineNoteList with those notes whose title or description starts with the value of search field.
 *      - If the note contains fake title, then we ignores the original note's title and filter offlineNoteList with those notes whose fake title matches search field value.
 *      - If the note contains fake description, then we ignores the original note's description and filter offlineNoteList with those notes whose fake description matches search field value.
 *          - Reason for searching in fake title or fake description instead of original title and description is that if note contains fake title and fake description, we don't show the original title and description on the homeScreen. We only show original title and description only on DetailNoteScreen.
 *  - If the list of filtered note is empty then we show [EmptySearchResult] composable function.
 *
 *  This composable function also shows two dialogBox: [DeleteConfirmationDialog] and [SetPasswordDialog] . These dialog box are shown when the delete icon or set password icon of [NoteListTopBar] are clicked in actionMode respectively.
 * @param noteListScreenViewModel ViewModel for this composable function.
 * @see NoteListScreenViewModel
 */
@Composable
fun NoteHomeScreen(
    noteListScreenViewModel: NoteListScreenViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                NoteListScreenViewModel(noteRepo().noteRepository)
            }
        }
    ),
    navController: NavController,
    onNoteClicked: (Note) -> Unit
) {
    BackHandler(
        enabled = noteListScreenViewModel.isSelectionModeActive
    ) {
        noteListScreenViewModel.reverseSelectionMode()
    }

    var showLogoutConfirmationDialog by remember {
        mutableStateOf(false)
    }

    val currentUser = currentUser.collectAsState(null).value

    /**
     * Fetching all the saved notes and applying search field filter.
     *  - It will return null, if the data from room database are still loading
     *  - It will return empty list, if there is no any saved note
     */
    val offlineNoteList: List<Note>? =
        noteListScreenViewModel.offlineNoteList.collectAsState(initial = null).value?.applySearchFilter(
            noteListScreenViewModel.searchBarTextValue
        )

    val onlineNoteList: List<Note>? =
        noteListScreenViewModel.onlineNoteList.collectAsState(null).value?.applySearchFilter(
            noteListScreenViewModel.searchBarTextValue
        )?.shortedNote()

    val sharedNoteList: List<Note>? = noteListScreenViewModel.sharedNoteList.collectAsState(null)
        .value?.applySearchFilter(noteListScreenViewModel.searchBarTextValue)?.shortedNote()
        ?.toSet()?.toList()

    Scaffold(
        modifier = Modifier.imePadding(),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        snackbarHost = {
            SnackbarHost(
                hostState = noteListScreenViewModel.snackBarHostState
            )
        },
        floatingActionButton = {
            NoteHomeScreenFloatingButton(
                visibility = (currentUser != null || !offlineNoteList.isNullOrEmpty() || noteListScreenViewModel.searchBarTextValue.isNotEmpty()),
                onClick = remember {
                    {
                        navController.navigate(Screens.AddNoteScreen.route)
                    }
                }
            )
        },
        topBar = {
            if (currentUser != null || !offlineNoteList.isNullOrEmpty() || noteListScreenViewModel.searchBarTextValue.isNotEmpty()) {
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onBackground) {
                    NoteListTopBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        actionModeText = noteListScreenViewModel.selectedNotes.size.toString(),
                        value = noteListScreenViewModel.searchBarTextValue,
                        onValueChange = noteListScreenViewModel::updateSearchBarText,
                        onClearFilterClicked = {
                            noteListScreenViewModel.updateSearchBarText("")
                        },
                        showAction = noteListScreenViewModel.isSelectionModeActive,
                        onCloseActionModeClick = {
                            noteListScreenViewModel.reverseSelectionMode()
                        },
                        content = {
                            ActionModeIconsList(
                                showPinIcon = noteListScreenViewModel.pinIcon,
                                onSelectAllIconClick = {
                                    offlineNoteList?.forEach {
                                        noteListScreenViewModel.addNoteToSelectedNoteList(it)
                                    }
                                },
                                onPinUnpinIconClick = noteListScreenViewModel::onPinIconClick,
                                onLockIconClick = noteListScreenViewModel::onLockIconClick,
                                onDeleteIconClick = noteListScreenViewModel::onDeleteIconClick
                            )
                        },
                        onLogoutButtonClicked = {
                            showLogoutConfirmationDialog = !showLogoutConfirmationDialog
                        },
                        moveToSignInScreen = remember {
                            {
                                navController.navigate("auth")
                            }
                        },
                        moveToProfileScreen = remember {
                            {
                                navController.navigate(Screens.ProfileScreen.route)
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        if (offlineNoteList != null || currentUser != null) {
            if (currentUser != null || !offlineNoteList.isNullOrEmpty() || noteListScreenViewModel.searchBarTextValue.isNotEmpty()) {
                NoteList(
                    currentUser = currentUser,
                    NoteListData(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        offlineNoteList = offlineNoteList,
                        onlineNoteList = onlineNoteList,
                        sharedNoteList = sharedNoteList,
                        onDeleteNoteIconClick = noteListScreenViewModel::deleteNote,
                        onNoteLongClick = noteListScreenViewModel::onNoteLongClick,
                        onNoteClick = remember {
                            { note ->
                                if (!noteListScreenViewModel.isSelectionModeActive) {
                                    onNoteClicked(note)
                                    if (note.password == null) {
                                        navController.navigate(Screens.DetailNoteScreen.route)
                                    } else {
                                        navController.navigate(Screens.PasswordScreen.route)
                                    }
                                } else {
                                    noteListScreenViewModel.onNoteLongClick(note)
                                }
                            }
                        },
                        selectedNoteList = if (noteListScreenViewModel.isSelectionModeActive) noteListScreenViewModel.selectedNotes else null
                    )
                )
            } else {
                EmptyNoteList(
                    onAddNoteClicked = remember { { navController.navigate(Screens.AddNoteScreen.route) } },
                    moveToLoginScreen = remember {
                        {
                            navController.navigate("auth")
                        }
                    }
                )
            }
            if (noteListScreenViewModel.showDeleteConfirmationDialog) {
                DeleteConfirmationDialog(
                    numberOfNoteToBeDelete = noteListScreenViewModel.selectedNotes.size,
                    onDismissRequest = noteListScreenViewModel::onDeleteIconClick,
                    onConfirm = noteListScreenViewModel::deleteAllSelectedNote
                )
            }
            if (noteListScreenViewModel.showSetPasswordDialogBox) {
                SetPasswordDialog(
                    onDismissRequest = noteListScreenViewModel::onLockIconClick,
                    onSetPasswordClick = noteListScreenViewModel::setPasswordForAllSelectedNote
                )
            }
            if (showLogoutConfirmationDialog) {
                LogoutConfirmationDialog(
                    onDismissRequest = {
                        showLogoutConfirmationDialog = false
                    },
                    onConfirmClicked = {
                        Firebase.auth.signOut()
                        showLogoutConfirmationDialog = false
                    }
                )
            }
        } else {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

fun CreationExtras.noteRepo(): Application {
    return (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as Application
}