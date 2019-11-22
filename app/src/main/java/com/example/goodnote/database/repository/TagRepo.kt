package com.example.goodnote.database.repository

import com.example.goodnote.database.models.Tag

interface TagRepo {

    suspend fun getAllTags(): List<Tag>

    suspend fun addTag(tag: Tag)

    suspend fun deleteTagById(id: String)

    suspend fun findTagById(id: String): Tag

    suspend fun findTagsByName(name: String): List<Tag>
}