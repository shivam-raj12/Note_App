package com.shivam_raj.noteapp.screens.noteListScreen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseUser
import com.shivam_raj.noteapp.R

/**
 * Composable function which displays all the notes in a list(Lazy Column)
 * @param noteListData An instance of [NoteListData] class
 * @see NoteListData
 * @see NoteItem
 */

private val filterList = listOf("Saved notes", "Synced notes", "Shared notes")

@Composable
fun NoteList(
    currentUser: FirebaseUser?,
    noteListData: NoteListData
) {
    var selectedFilter by rememberSaveable {
        mutableIntStateOf(0)
    }
    val noteList = if (selectedFilter == 0) {
        noteListData.offlineNoteList
    } else if (selectedFilter == 1 && currentUser != null) {
        noteListData.onlineNoteList
    } else if (selectedFilter == 2 && currentUser != null) {
        noteListData.sharedNoteList
    } else {
        emptyList()
    }

    LazyColumn(
        modifier = noteListData.modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp)
    ) {
        item {
            Row(
                Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                filterList.forEachIndexed { index, s ->
                    ElevatedFilterChip(
                        selected = selectedFilter == index,
                        onClick = {
                            selectedFilter = index
                        },
                        label = {
                            Text(
                                text = "$s (" + when (index) {
                                    0 -> noteListData.offlineNoteList?.size ?: "0"
                                    1 -> noteListData.onlineNoteList?.size ?: "0"
                                    else -> noteListData.sharedNoteList?.size ?: "0"
                                } + ")",
                            )
                        }
                    )
                }
            }
        }
        if (!noteList.isNullOrEmpty()) {
            items(
                items = noteList,
                key = {
                    it.id
                }
            ) {
                NoteItem(
                    modifier = Modifier.animateItem(),
                    note = it,
                    onDeleteNoteIconClick = noteListData.onDeleteNoteIconClick,
                    onNoteClick = noteListData.onNoteClick,
                    onNoteLongClick = noteListData.onNoteLongClick,
                    isSelected = noteListData.selectedNoteList?.contains(it)
                )
            }
        } else if ((selectedFilter == 0 && noteListData.offlineNoteList.isNullOrEmpty()) ||
            (currentUser != null && ((selectedFilter == 1 && noteListData.onlineNoteList.isNullOrEmpty()) ||
                    (selectedFilter == 2 && noteListData.sharedNoteList.isNullOrEmpty())))
        ) {
            item {
                Text(
                    modifier = Modifier.fillParentMaxWidth(),
                    text = "Oops! Looks like things are a bit quiet here :(",
                    style = MaterialTheme.typography.titleLarge,
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
            }
        } else {
            item {
                Text(
                    modifier = Modifier.fillParentMaxWidth(),
                    text = "You need to sign in before you can rustle up some notes. :)",
                    style = MaterialTheme.typography.titleLarge,
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
            }
        }
    }
}