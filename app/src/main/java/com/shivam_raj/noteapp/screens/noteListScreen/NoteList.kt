package com.shivam_raj.noteapp.screens.noteListScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Composable function which displays all the notes in a list(Lazy Column)
 * @param noteListData An instance of [NoteListData] class
 * @see NoteListData
 * @see NoteItem
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteList(
    noteListData: NoteListData
) {
    LazyColumn(
        modifier = noteListData.modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp)
    ) {
        items(
            items = noteListData.noteList,
            key = {
                it.id
            }
        ) {
            NoteItem(
                modifier = Modifier.animateItemPlacement(),
                note = it,
                onDeleteNoteIconClick = noteListData.onDeleteNoteIconClick,
                onNoteClick = noteListData.onNoteClick,
                onNoteLongClick = noteListData.onNoteLongClick,
                isSelected = noteListData.selectedNoteList?.contains(it)
            )
        }
    }
}