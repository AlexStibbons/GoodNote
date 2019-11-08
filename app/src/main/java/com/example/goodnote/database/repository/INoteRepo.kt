package com.example.goodnote.database.repository

import com.example.goodnote.database.models.*

interface INoteRepo {

    suspend fun getAllNotes(): List<Note>

    fun deleteNote(id: Int)

    suspend fun saveNote(note: Note)

    fun findNoteById(id: Int): Note

    fun findNoteByTitle(title: String): List<Note>
}