package com.example.goodnote.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tag(
    var name: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}