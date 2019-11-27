package com.example.goodnote.ui.models

data class NoteDetailsModel(
    val noteId: String,
    var title: String,
    val text: String,
    val tags: List<TagModel> = emptyList()
)