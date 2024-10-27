package com.shivam_raj.noteapp.screens.noteListScreen.extensionFunctions

import com.shivam_raj.noteapp.database.Note

fun List<Note>.applySearchFilter(filter: String) : List<Note> {
    return this.filter {
        if (filter.isEmpty()) {
            true
        } else if (filter.isNotEmpty()) {
            (((it.fakeTitle ?: it.noteTitle)
                .startsWith(
                    prefix = filter,
                    ignoreCase = true
                )) ||
                    ((it.fakeDescription ?: it.noteDescription).startsWith(
                        prefix = filter,
                        ignoreCase = true
                    )))
        } else {
            false
        }
    }
}