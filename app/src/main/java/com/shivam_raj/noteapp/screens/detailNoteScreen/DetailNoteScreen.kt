package com.shivam_raj.noteapp.screens.detailNoteScreen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.shivam_raj.noteapp.Application
import com.shivam_raj.noteapp.database.Note
import com.shivam_raj.noteapp.database.Priority
import com.shivam_raj.noteapp.getSimpleFormattedDate
import com.shivam_raj.noteapp.navigationGraph.Screens
import com.shivam_raj.noteapp.onlineDatabase.User
import com.shivam_raj.noteapp.screens.noteListScreen.utils.DeleteConfirmationDialog
import com.shivam_raj.noteapp.ui.theme.colorProviderForNoteBackground
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * This screen will show the selected note in detail.
 *  - Note title code will be found in topBar section. It is being shown with LargeTopAppBar.
 *  - Note description and time is in content section of Scaffold
 * @param note Instance of [Note] class which will be shown in detail.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailNoteScreen(
    navController: NavController,
    note: Note
) {
    val colorFamily = colorProviderForNoteBackground(
        isDarkTheme = isSystemInDarkTheme(),
        notePriority = Priority.getValueWithId(note.notePriority),
        noteCustomColorIndex = note.colorIndex
    )
    val isSharedNote = if (note.userId==null) false else note.userId != (Firebase.auth.currentUser?.uid?:"")
    val scrollState = rememberLazyListState()
    var showTitle by remember { mutableStateOf(false) }
    LaunchedEffect(scrollState) {
        snapshotFlow { if (scrollState.firstVisibleItemIndex == 0) scrollState.firstVisibleItemScrollOffset else 400 }
            .collect {
                showTitle = it >= 300
            }
    }
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }
    val noteRepository = (LocalContext.current.applicationContext as Application).noteRepository
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier
            .imePadding(),
        containerColor = colorFamily.colorContainer,
        contentColor = colorFamily.onColorContainer,
        topBar = {
            TopAppBar(
                title = {
                    AnimatedVisibility(
                        showTitle,
                        enter = slideInVertically { it } + fadeIn(),
                        exit = slideOutVertically { it } + fadeOut()
                    ) {
                        Text(text = note.noteTitle, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                actions = {
                    TopBarActionButtons(
                        note = note,
                        snackBarHostState = snackBarHostState,
                        moveToEditNoteScreen = {
                            navController.navigate(Screens.AddNoteScreen.route)
                        },
                        onDeleteNoteClicked = {
                            showDeleteDialog = true
                        },
                        moveToAddFriendsScreen = {
                            navController.navigate(Screens.AddFriendsScreen.route)
                        },
                        isSharedNote = isSharedNote
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                    navigationIconContentColor = colorFamily.onColorContainer,
                    titleContentColor = colorFamily.onColorContainer,
                    actionIconContentColor = colorFamily.onColorContainer
                )
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState
            )
        }
    ) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 12.dp),
            contentPadding = PaddingValues(bottom = 60.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item(
                key = "Note_Title"
            ) {
                Text(
                    text = note.noteTitle,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
            stickyHeader(
                key = "Note_Date"
            ) {
                NoteDate(
                    backgroundColor = colorFamily.color,
                    onBackgroundColor = colorFamily.onColor,
                    lastUpdate = note.lastUpdate,
                    createdOn = note.dateAdded,
                    ownerId = if (isSharedNote) note.userId else null
                )
            }
            item(key = "Note_Description") {
                Text(
                    text = note.noteDescription,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
        if (showDeleteDialog) {
            DeleteConfirmationDialog(
                notePassword = note.password,
                onDismissRequest = { showDeleteDialog = false },
                onConfirm = {
                    showDeleteDialog = false
                    coroutineScope.launch {
                        noteRepository.deleteNote(note)
                    }
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
private fun NoteDate(
    backgroundColor: Color,
    onBackgroundColor: Color,
    lastUpdate: Long,
    createdOn: Long,
    ownerId: String?
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    var owner: User? by remember {
        mutableStateOf(null)
    }
    if (ownerId != null) {
        LaunchedEffect(ownerId) {
            try {
                owner = Firebase.firestore.document("Users/$ownerId").get().await()
                    .toObject(User::class.java)
            } catch (e: Exception) {
                Log.d("TAG", "NoteDate: ${e.message}")
            }
        }
    }
    val rotation by animateFloatAsState(
        if (expanded) 180f else 0f, label = ""
    )
    CompositionLocalProvider(LocalContentColor provides onBackgroundColor) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    backgroundColor,
                    RoundedCornerShape(5.dp)
                )
                .then(
                    if (ownerId == null) {
                        Modifier
                    } else {
                        Modifier.clickable {
                            expanded = !expanded
                        }
                    }
                )
                .padding(16.dp, 8.dp)
                .animateContentSize()
        ) {
            Text(
                text = "Last update: ${getSimpleFormattedDate(lastUpdate)}"
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Created at: ${getSimpleFormattedDate(createdOn)}"
            )
            if (ownerId != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Owner:" + (owner?.name ?: "Unknown"),
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.alpha(0.8f)
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier
                            .size(18.dp)
                            .alpha(0.6f)
                            .rotate(rotation)
                    )
                }
            }
            if (expanded) {
                Column {
                    Text(
                        text = "Note created by:",
                        style = MaterialTheme.typography.bodySmall,
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(Modifier.height(6.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = owner?.name?.first()?.toString() ?: "",
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text(
                                text = owner?.name ?: "Loading...",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            Text(
                                text = owner?.email ?: "Loading...",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                    }
                }
            }
        }
    }
}