package com.example.goodnote.graveyard

import com.example.goodnote.database.LocalDb
import com.example.goodnote.database.models.Note
import com.example.goodnote.database.repository.INoteRepo

class NoteRepoNotSnglt(
    private val localDb: LocalDb
    //private val noteDao: NoteDao
) : INoteRepo {


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