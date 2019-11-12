package com.example.goodnote.database.repository

import com.example.goodnote.database.daos.NoteDao
import com.example.goodnote.database.models.Note

class NoteRepo2 private constructor(private val noteDao: NoteDao) {

    companion object{
        @Volatile
        private var instance: NoteRepo2? = null

        fun getInstance(noteDao: NoteDao) =
            instance ?: synchronized(this) {
                instance ?: NoteRepo2(noteDao).also { instance = it }
            }
    }

    suspend fun getNotes() = noteDao.getAllNotes()

    suspend fun saveNote(note: Note) = noteDao.addNote(note)
}