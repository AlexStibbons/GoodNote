package com.example.goodnote.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.goodnote.database.models.Tag

@Dao
interface TagDao {

    @Query("SELECT * FROM tag")
    suspend fun getAllTags(): List<Tag> // not really

}

