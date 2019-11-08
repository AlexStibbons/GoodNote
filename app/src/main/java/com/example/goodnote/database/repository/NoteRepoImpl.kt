package com.example.goodnote.database.repository

import android.content.Context
import com.example.goodnote.database.LocalDb
import com.example.goodnote.database.LocalDb_Impl
import com.example.goodnote.database.models.Note

class NoteRepoImpl(
    private val localDb: LocalDb
    //val context: Context
) : INoteRepo {

    // ?
    /*private val localDb: LocalDb by lazy {
       localDb.getInstance(context)
    }*/

    override suspend fun getAllNotes(): List<Note> {
        return localDb.noteDao().getAllNotes()
    }

    override fun deleteNote(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveNote(note: Note) {
        return localDb.noteDao().addNote(note)
    }

    override fun findNoteById(id: Int): Note {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findNoteByTitle(title: String): List<Note> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}