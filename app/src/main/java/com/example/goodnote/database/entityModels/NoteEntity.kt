package com.example.goodnote.database.entityModels

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(indices=[Index(value = ["noteId"], unique = true)])
data class NoteEntity(
    var title: String,
    var text: String,
    val noteId: String = UUID.randomUUID().toString()
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}