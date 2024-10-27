package com.shivam_raj.noteapp.screens.addFriendsScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.shivam_raj.noteapp.R
import com.shivam_raj.noteapp.onlineDatabase.User

@Composable
fun SearchSuggestion(
    user: User?,
    userAlreadyExistInFriend: Boolean = false,
    onAddToFriends: (Boolean) -> Unit
) {
    var checked by remember(user) {
        mutableStateOf(false)
    }
    val currentUser = Firebase.auth.currentUser
    AnimatedVisibility(visible = user != null) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (user?.profile.isNullOrEmpty()) {
                    Box(
                        Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = user?.name?.first()?.toString() ?: "G",
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                } else {
                    AsyncImage(
                        model = user?.profile,
                        contentDescription = "Friend profile",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                        placeholder = painterResource(R.drawable.default_profile),
                        contentScale = ContentScale.Crop
                    )
                }
                Column {
                    Text(
                        text = user?.name ?: "Guest",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = user?.email ?: "Guest",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            if (userAlreadyExistInFriend){
                Text(
                    text = "Already exists in friends",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic
                )
            }else if (user?.uid==currentUser?.uid){
                Text(
                    text = "It's you.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic
                )
            } else{
                Switch(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                    }
                )
                FilledTonalButton(
                    onClick = { onAddToFriends(checked) }
                ) {
                    Text(text = "Add")
                }
            }
        }
    }
}