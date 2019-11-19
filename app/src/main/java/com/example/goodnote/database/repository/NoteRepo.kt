package com.example.goodnote.database.repository

import com.example.goodnote.database.models.*

interface NoteRepo {

    suspend fun getAllNotes(): List<Note>

    suspend fun deleteNote(id: String)

    suspend fun saveNote(note: Note)

    suspend fun findNoteById(id: String): Note

    suspend fun findNoteByTitle(title: String): List<Note>

}