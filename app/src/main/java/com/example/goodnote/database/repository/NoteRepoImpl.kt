package com.example.goodnote.database.repository

import androidx.lifecycle.LiveData
import com.example.goodnote.database.LocalDb
import com.example.goodnote.database.daos.NoteDao
import com.example.goodnote.database.models.Note

class NoteRepoImpl(
    private val localDb: LocalDb
    //private val noteDao: NoteDao
) : INoteRepo {

   // val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    override suspend fun getAllNotes(): List<Note> {
        return localDb.noteDao().getAllNotes()
    }

    override suspend fun deleteNote(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveNote(note: Note) {
        localDb.noteDao().addNote(note)
    }

    override suspend fun findNoteById(id: Int): Note {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findNoteByTitle(title: String): List<Note> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}