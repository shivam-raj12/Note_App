package com.shivam_raj.noteapp.screens.noteListScreen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.shivam_raj.noteapp.database.Note

/**
 * A simple immutable data class which represents all the necessary parameters for displaying the [NoteList].
 * @param modifier Modifier to be applied to the [NoteList] layout i.e. LazyColumn
 * @param noteList List of all the saved notes
 * @param onDeleteNoteIconClick This callback is called when the delete icon of [NoteItem] will be clicked
 * @param onNoteLongClick This callback will be called when the user long presses a NoteList item. It is being used to enter actionMode which will allow users to select more notes and to perform a specific action to all the notes at once like deleting the note, or making all the selected note password protected, or to pin the note etc.
 * @param onNoteClick This callback will be called when the NoteList item will be clicked. It is used for two purposes:
 *  - When the actionMode is active, this click will select or deselect the note.
 *  - In normal mode it will simply open the DetailNoteScreen( or PasswordScreen if the note is locked).
 * @param selectedNoteList List of all the selected note.
 *  - it will be null in normal mode
 */
@Immutable
data class NoteListData(
    val modifier: Modifier = Modifier,
    val noteList: List<Note>,
    val onDeleteNoteIconClick: (Note) -> Unit,
    val onNoteLongClick: (Note) -> Unit,
    val onNoteClick: (Note) -> Unit,
    val selectedNoteList: List<Note>? = null
)
