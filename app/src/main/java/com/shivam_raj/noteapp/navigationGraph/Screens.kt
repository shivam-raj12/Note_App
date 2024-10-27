package com.shivam_raj.noteapp.navigationGraph

sealed class Screens(val route: String){
    data object CreateAccountScreen:Screens("create_account")
    data object LogInScreen:Screens("login")
    data object ForgetPasswordScreen:Screens("forget_password")
    data object NoteListScreen:Screens("main_screen")
    data object DetailNoteScreen:Screens("detail_note_screen")
    data object AddNoteScreen:Screens("add_or_edit_note")
    data object PasswordScreen:Screens("password_screen")
    data object SetPasswordScreen:Screens("set_password_screen")
    data object AddFriendsScreen:Screens("add_friends_screen")
    data object ProfileScreen:Screens("profile_screen")
}