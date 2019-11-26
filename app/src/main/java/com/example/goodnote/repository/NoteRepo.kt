package com.example.goodnote.repository

import com.example.goodnote.database.entityModels.*

interface NoteRepo {

    suspend fun getAllNotes(): List<NoteEntity>

    suspend fun deleteNote(id: String)

    suspend fun saveNote(note: NoteEntity)

    suspend fun findNoteById(id: String): NoteEntity

    suspend fun findNoteByTitle(title: String): List<NoteEntity>

}