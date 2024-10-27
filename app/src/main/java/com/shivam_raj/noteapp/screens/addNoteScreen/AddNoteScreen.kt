package com.shivam_raj.noteapp.screens.addNoteScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.shivam_raj.noteapp.database.Note
import com.shivam_raj.noteapp.database.Priority
import com.shivam_raj.noteapp.navigationGraph.Screens
import com.shivam_raj.noteapp.screens.addNoteScreen.viewModel.AddNoteScreenViewModel
import com.shivam_raj.noteapp.screens.noteListScreen.noteRepo
import com.shivam_raj.noteapp.screens.setPasswordScreen.SecurityData

@Composable
fun AddNoteScreen(
    navController: NavController,
    note: Note?,
    securityData: SecurityData?
) {
    val addNoteScreenViewModel: AddNoteScreenViewModel = viewModel(factory = viewModelFactory {
        initializer {
            AddNoteScreenViewModel(noteRepo().noteRepository, note)
        }
    })
    val focusManager = LocalFocusManager.current

    val topBarButtonEnabled by remember {
        derivedStateOf {
            (addNoteScreenViewModel.title.value.isNotEmpty() && addNoteScreenViewModel.description.value.isNotEmpty())
        }
    }

    val snackBarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(Unit) {
        if (note?.allowEditing == false){
            snackBarHostState.showSnackbar(
                message = "You are not allowed to edit this note. Changes made to this note will be saved locally (only for you).",
                withDismissAction = true,
                duration = SnackbarDuration.Long
            )
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(MaterialTheme.colorScheme.background),
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        topBar = {
            AddNoteTopBar(
                priority = if (note != null) Priority.getValueWithId(note.notePriority) else null,
                enabled = topBarButtonEnabled,
                isNewNote = note == null,
                onSecurityClick = {
                    navController.navigate(Screens.SetPasswordScreen.route)
                },
                onBackArrowClick = {
                    navController.navigateUp()
                },
                colorIndex = note?.colorIndex,
                onSaveClick = { priority, colorIndex ->
                    addNoteScreenViewModel.onSaveNoteButtonClick(
                        priority,
                        colorIndex,
                        securityData ?: SecurityData(
                            note?.password,
                            note?.fakeTitle,
                            note?.fakeDescription
                        )
                    )
                    navController.navigate(Screens.NoteListScreen.route) {
                        popUpTo(Screens.AddNoteScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(
                    horizontal = 8.dp,
                )
        ) {
            OutlinedTextField(
                value = addNoteScreenViewModel.title.value,
                onValueChange = addNoteScreenViewModel::setTitle,
                singleLine = true,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Note's Title")
                },
                textStyle = MaterialTheme.typography.titleLarge,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrectEnabled = true,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )
            HorizontalDivider()
            OutlinedTextField(
                value = addNoteScreenViewModel.description.value,
                onValueChange = addNoteScreenViewModel::setDescription,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                placeholder = {
                    Text(text = "Note's Description")
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrectEnabled = true,
                    keyboardType = KeyboardType.Text
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )
        }
    }
}