package com.shivam_raj.noteapp.screens.noteListScreen.extensionFunctions

import com.shivam_raj.noteapp.database.Note

fun List<Note>.shortedNote():List<Note>{
    return this.sortedWith(
        compareByDescending<Note> {
            it.pinnedAt ?: it.notePriority
        }.thenByDescending { it.notePriority }.thenByDescending { it.dateAdded }
    )
}