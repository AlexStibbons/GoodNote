package com.example.goodnote.repository

import com.example.goodnote.database.entityModels.*
import com.example.goodnote.repository.domainModels.NoteDomanModel

interface NoteRepo {

    suspend fun getAllNotes(): List<NoteDomanModel>

    suspend fun deleteNote(id: String)

    suspend fun saveNote(note: NoteEntity): Long

    suspend fun findNoteById(id: String): NoteEntity

    suspend fun findNoteByTitle(title: String): List<NoteEntity>

}