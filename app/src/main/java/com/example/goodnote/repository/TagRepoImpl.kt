package com.example.goodnote.repository

import com.example.goodnote.database.daos.JoinNoteTagDao
import com.example.goodnote.database.daos.TagDao
import com.example.goodnote.database.entityModels.TagEntity
import com.example.goodnote.repository.domainModels.TagDomainModel
import com.example.goodnote.utils.toListTagDomainModel
import com.example.goodnote.utils.toTagDomainModel
import com.example.goodnote.utils.toTagEntity

class TagRepoImpl(private val tagDao: TagDao, private val joinDao: JoinNoteTagDao): TagRepo {

    companion object {
        @Volatile
        private var instance: TagRepoImpl? = null

        fun getInstance(tagDao: TagDao, joinDao: JoinNoteTagDao) =
            instance ?: synchronized(this) {
                instance ?: TagRepoImpl(tagDao, joinDao).also { instance = it }
            }

    }

    override suspend fun getAllTags(): List<TagDomainModel> = tagDao.getAllTags().toListTagDomainModel()

    override suspend fun addTag(tag: TagDomainModel) = tagDao.addTag(tag.toTagEntity())

    override suspend fun deleteTagById(id: String) {
        joinDao.deleteJoinByTagId(id)
        tagDao.deleteTagById(id)
    }

    override suspend fun findTagById(id: String): TagDomainModel = tagDao.findTagById(id).toTagDomainModel()

    override suspend fun findTagsByName(name: String): List<TagDomainModel> = tagDao.findTagsByName(name).toListTagDomainModel()
}