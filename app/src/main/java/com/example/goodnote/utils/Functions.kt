package com.example.goodnote.utils

import androidx.lifecycle.MutableLiveData
import com.example.goodnote.database.entityModels.*
import com.example.goodnote.repository.domainModels.NoteDomanModel
import com.example.goodnote.repository.domainModels.TagDomainModel
import com.example.goodnote.ui.models.NoteListModel

internal fun List<TagEntity>.toTagsString(): String = joinToString(separator = ", ") { it.name } // shortest
    // long:
    // val names: List<String> = this.map { it.name }
   // return names.joinToString(separator = ", ")

    // shorter: map { it.name }.joinToString(separator = ", ")

internal fun <T> MutableLiveData<List<T>>.addOne(item: T) {
    val value = this.value ?: emptyList()
    this.value = value + listOf(item)
}

internal fun NoteEntity.toNoteDomainModel(tags: List<TagDomainModel>): NoteDomanModel {
    return NoteDomanModel(
        this.id,
        this.noteId,
        this.title,
        this.text,
        tags
    )
}

internal fun NoteDomanModel.toNoteListModel(tags: String): NoteListModel{
    return NoteListModel(
        this.id,
        this.noteId,
        this.title,
        this.text,
        tags
    )
}

internal fun TagEntity.toTagDomainModel(): TagDomainModel {
    return TagDomainModel(
        this.id,
        this.name,
        this.tagId
    )
}