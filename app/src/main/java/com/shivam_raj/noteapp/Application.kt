package com.shivam_raj.noteapp

import android.app.Application
import com.shivam_raj.noteapp.database.NoteDatabase
import com.shivam_raj.noteapp.database.NoteRepository

/**
 * Application class for the app.
 * @property noteRepository Room database repository is declared here.
 */
class Application : Application() {

    lateinit var noteRepository: NoteRepository
    override fun onCreate() {
        super.onCreate()
        noteRepository = NoteRepository(NoteDatabase.getDatabase(this).noteDao())
    }
}