package com.example.goodnote.database.entityModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(indices=[Index(value = ["tagId"], unique = true)])
data class TagEntity(
    var name: String,
    var tagId: String = UUID.randomUUID().toString()
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}