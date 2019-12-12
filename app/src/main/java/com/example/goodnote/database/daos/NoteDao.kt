package com.example.goodnote.database.daos

import androidx.room.*
import com.example.goodnote.database.entityModels.NoteEntity

@Dao
interface NoteDao {

    @Query("SELECT * FROM noteentity")
    suspend fun getAllNotes(): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: NoteEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg note: NoteEntity): Int

    @Query("DELETE FROM noteentity where noteentity.noteId = :id")
    suspend fun deleteNote(id: String)

    @Query("SELECT * FROM noteentity WHERE noteentity.title LIKE '%' || :title || '%'")
    suspend fun findNotesByTitle(title: String): List<NoteEntity>

    @Query("SELECT * FROM noteentity WHERE noteentity.noteId = :id")
    suspend fun findNoteById(id: String): NoteEntity
}