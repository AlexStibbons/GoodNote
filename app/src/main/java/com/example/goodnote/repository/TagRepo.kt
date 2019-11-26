package com.example.goodnote.repository

import com.example.goodnote.database.entityModels.TagEntity

interface TagRepo {

    suspend fun getAllTags(): List<TagEntity>

    suspend fun addTag(tag: TagEntity)

    suspend fun deleteTagById(id: String)

    suspend fun findTagById(id: String): TagEntity

    suspend fun findTagsByName(name: String): List<TagEntity>
}