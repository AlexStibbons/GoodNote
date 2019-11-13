package com.example.goodnote.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.goodnote.database.models.*

@Dao
interface JoinNoteTagDao {

    @Query("SELECT * FROM tag INNER JOIN joinNoteTag ON tag.id = joinNoteTag.tagId WHERE joinNoteTag.noteId = :noteId")
    suspend fun getTagsForNote(noteId: Int): List<Tag>

    @Query("SELECT * FROM note INNER JOIN joinNoteTag ON note.id = joinNoteTag.noteId WHERE joinNoteTag.tagId = :tagId")
    suspend fun getNotesForTag(tagId: Int): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNoteTag(noteTag: JoinNoteTag)

    @Query("DELETE FROM joinNoteTag WHERE noteId = :noteId")
    suspend fun deleteJoinByNoteId(noteId: Int)

    @Query("DELETE FROM joinNoteTag WHERE tagId = :tagId")
    suspend fun deleteJoinByTagId(tagId: Int)

}