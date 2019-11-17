package com.example.goodnote.database.repository

import com.example.goodnote.database.daos.TagDao
import com.example.goodnote.database.models.Tag

class TagRepoImpl(private val tagDao: TagDao): TagRepo {

    companion object {
        @Volatile
        private var instance: TagRepoImpl? = null

        fun getInstance(tagDao: TagDao) =
            instance ?: synchronized(this) {
                instance ?: TagRepoImpl(tagDao).also { instance = it }
            }

    }

    override suspend fun getAllTags(): List<Tag> = tagDao.getAllTags()

    override suspend fun addTag(tag: Tag) = tagDao.addTag(tag)

    override suspend fun deleteTagBId(id: Int) = tagDao.deleteTagById(id)

    override suspend fun findTagById(id: Int): Tag = tagDao.findTagById(id)

    override suspend fun findTagsByName(name: String): List<Tag> = tagDao.findTagsByName(name)
}