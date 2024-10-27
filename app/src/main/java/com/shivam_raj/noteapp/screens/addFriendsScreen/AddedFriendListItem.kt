package com.shivam_raj.noteapp.screens.addFriendsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.shivam_raj.noteapp.R

@Composable
fun ListItem(
    friend: Friend,
    onRemoveFriendClicked:()->Unit
) {
    var checked by remember {
        mutableStateOf(friend.allowEditing)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (friend.user.profile.isEmpty()) {
                Box(
                    Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = friend.user.name.first().toString(),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            } else {
                AsyncImage(
                    model = friend.user.profile,
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
                    text = friend.user.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = friend.user.email,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
            }
        )
        IconButton(onClick = onRemoveFriendClicked) {
            Icon(imageVector = Icons.Default.Clear, contentDescription = null)
        }
    }
}