package com.example.goodnote.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.goodnote.database.models.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    suspend fun getAllNotes(): List<Note> // not really
}