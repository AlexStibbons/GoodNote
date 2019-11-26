package com.example.goodnote.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.goodnote.database.entityModels.TagEntity

@Dao
interface TagDao {

    @Query("SELECT * FROM tagentity")
    suspend fun getAllTags(): List<TagEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTag(tag: TagEntity)

    // remove from join first!
    @Query("DELETE FROM tagentity WHERE tagentity.tagId = :id")
    suspend fun deleteTagById(id: String)

    @Query("SELECT * FROM tagentity WHERE tagentity.name LIKE '%' || :name || '%'")
    suspend fun findTagsByName(name: String): List<TagEntity>

    @Query("SELECT * FROM tagentity WHERE tagentity.tagId = :id")
    suspend fun findTagById(id: String): TagEntity
}

