package com.example.goodnote.utils

import androidx.lifecycle.MutableLiveData
import com.example.goodnote.database.entityModels.*
import com.example.goodnote.repository.domainModels.NoteDomanModel
import com.example.goodnote.repository.domainModels.TagDomainModel
import com.example.goodnote.ui.models.NoteDetailsModel
import com.example.goodnote.ui.models.NoteModel
import com.example.goodnote.ui.models.TagModel
import java.util.*

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
@JvmName("toListTagDomainModelFromModel")
internal fun List<TagModel>.toListTagDomainModel(): List<TagDomainModel> {
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
// maybe add "no tags yet!" if tag list is empty?
internal fun List<TagDomainModel>.toTagsString(): String = joinToString(separator = ", ") { it.name }
@JvmName("toTagsStringOnModel")
internal fun List<TagModel>.toTagsString(): String = joinToString(separator = ", ") { it.name }

internal fun NoteEntity.toNoteDomainModel(tags: List<TagDomainModel>): NoteDomanModel {
    return NoteDomanModel(
        this.noteId,
        this.title,
        this.text,
        tags
    )
}

internal fun NoteDomanModel.toNoteModel(): NoteModel{

    val tagsString = if (this.tags.isEmpty()) "no tags yet" else this.tags.toTagsString()

    return NoteModel(
        this.noteId,
        this.title,
        this.text,
        tagsString
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

internal fun NoteDetailsModel.toNoteModel(): NoteModel {

    val tagsString = if (this.tags.isEmpty()) "no tags yet" else this.tags.toTagsString()

    return NoteModel(
        this.noteId,
        this.title,
        this.text,
        tagsString
    )
}

internal fun NoteDetailsModel.toNoteDomainModel(): NoteDomanModel {
    return NoteDomanModel(
        this.noteId,
        this.title,
        this.text,
        this.tags.toListTagDomainModel()
    )
}

internal fun NoteDomanModel.toNoteEntity(): NoteEntity {

    val uuid = if (this.noteId.isNullOrEmpty()) UUID.randomUUID().toString() else this.noteId

    return NoteEntity(
        this.title,
        this.text,
        uuid
    )
}