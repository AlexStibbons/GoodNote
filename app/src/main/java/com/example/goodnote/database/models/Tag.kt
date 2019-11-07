package com.example.goodnote.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tag(

    @PrimaryKey(autoGenerate = true)
    private var id: Int?,
    private var name: String // or val since tags can only be created or deleted, not changed
)