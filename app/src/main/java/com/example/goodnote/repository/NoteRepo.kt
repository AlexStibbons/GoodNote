package com.example.goodnote.repository

import com.example.goodnote.database.entityModels.*
import com.example.goodnote.repository.domainModels.NoteDomanModel

interface NoteRepo {

    suspend fun getAllNotes(): List<NoteDomanModel>

    suspend fun deleteNote(id: String)

    suspend fun saveNote(note: NoteDomanModel): Long

    suspend fun findNoteById(id: String): NoteDomanModel

    suspend fun findNoteByTitle(title: String): List<NoteDomanModel>

    suspend fun deleteTagForNote(noteId: String, tagId: String)

    suspend fun addTagForNote(noteId: String, tagId: String)

    suspend fun updateNote(note: NoteDomanModel): Int
}