package com.shivam_raj.noteapp.screens.noteListScreen.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shivam_raj.noteapp.LottieAnimations
import com.shivam_raj.noteapp.R

/**
 * When user don't have any saved note, this composable will be shown.
 */
@Composable
fun EmptyNoteList(
    onAddNoteClicked: () -> Unit,
    moveToLoginScreen: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "You haven't saved any notes yet.",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = if (!isSystemInDarkTheme()) Color(0xFF081E11) else MaterialTheme.colorScheme.onBackground,
            fontFamily = FontFamily(
                Font(
                    R.font.inknut_antiqua_medium,
                    FontWeight.Medium
                )
            ),
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        LottieAnimations(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            animation = R.raw.empty_list
        )
        Text(
            text = "Add your note or login to your account to sync them.",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            FilledTonalButton(onClick = onAddNoteClicked) {
                Text(text = "Add note")
            }
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                onClick = moveToLoginScreen
            ) {
                Text(text = "Login to your account")
            }
        }
    }

}