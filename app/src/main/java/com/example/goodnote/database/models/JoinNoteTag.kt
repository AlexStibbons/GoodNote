package com.example.goodnote.database.models

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "joinNoteTag",
    primaryKeys = ["tagId", "noteId"],
    foreignKeys = [
        ForeignKey(
            entity = Tag::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("tagId")
        ),
        ForeignKey(
            entity = Note::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("noteId")
        )
    ]
)
data class JoinNoteTag(
    val tagId: Int,
    val noteId: Int
)