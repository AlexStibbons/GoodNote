package com.example.goodnote.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(

    @PrimaryKey(autoGenerate = true)
    private var id: Int?,
    private var title: String,
    private var text: String
)