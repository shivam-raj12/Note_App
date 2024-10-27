package com.shivam_raj.noteapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.UUID

@Entity(tableName = "Note_Table")
data class Note(
    @PrimaryKey(autoGenerate = false)
    val id: String = UUID.randomUUID().toString(),
    val userId: String? = null,
    val noteTitle: String,
    val noteDescription: String,
    val fakeTitle: String? = null,
    val fakeDescription: String? = null,
    val notePriority: Int,
    val dateAdded: Long,
    val lastUpdate: Long,
    val pinnedAt: Long? = null,
    val password: String? = null,
    val colorIndex: Int? = null,
    val allowEditing: Boolean = true
) : Serializable {
    constructor() : this(
        noteTitle = "",
        noteDescription = "",
        notePriority = 0,
        dateAdded = 0,
        lastUpdate = 0
    )

    override fun toString(): String {
        return "Title: $noteTitle\n" +
                "Description: $noteDescription\n" +
                "Fake Title: $fakeTitle\n" +
                "Fake Description: $fakeDescription\n" +
                "Priority: $notePriority\n" +
                "Note Background Color: $colorIndex\n" +
                "Password: $password"
    }

}

val EmptyNote = Note(
    noteTitle = "",
    noteDescription = "",
    notePriority = 0,
    dateAdded = 0,
    lastUpdate = 0
)