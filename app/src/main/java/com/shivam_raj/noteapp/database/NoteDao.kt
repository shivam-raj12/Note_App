package com.shivam_raj.noteapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Upsert
    suspend fun addOrUpdateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM Note_Table ORDER BY CASE WHEN pinnedAt IS NULL THEN notePriority ELSE pinnedAt END DESC, notePriority DESC, dateAdded DESC")
    fun getAllNote(): Flow<List<Note>>

    @Query("SELECT * FROM Note_Table WHERE password IS NOT NULL ORDER BY notePriority DESC")
    fun getAllPasswordProtectedNote(): Flow<List<Note>>

    @Query("UPDATE Note_Table SET password = :password WHERE id = :id")
    suspend fun updatePasswordForNoteID(id: String, password:String)

//    @Query("UPDATE Note_Table SET isOnlineSynced = :isOnlineSynced WHERE id = :id")
//    suspend fun updateIsOnlineSyncedForNoteID(id: Int, isOnlineSynced:Boolean)
}