package com.example.goodnote.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.goodnote.database.entityModels.*

@Dao
interface JoinNoteTagDao {

    @Query("SELECT * FROM tagentity INNER JOIN joinNoteTagEntity ON tagentity.tagId = joinNoteTagEntity.tagId WHERE joinNoteTagEntity.noteId = :noteId ")
    suspend fun getTagsForNote(noteId: String): List<TagEntity>

    //@Query("SELECT * FROM note INNER JOIN joinNoteTag ON note.id = joinNoteTag.noteId WHERE joinNoteTag.tagId = :tagId")

    @Query("SELECT * FROM noteentity INNER JOIN joinNoteTagEntity ON noteentity.noteId = joinNoteTagEntity.noteId WHERE joinNoteTagEntity.tagId = :tagId")
    suspend fun getNotesForTag(tagId: String): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNoteTag(noteTag: JoinNoteTagEntity)

    //@Query("DELETE FROM joinNoteTag WHERE noteId = :noteId")
    @Query("DELETE FROM joinNoteTagEntity WHERE noteId = :noteId")
    suspend fun deleteJoinByNoteId(noteId: String)

    //@Query("DELETE FROM joinNoteTag WHERE tagId = :tagId")
    @Query("DELETE FROM joinNoteTagEntity WHERE tagId = :tagId")
    suspend fun deleteJoinByTagId(tagId: String)

}