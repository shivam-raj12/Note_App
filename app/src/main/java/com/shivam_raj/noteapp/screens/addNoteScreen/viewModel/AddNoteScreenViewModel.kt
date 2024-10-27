package com.shivam_raj.noteapp.screens.addNoteScreen.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivam_raj.noteapp.database.Note
import com.shivam_raj.noteapp.database.NoteRepository
import com.shivam_raj.noteapp.database.Priority
import com.shivam_raj.noteapp.screens.setPasswordScreen.SecurityData
import kotlinx.coroutines.launch

class AddNoteScreenViewModel(
    private val noteRepository: NoteRepository,
    private val note: Note?
) : ViewModel() {
    val title = mutableStateOf(note?.noteTitle ?: "")

    val description = mutableStateOf(note?.noteDescription ?: "")

    fun setTitle(title: String) {
        this.title.value = title
    }

    fun setDescription(description: String) {
        this.description.value = description
    }

    fun onSaveNoteButtonClick(
        priority: Priority,
        colorIndex: Int?,
        securityData: SecurityData
    ) {
        val updatedNote = note?.copy(
            noteTitle = title.value.trim(),
            noteDescription = description.value.trim(),
            notePriority = priority.id,
            fakeTitle = securityData.fakeTitle,
            fakeDescription = securityData.fakeDescription,
            password = securityData.password,
            colorIndex = colorIndex,
            lastUpdate = System.currentTimeMillis()
        ) ?: Note(
            noteTitle = title.value.trim(),
            noteDescription = description.value.trim(),
            notePriority = priority.id,
            fakeTitle = securityData.fakeTitle,
            fakeDescription = securityData.fakeDescription,
            password = securityData.password,
            colorIndex = colorIndex,
            lastUpdate = System.currentTimeMillis(),
            dateAdded = System.currentTimeMillis()
        )
        if (note == null || note.toString() != updatedNote.toString()) {
            viewModelScope.launch {
                noteRepository.addOrUpdateNote(updatedNote)
            }
        }
    }
}