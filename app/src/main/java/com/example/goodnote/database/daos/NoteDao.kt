package com.example.goodnote.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.goodnote.database.entityModels.NoteEntity

@Dao
interface NoteDao {

    @Query("SELECT * FROM noteentity")
    suspend fun getAllNotes(): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: NoteEntity): Long

    @Query("DELETE FROM noteentity where noteentity.noteId = :id")
    suspend fun deleteNote(id: String)

    @Query("SELECT * FROM noteentity WHERE noteentity.title LIKE '%' || :title || '%'")
    suspend fun findNotesByTitle(title: String): List<NoteEntity>

    @Query("SELECT * FROM noteentity WHERE noteentity.noteId = :id")
    suspend fun findNoteById(id: String): NoteEntity
}