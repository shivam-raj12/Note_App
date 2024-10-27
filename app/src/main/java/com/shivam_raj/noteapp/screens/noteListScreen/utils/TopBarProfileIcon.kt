package com.shivam_raj.noteapp.screens.noteListScreen.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.asLiveData
import coil3.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.shivam_raj.noteapp.R
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

@Composable
fun ProfileIcon(
    modifier: Modifier = Modifier,
    onLogoutButtonClicked: () -> Unit,
    moveToSignInScreen: () -> Unit,
    moveToProfileScreen: () -> Unit
) {
    val currentUser by callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            auth.currentUser?.reload()
            trySend(auth.currentUser)
        }
        Firebase.auth.addAuthStateListener(authStateListener)
        awaitClose {
            Firebase.auth.removeAuthStateListener(authStateListener)
        }
    }.asLiveData().observeAsState()

    var showAccountInfo by remember {
        mutableStateOf(false)
    }
    if (currentUser == null) {
        Image(
            modifier = modifier
                .clip(CircleShape)
                .clickable { if (!showAccountInfo) showAccountInfo = true },
            painter = painterResource(R.drawable.default_profile),
            contentDescription = "Guest account"
        )
    } else if (currentUser?.photoUrl == null) {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .clickable { if (!showAccountInfo) showAccountInfo = true }
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currentUser?.displayName?.first()?.toString() ?: "G",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    } else {
        AsyncImage(
            model = currentUser?.photoUrl,
            contentDescription = "Profile photo",
            modifier = modifier
                .clip(CircleShape)
                .clickable { if (!showAccountInfo) showAccountInfo = true },
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.default_profile)
        )
    }
    AccountInfo(
        showAccountInfo = showAccountInfo,
        onDismiss = {
            showAccountInfo = false
        },
        onLogoutButtonClicked = onLogoutButtonClicked,
        moveToSignInScreen = moveToSignInScreen,
        moveToProfileScreen = moveToProfileScreen
    )
}