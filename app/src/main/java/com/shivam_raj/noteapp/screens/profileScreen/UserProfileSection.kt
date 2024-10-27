package com.shivam_raj.noteapp.screens.profileScreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.shivam_raj.noteapp.R
import com.shivam_raj.noteapp.onlineDatabase.deleteCurrentUserProfile
import com.shivam_raj.noteapp.onlineDatabase.updateCurrentUserProfile
import kotlinx.coroutines.launch

@Composable
fun UserProfileSection() {
    val currentUser = Firebase.auth.currentUser
    var isProfileLoading by remember {
        mutableStateOf(false)
    }
    var profile: Any? by remember {
        mutableStateOf(currentUser?.photoUrl)
    }
    val coroutineScope = rememberCoroutineScope()
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    val galleryRequest =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                coroutineScope.launch {
                    profile = uri
                    isProfileLoading = true
                    updateCurrentUserProfile(uri.toString())
                    isProfileLoading = false
                }
            }
        }
    val cameraRequest =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                coroutineScope.launch {
                    profile = bitmap
                    isProfileLoading = true
                    updateCurrentUserProfile(bitmap)
                    isProfileLoading = false
                }
            }
        }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (profile != null) {
            Box {
                AsyncImage(
                    model = profile,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(130.dp)
                        .clip(CircleShape)
                        .clickable {
                            showBottomSheet = !showBottomSheet
                        },
                    placeholder = painterResource(R.drawable.default_profile),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
                IconButton(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            MaterialTheme.colorScheme.secondaryContainer,
                            CircleShape
                        )
                        .align(Alignment.BottomEnd),
                    onClick = {
                        showDialog = !showDialog
                    }
                ) {
                    Icon(Icons.Outlined.Delete, null)
                }
            }
            if (isProfileLoading) {
                CircularProgressIndicator(
                    color = Color.White
                )
            }
        } else {
            Box(
                Modifier
                    .size(130.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                    .clip(CircleShape)
                    .clickable {
                        showBottomSheet = !showBottomSheet
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = currentUser?.displayName?.first()?.toString() ?: "G",
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }
    if (showBottomSheet) {
        BottomSheet(
            onDismiss = { showBottomSheet = false },
            onCameraButtonClick = {
                cameraRequest.launch()
            },
            onGalleryButtonClick = {
                galleryRequest.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        )
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Delete Profile") },
            text = { Text("Are you sure you want to delete your profile?") },
            confirmButton = {
                Button(onClick = {
                    coroutineScope.launch {
                        showDialog = false
                        profile = null
                        deleteCurrentUserProfile()
                    }
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onDismiss: () -> Unit,
    onCameraButtonClick: () -> Unit,
    onGalleryButtonClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            coroutineScope.launch {
                                sheetState.hide()
                                onDismiss()
                                onCameraButtonClick()
                            }
                        },
                    painter = painterResource(R.drawable.camera_svgrepo_com),
                    contentDescription = "Camera"
                )

                Text(
                    text = "Camera",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            coroutineScope.launch {
                                sheetState.hide()
                                onDismiss()
                                onGalleryButtonClick()
                            }
                        },
                    painter = painterResource(R.drawable.gallery_svgrepo_com),
                    contentDescription = "Camera"
                )
                Text(
                    text = "Gallery",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
    }

}
