package com.shivam_raj.noteapp.screens.noteListScreen.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.shivam_raj.noteapp.LottieAnimations
import com.shivam_raj.noteapp.R
import com.shivam_raj.noteapp.rememberShakeController
import com.shivam_raj.noteapp.shake

/**
 * This confirmation dialog will be shown when user wants to delete the note
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteConfirmationDialog(
    numberOfNoteToBeDelete: Int = 1,
    notePassword: String? = null,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
) {
    var password by remember {
        mutableStateOf("")
    }
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    val shakeController = rememberShakeController()
    val onDeleteNoteClicked = {
        if (notePassword == null) {
            onConfirm()
        } else {
            if (password != notePassword) {
                shakeController.shake()
                password = ""
            } else {
                onConfirm()
            }
        }
    }
    BasicAlertDialog(onDismissRequest = onDismissRequest) {
        ElevatedCard(
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 6.dp,
                pressedElevation = 6.dp,
                focusedElevation = 6.dp,
                hoveredElevation = 6.dp,
                draggedElevation = 6.dp
            ),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                Modifier.padding(24.dp)
            ) {
                LottieAnimations(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (numberOfNoteToBeDelete == 1) "Are you sure?" else "Delete $numberOfNoteToBeDelete selected notes?",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = if (numberOfNoteToBeDelete == 1) {
                        "This action will delete your note permanently. Are you sure you want to proceed?"
                    } else {
                        "This action will delete your $numberOfNoteToBeDelete selected notes. Are you sure you want to proceed?\nThis action will not delete password protected notes."
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
                if (notePassword != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        modifier = Modifier.shake(shakeController),
                        value = password,
                        onValueChange = { password = it },
                        placeholder = {
                            Text(text = "Enter your note password")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = { onDeleteNoteClicked() }),
                        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(
                            '*'
                        ),
                        trailingIcon = {
                            if (password.isNotEmpty()) {
                                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                                    Icon(
                                        painter = painterResource(id = if (passwordVisibility) R.drawable.password_visibility_off else R.drawable.password_visible),
                                        contentDescription = null
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        supportingText = {
                            Text(text = "You have to enter note's password to delete it")
                        }
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismissRequest, colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text(text = "Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        enabled = notePassword == null || (password.isNotEmpty()),
                        onClick = onDeleteNoteClicked,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError,
                        )
                    ) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
}