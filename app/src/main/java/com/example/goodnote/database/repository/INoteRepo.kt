package com.example.goodnote.database.repository

import androidx.lifecycle.LiveData
import com.example.goodnote.database.models.*

interface INoteRepo {

    suspend fun getAllNotes(): List<Note>

    suspend fun deleteNote(id: Int)

    suspend fun saveNote(note: Note)

    suspend fun findNoteById(id: Int): Note

    suspend fun findNoteByTitle(title: String): List<Note>
}