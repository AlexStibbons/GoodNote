package com.example.goodnote.repository

import com.example.goodnote.database.daos.TagDao
import com.example.goodnote.database.entityModels.TagEntity

class TagRepoImpl(private val tagDao: TagDao): TagRepo {

    companion object {
        @Volatile
        private var instance: TagRepoImpl? = null

        fun getInstance(tagDao: TagDao) =
            instance ?: synchronized(this) {
                instance ?: TagRepoImpl(tagDao).also { instance = it }
            }

    }

    override suspend fun getAllTags(): List<TagEntity> = tagDao.getAllTags()

    override suspend fun addTag(tag: TagEntity) = tagDao.addTag(tag)

    override suspend fun deleteTagById(id: String) = tagDao.deleteTagById(id)

    override suspend fun findTagById(id: String): TagEntity = tagDao.findTagById(id)

    override suspend fun findTagsByName(name: String): List<TagEntity> = tagDao.findTagsByName(name)
}