package com.shivam_raj.noteapp.screens.addNoteScreen.bottomSheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.shivam_raj.noteapp.ui.theme.NOTE_BACKGROUND_COLOR_LIST

@Composable
fun NoteBackgroundColorSheetItem(
    noteBackgroundColorIndex: Int,
    onCheckedChange: (Int?) -> Unit
) {
    var checked by remember {
        mutableStateOf(noteBackgroundColorIndex != -1)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Custom background color", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "If not provided, background color will be based on your note's priority.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.alpha(0.6f)
                )
            }
            Switch(
                checked = checked,
                onCheckedChange = { checked = it; if (!it) onCheckedChange(null) })
        }
        AnimatedVisibility(
            visible = checked,
            enter = slideInVertically(),
            exit = slideOutVertically()
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(NOTE_BACKGROUND_COLOR_LIST) { index, color ->
                    Column(
                        modifier = Modifier
                            .width(80.dp)
                            .height(100.dp)
                            .background(
                                color = if (isSystemInDarkTheme()) color.darkColorTheme.colorContainer else color.lightColorTheme.colorContainer,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .clickable {
                                onCheckedChange(index)
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AnimatedVisibility(
                            index == noteBackgroundColorIndex,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                }
            }
        }
    }

}