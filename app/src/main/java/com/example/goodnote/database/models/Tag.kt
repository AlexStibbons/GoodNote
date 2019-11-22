package com.example.goodnote.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Tag(
    var name: String,
    val tagId: String = UUID.randomUUID().toString()
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}