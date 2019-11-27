package com.example.goodnote.utils

import androidx.lifecycle.MutableLiveData
import com.example.goodnote.database.entityModels.*
import com.example.goodnote.repository.domainModels.NoteDomanModel
import com.example.goodnote.repository.domainModels.TagDomainModel
import com.example.goodnote.ui.models.NoteDetailsModel
import com.example.goodnote.ui.models.NoteListModel
import com.example.goodnote.ui.models.TagModel

internal fun <T> MutableLiveData<List<T>>.addOne(item: T) {
    val value = this.value ?: emptyList()
    this.value = value + listOf(item)
}

internal fun List<TagEntity>.toListTagDomainModel(): List<TagDomainModel> {
    val list = mutableListOf<TagDomainModel>()
    this.forEach{
        list.add(
            TagDomainModel(
            it.tagId,
            it.name
        )
        )
    }

    return list
}

internal fun List<TagDomainModel>.toListTagModel(): List<TagModel> {
    val list = mutableListOf<TagModel>()
    this.forEach{
        list.add(
            TagModel(
                it.tagId,
                it.name
            )
        )
    }

    return list
}

internal fun List<TagDomainModel>.toTagsString(): String = joinToString(separator = ", ") { it.name }

internal fun NoteEntity.toNoteDomainModel(tags: List<TagDomainModel>): NoteDomanModel {
    return NoteDomanModel(
        this.noteId,
        this.title,
        this.text,
        tags
    )
}

internal fun NoteDomanModel.toNoteListModel(): NoteListModel{
    return NoteListModel(
        this.noteId,
        this.title,
        this.text,
        this.tags.toTagsString()
    )
}

internal fun NoteDomanModel.toNoteDetailsModel(): NoteDetailsModel {
    return NoteDetailsModel(
        this.noteId,
        this.title,
        this.text,
        this.tags.toListTagModel()
    )
}