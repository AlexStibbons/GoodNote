package com.example.goodnote.repository

import com.example.goodnote.database.entityModels.*
import com.example.goodnote.repository.domainModels.NoteDomanModel

interface NoteRepo {

    suspend fun getAllNotes(): List<NoteDomanModel>

    suspend fun deleteNote(id: String)

    suspend fun saveNote(note: NoteDomanModel): Long

    suspend fun findNoteById(id: String): NoteDomanModel

    suspend fun findNoteByTitle(title: String): List<NoteDomanModel>

}