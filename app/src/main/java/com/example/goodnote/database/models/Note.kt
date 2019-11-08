package com.example.goodnote.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    var title: String,
    var text: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}