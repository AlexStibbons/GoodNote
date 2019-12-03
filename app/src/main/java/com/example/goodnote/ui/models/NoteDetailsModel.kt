package com.example.goodnote.ui.models

import java.util.*

data class NoteDetailsModel(
    val noteId: String = UUID.randomUUID().toString(),
    val title: String,
    val text: String,
    val tags: List<TagModel> = emptyList()
)