package com.example.goodnote.repository

import com.example.goodnote.database.entityModels.TagEntity
import com.example.goodnote.repository.domainModels.TagDomainModel

interface TagRepo {

    suspend fun getAllTags(): List<TagDomainModel>

    suspend fun addTag(tag: TagDomainModel): Long

    suspend fun deleteTagById(id: String)

    suspend fun findTagById(id: String): TagDomainModel

    suspend fun findTagsByName(name: String): List<TagDomainModel>
}