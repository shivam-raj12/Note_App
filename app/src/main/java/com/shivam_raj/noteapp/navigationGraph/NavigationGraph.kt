package com.shivam_raj.noteapp.navigationGraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.shivam_raj.noteapp.database.EmptyNote
import com.shivam_raj.noteapp.screens.SharedViewModel
import com.shivam_raj.noteapp.screens.addFriendsScreen.AddFriendsScreen
import com.shivam_raj.noteapp.screens.addNoteScreen.AddNoteScreen
import com.shivam_raj.noteapp.screens.createAccountOrLoginScreen.CreateAccountPage
import com.shivam_raj.noteapp.screens.createAccountOrLoginScreen.LogInScreen
import com.shivam_raj.noteapp.screens.detailNoteScreen.DetailNoteScreen
import com.shivam_raj.noteapp.screens.noteListScreen.NoteHomeScreen
import com.shivam_raj.noteapp.screens.passwordScreen.PasswordScreen
import com.shivam_raj.noteapp.screens.profileScreen.ProfileScreen
import com.shivam_raj.noteapp.screens.setPasswordScreen.SecurityData
import com.shivam_raj.noteapp.screens.setPasswordScreen.SetPasswordScreen
import com.shivam_raj.noteapp.screens.sharedViewModel

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        navigation(route = "auth", startDestination = Screens.LogInScreen.route) {
            composable(route = Screens.CreateAccountScreen.route) {
                CreateAccountPage(navController)
            }
            composable(route = Screens.LogInScreen.route) {
                LogInScreen(navController)
            }

        }
        navigation(route = "main", startDestination = Screens.NoteListScreen.route) {
            composable(route= Screens.ProfileScreen.route){
                ProfileScreen(navController)
            }
            composable(route = Screens.NoteListScreen.route) {
                val viewModel = it.sharedViewModel<SharedViewModel>(navController)
                if (navController.currentBackStackEntry?.destination?.route == Screens.NoteListScreen.route) viewModel.changeClickedNote(
                    null
                )
                NoteHomeScreen(
                    navController = navController,
                    onNoteClicked = viewModel::changeClickedNote
                )
            }
            composable(route = Screens.DetailNoteScreen.route) {
                val viewModel = it.sharedViewModel<SharedViewModel>(navController)
                val note by viewModel.clickedNote.collectAsStateWithLifecycle()
                DetailNoteScreen(navController = navController, note = note ?: EmptyNote)
            }
            composable(route = Screens.PasswordScreen.route) {
                val viewModel = it.sharedViewModel<SharedViewModel>(navController)
                val note by viewModel.clickedNote.collectAsStateWithLifecycle()
                PasswordScreen(note?.password ?: "", navController)
            }
            composable(route = Screens.AddNoteScreen.route) {
                val viewModel = it.sharedViewModel<SharedViewModel>(navController)
                val note by viewModel.clickedNote.collectAsStateWithLifecycle()
                val securityData = it.savedStateHandle.get<SecurityData>("security_data")
                AddNoteScreen(
                    navController = navController,
                    note = note,
                    securityData = securityData
                )
            }
            composable(route = Screens.SetPasswordScreen.route) {
                val viewModel = it.sharedViewModel<SharedViewModel>(navController)
                val note by viewModel.clickedNote.collectAsStateWithLifecycle()
                SetPasswordScreen(
                    SecurityData(
                        password = note?.password,
                        fakeTitle = note?.fakeTitle,
                        fakeDescription = note?.fakeDescription
                    ), navController
                )
            }
            composable(route = Screens.AddFriendsScreen.route) {
                val viewModel = it.sharedViewModel<SharedViewModel>(navController)
                val note by viewModel.clickedNote.collectAsStateWithLifecycle()
                AddFriendsScreen(note ?: EmptyNote, navController)
            }
        }
    }
}