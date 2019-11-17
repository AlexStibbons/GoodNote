package com.example.goodnote.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.goodnote.database.models.Tag

@Dao
interface TagDao {

    @Query("SELECT * FROM tag")
    suspend fun getAllTags(): List<Tag>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTag(tag: Tag)

    // remove from join first!
    @Query("DELETE FROM tag WHERE tag.id = :id")
    suspend fun deleteTagById(id: Int)

    @Query("SELECT * FROM tag WHERE tag.name LIKE '%' || :name || '%'")
    suspend fun findTagsByName(name: String): List<Tag>

    @Query("SELECT * FROM tag WHERE tag.id = :id")
    suspend fun findTagById(id: Int): Tag
}

