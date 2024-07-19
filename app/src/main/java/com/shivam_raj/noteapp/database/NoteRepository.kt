package com.shivam_raj.noteapp.database

import kotlinx.coroutines.flow.Flow

class NoteRepository(private val dao: NoteDao) : NoteDao {

    override suspend fun addOrUpdateNote(note: Note) = dao.addOrUpdateNote(note)

    override suspend fun deleteNote(note: Note) = dao.deleteNote(note)

    override fun getAllNote(): Flow<List<Note>> = dao.getAllNote()

    override fun getAllPasswordProtectedNote(): Flow<List<Note>> = dao.getAllPasswordProtectedNote()

    override suspend fun updatePasswordForNoteID(id: Int, password: String) = dao.updatePasswordForNoteID(id, password)
}