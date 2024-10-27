package com.shivam_raj.noteapp.screens.noteListScreen.utils

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import coil3.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.shivam_raj.noteapp.R


@Composable
fun AccountInfo(
    showAccountInfo: Boolean,
    onDismiss: () -> Unit,
    onLogoutButtonClicked:()->Unit,
    moveToSignInScreen: () -> Unit,
    moveToProfileScreen:()->Unit
) {
    val currentUser = Firebase.auth.currentUser
    val alpha by animateFloatAsState(if (showAccountInfo) 1f else 0f, label = "")
    if (showAccountInfo) {
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Popup(
                alignment = Alignment.TopEnd,
                onDismissRequest = onDismiss
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.7f)
                        .clip(RoundedCornerShape(10.dp))
                        .shadow(30.dp, RoundedCornerShape(10.dp))
                        .customBackground(
                            isSystemInDarkTheme(),
                            MaterialTheme.colorScheme.surfaceBright
                        )
                        .padding(12.dp, 16.dp)
                        .alpha(alpha),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = "Close"
                            )
                        }
                        IconButton(onClick = {
                            // TODO: move to setting screen
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Settings,
                                contentDescription = "Settings"
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(
                                if (currentUser!=null){
                                    Modifier.clickable {
                                        onDismiss()
                                        moveToProfileScreen()
                                    }
                                }else{
                                    Modifier
                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PopUpProfile(Modifier.size(60.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(Modifier.weight(1f)) {
                            Text(
                                text = currentUser?.displayName ?: "Guest",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = currentUser?.email ?: "Guest",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.TwoTone.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(8.dp))
                    if (currentUser != null) {
                        FilledTonalButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onDismiss()
                                onLogoutButtonClicked()
                            }
                        ) {
                            Text(text = "Log out")
                        }
                    } else {
                        OutlinedButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onDismiss()
                                moveToSignInScreen()
                            }
                        ) {
                            Text("Log in")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PopUpProfile(modifier: Modifier = Modifier) {
    val currentUser = Firebase.auth.currentUser
    if (currentUser == null) {
        Image(
            modifier = modifier
                .clip(CircleShape),
            painter = painterResource(R.drawable.default_profile),
            contentDescription = "Guest account"
        )
    } else if (currentUser.photoUrl == null) {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currentUser.displayName?.first()?.toString() ?: "G",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp
            )
        }
    } else {
        AsyncImage(
            model = currentUser.photoUrl,
            contentDescription = "Profile photo",
            modifier = modifier.clip(CircleShape),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.default_profile)
        )
    }
}

private fun Modifier.customBackground(isDarkTheme: Boolean, darkThemeBackground: Color): Modifier {
    return this.then(
        if (isDarkTheme) {
            Modifier.background(darkThemeBackground)
        } else {
            Modifier
                .background(Color.White)
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF00BFA5),
                            Color(0xFFFFC107)
                        )
                    ),
                    alpha = 0.15f
                )
        }
    )
}