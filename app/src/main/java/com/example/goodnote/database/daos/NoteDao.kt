package com.example.goodnote.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.goodnote.database.models.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    suspend fun getAllNotes(): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    // remove from join table first!
    @Query("DELETE FROM note where note.id = :id")
    suspend fun deleteNote(id: Int)

    @Query("SELECT * FROM note WHERE note.title LIKE '%' || :title || '%'")
    suspend fun findNotesByTitle(title: String): List<Note>
}