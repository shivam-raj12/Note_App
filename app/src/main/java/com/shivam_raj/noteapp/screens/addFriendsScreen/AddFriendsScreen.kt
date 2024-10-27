package com.shivam_raj.noteapp.screens.addFriendsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import com.shivam_raj.noteapp.database.Note
import com.shivam_raj.noteapp.onlineDatabase.FetchingManager
import com.shivam_raj.noteapp.onlineDatabase.User
import com.shivam_raj.noteapp.onlineDatabase.addFriendToNote
import com.shivam_raj.noteapp.onlineDatabase.removeFriendFromNote
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFriendsScreen(
    note: Note,
    navController: NavController = rememberNavController()
) {
    var searchFieldValue by remember {
        mutableStateOf("")
    }
    val suggestedUser =
        Firebase.firestore.collection("Users").whereEqualTo("email", searchFieldValue).snapshots()
            .collectAsState(null).value?.firstOrNull()?.toObject(User::class.java)

    val friendsList = remember {
        mutableStateSetOf<Friend>()
    }
    var isLoading by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(coroutineScope) {
        isLoading = true
        val pair = FetchingManager.getFriendsList(note.id)
        pair.second?.let {
            snackBarHostState.showSnackbar(it)
        }
        friendsList.addAll(pair.first)
        isLoading = false
    }
    Scaffold(
        modifier = Modifier.imePadding(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Add Friends")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = searchFieldValue,
                onValueChange = {
                    searchFieldValue = it
                },
                singleLine = true,
                maxLines = 1,
                placeholder = {
                    Text(text = "Enter your friend's email")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            SearchSuggestion(
                user = suggestedUser,
                userAlreadyExistInFriend = friendsList.any { it.user.uid == suggestedUser?.uid },
                onAddToFriends = {
                    if (suggestedUser != null) {
                        friendsList.add(Friend(user = suggestedUser, allowEditing = it))
                        searchFieldValue = ""
                        coroutineScope.launch {
                            val e = addFriendToNote(
                                note,
                                Friend(user = suggestedUser, allowEditing = it)
                            )
                            if (e != null) {
                                snackBarHostState.showSnackbar(e)
                            }
                        }
                    }
                }
            )
            LazyColumn(
                Modifier.weight(1f)
            ) {
                if (friendsList.isEmpty()) {
                    if (isLoading) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    } else {
                        item {
                            Text(
                                text = "No friends found",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    stickyHeader {
                        Row(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(start = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "User detail"
                            )
                            VerticalDivider()
                            Text(
                                modifier = Modifier.padding(end = 8.dp),
                                text = "Allow\nEditing"
                            )
                            VerticalDivider()
                            Text(
                                modifier = Modifier,
                                text = "Remove"
                            )
                        }
                    }
                    friendsList.forEach {
                        item(
                            key = it.user.uid
                        ) {
                            ListItem(
                                friend = it,
                                onRemoveFriendClicked = {
                                    friendsList.remove(it)
                                    val e = removeFriendFromNote(note.id, it.user.uid)
                                    if (e != null) {
                                        coroutineScope.launch {
                                            snackBarHostState.showSnackbar(e)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}