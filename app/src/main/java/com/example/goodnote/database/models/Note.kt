package com.example.goodnote.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Note(
    var title: String,
    var text: String,
    val noteId: String = UUID.randomUUID().toString() // should this then be the primary key?
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}