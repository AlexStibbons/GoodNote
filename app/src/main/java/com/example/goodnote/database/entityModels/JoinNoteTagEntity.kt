package com.example.goodnote.database.entityModels

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "joinNoteTagEntity",
    primaryKeys = ["tagId", "noteId"],
    foreignKeys = [
        ForeignKey(
            entity = TagEntity::class,
            parentColumns = arrayOf("tagId"),
            childColumns = arrayOf("tagId")
        ),
        ForeignKey(
            entity = NoteEntity::class,
            parentColumns = arrayOf("noteId"),
            childColumns = arrayOf("noteId")
        )
    ]
)
data class JoinNoteTagEntity(
    val tagId: String,
    val noteId: String
)